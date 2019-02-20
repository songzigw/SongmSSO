package cn.songm.sso.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.songm.common.utils.Sequence;
import cn.songm.common.utils.StringUtils;
import cn.songm.sso.dao.SessionDao;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.SesAttrRedis;
import cn.songm.sso.redis.UserRedis;
import cn.songm.sso.service.SSOService;

@Service("ssoService")
public class SSOServiceImpl implements SSOService {

    //private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private UserRedis userRedis;
    @Autowired
    private SesAttrRedis sesAttrRedis;
    
    private Session getSession(String sesId, boolean isAccess) {
        if (StringUtils.isEmptyOrNull(sesId)) return null;
        
        // 缓存中是否存在，存在从缓存中获取并返回，绕开数据库
        // 缓存中不存在，从数据库中查询并，并同步到缓存中
        Session ses = sessionDao.selectOneById(sesId);
        if (ses == null) return null;
        
        if (ses.isTimeout()) {
            // 数据库删除后，同时删除缓存
            sessionDao.delete(sesId);
            return null;
        }
        
        if (isAccess) {
            // 修改数据库，并同步到缓存
            ses.setAccess(System.currentTimeMillis());
            sessionDao.updateAccess(sesId, ses.getAccess());
        }
        
        return ses;
    }

    @Override
    public Session getSession(String sesId) {
        return getSession(sesId, false);
    }
    
    @Override
    public Session report(String sesId) {
        Session ses = getSession(sesId, true);
        if (ses != null) return ses;
        
        return createSession();
    }
    
    private Session createSession() {
        Session ses = new Session(Sequence.getInstance().getSequence(28));
        sessionDao.insert(ses);
        return ses;
    }
    
    @Override
    public Session login(String sessionId, String userId, Object user) {
        Session ses = getSession(sessionId, false);
        if (ses == null) ses = createSession();
        ses.setUserId(userId);
        // 绑定session登入的用户，并且更新缓存
        sessionDao.updateUserId(userId, userId);
        // 用户完整信息之间保存到缓存，不进过数据库
        userRedis.insert(userId, user);
        return ses;
    }
    
    @Override
    public void logout(String sesId) {
        // 数据库删除后，同时删除缓存
        sessionDao.delete(sesId);
    }
    
    @Override
    public Object getUser(String sesId) {
        Session ses = getSession(sesId, false);
        if (ses == null) return null;
        return userRedis.selectByUid(ses.getUserId());
    }
    
    @Override
    public String getUserId(String sesId) {
        Session ses = getSession(sesId, false);
        if (ses == null) return null;
        return ses.getUserId();
    }

    @Override
    public void setAttr(String sesId, String attrKey, Object attrVal) {
        sesAttrRedis.insertAttr(sesId, attrKey, attrVal);
    }

    @Override
    public Object getAttr(String sesId, String attrKey) {
        return sesAttrRedis.selectAttr(sesId, attrKey);
    }

    @Override
    public void editUser(String userId, Object user) {
        userRedis.insert(userId, user);
    }

    @Override
    public List<Session> getSessionsByUid(String userId) {
        return sessionDao.selectListByUid(userId);
    }

    @Override
    public List<Session> getSessionsOnline() {
        new RuntimeException("未完工");
        return null;
    }

}
