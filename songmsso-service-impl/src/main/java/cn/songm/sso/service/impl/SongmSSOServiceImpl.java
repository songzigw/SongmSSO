package cn.songm.sso.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.songm.common.utils.Sequence;
import cn.songm.common.utils.StringUtils;
import cn.songm.sso.dao.SessionDao;
import cn.songm.sso.entity.Session;
import cn.songm.sso.service.SongmSSOService;

@Service("songmSsoService")
public class SongmSSOServiceImpl implements SongmSSOService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SessionDao sessionDao;

    private Session getSession(String sesId, boolean isAccess) {
        if (StringUtils.isEmpty(sesId)) return null;
        
        Session ses = sessionDao.selectOneById(sesId);
        if (ses == null) return null;
        
        if (ses.isTimeout()) {
            sessionDao.delete(sesId);
            return null;
        }
        
        if (isAccess) {
            ses.setAccess(new Date().getTime());
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("sesId", ses.getSesId());
            paramMap.put("access", ses.getAccess());
            sessionDao.update(paramMap);
        }
        
        return ses;
    }
    
    @Override
    public Session report(String sessionId) {
        Session ses = getSession(sessionId, true);
        if (ses != null) return ses;
        
        return createSession();
    }

    private Session createSession() {
        Session ses = new Session(Sequence.getInstance().getSequence(28));
        sessionDao.insert(ses);
        return ses;
    }
    
    @Override
    public Session login(String sessionId, String userId, String userInfo) {
        Session ses = getSession(sessionId, false);
        if (ses == null) {
            ses = createSession();
        }
        ses.setUserId(userId);
        ses.setUserInfo(userInfo);
        sessionDao.update(ses);
        return ses;
    }

    @Override
    public void logout(String sessionId) {
        sessionDao.delete(sessionId);
    }

    @Override
    public String getUserInfo(String sessionId) {
        Session ses = getSession(sessionId, false);
        if (ses != null) return ses.getUserInfo();
        return null;
    }
    
    @Override
    public String getUserId(String sessionId) {
        Session ses = getSession(sessionId, false);
        if (ses != null) return ses.getUserId();
        return null;
    }

    @Override
    public void setSessionAttr(String sessionId, String key, String value, long expires) {
        Session ses = getSession(sessionId, false);
        if (ses == null) return;
        String attr = ses.getAttribute();
        JsonArray attrJArr = null;
        
        if (StringUtils.isEmpty(attr)) {
            attrJArr = new JsonArray();
        } else {
            attrJArr = new JsonParser().parse(attr).getAsJsonArray();
        }
        LOG.debug("SessionAttrStart, sessionId: {}, {}", sessionId, attrJArr.toString());
        boolean f = false;
        for (int i = 0; i < attrJArr.size(); i++) {
            JsonObject attrJObj = attrJArr.get(i).getAsJsonObject();
            String k = attrJObj.get("key").getAsString();
            if (key.equals(k)) {
                attrJObj.addProperty("value", value);
                attrJObj.addProperty("created", new Date().getTime());
                attrJObj.addProperty("expires", expires);
                f = true;
                break;
            }
        }
        if (!f) {
            JsonObject attrJObj = new JsonObject();
            attrJObj.addProperty("key", key);
            attrJObj.addProperty("value", value);
            attrJObj.addProperty("created", new Date().getTime());
            attrJObj.addProperty("expires", expires);
            attrJArr.add(attrJObj);
        }
        LOG.debug("SessionAttrEnd, sessionId: {}, {}", sessionId, attrJArr.toString());
        
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sesId", sessionId);
        paramMap.put("attribute", attrJArr.toString());
        sessionDao.update(paramMap);
    }

    @Override
    public String getSessionAttr(String sessionId, String key) {
        Session ses = getSession(sessionId, false);
        if (ses == null) return null;
        String attr = ses.getAttribute();
        LOG.debug("SessionAttr, sessionId: {}, {}", sessionId, attr);
        
        if (StringUtils.isEmpty(attr)) {
            return null;
        }
        JsonArray attrJArr = new JsonParser().parse(attr).getAsJsonArray();
        for (int i = 0; i < attrJArr.size(); i++) {
            JsonObject attrJObj = attrJArr.get(i).getAsJsonObject();
            String k = attrJObj.get("key").getAsString();
            String v = attrJObj.get("value").getAsString();
            long c = attrJObj.get("created").getAsLong();
            long e = attrJObj.get("expires").getAsLong();
            if (key.equals(k) && System.currentTimeMillis() - c <= e) {
                return v;
            }
        }
        
        return null;
    }

    @Override
    public void delSessionAttr(String sessionId, String key) {
        Session ses = getSession(sessionId, false);
        if (ses == null) return;
        String attr = ses.getAttribute();
        JsonArray attrJArr = null;
        JsonArray newAJArr = new JsonArray();
        
        if (StringUtils.isEmpty(attr)) {
            attrJArr = new JsonArray();
        } else {
            attrJArr = new JsonParser().parse(attr).getAsJsonArray();
        }
        
        boolean f = false;
        for (int i = 0; i < attrJArr.size(); i++) {
            JsonObject attrJObj = attrJArr.get(i).getAsJsonObject();
            String k = attrJObj.get("key").getAsString();
            if (!key.equals(k)) {
                newAJArr.add(attrJObj);
            } else {
                f = true;
            }
        }
        if (f) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("sesId", sessionId);
            paramMap.put("attribute", newAJArr.toString());
            sessionDao.update(paramMap);
        }
    }
    
    /** 一般的验证码标识 */
    public static final String KEY_VCODE = "V_CODE";
    /** 一般的验证码超时时间（毫秒） */
    public static final long KEY_VCODE_EXPIRES = 1000 * 60;
    
    @Override
    public void setValidateCode(String sessionId, String vcode) {
        setSessionAttr(sessionId, KEY_VCODE, vcode, KEY_VCODE_EXPIRES);
    }

    @Override
    public String getValidateCode(String sessionId) {
        return getSessionAttr(sessionId, KEY_VCODE);
    }

    @Override
    public void delValidateCode(String sessionId) {
        delSessionAttr(sessionId, KEY_VCODE);
    }

}
