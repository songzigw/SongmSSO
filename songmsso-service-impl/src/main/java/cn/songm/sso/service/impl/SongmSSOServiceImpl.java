package cn.songm.sso.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.songm.sso.entity.Attribute;
import cn.songm.sso.entity.Session;
import cn.songm.sso.service.SessionService;
import cn.songm.sso.service.SongmSSOService;

@Service("songmSsoService")
public class SongmSSOServiceImpl implements SongmSSOService {

    @Resource(name = "sessionService")
    private SessionService sessionService;

    @Override
    public Session report(String sessionId) {
        return sessionService.createSession(sessionId);
    }

    @Override
    public Session login(String sessionId, String userId, String userInfo) {
        return sessionService.regist(sessionId, userId, userInfo);
    }

    @Override
    public void logout(String sessionId) {
        sessionService.removeSession(sessionId);
    }

    @Override
    public void setSessionAttr(String sessionId, String key, String value) {
        sessionService.setAttr(sessionId, key, value);
    }

    @Override
    public Attribute getSessionAttr(String sessionId, String key) {
        return sessionService.getAttr(sessionId, key);
    }

    @Override
    public void setValidateCode(String sessionId, String vcode) {
        sessionService.setAttr(sessionId, Attribute.KEY_VCODE, vcode);
    }

    @Override
    public String getValidateCode(String sessionId) {
        Attribute attr = sessionService.getAttr(sessionId, Attribute.KEY_VCODE);
        if (attr == null) {
            return null;
        } else {
            return attr.getValue();
        }
    }

    @Override
    public String getUserInfo(String sessionId) {
        Attribute attr = sessionService.getAttr(sessionId, Attribute.KEY_LOGIN);
        if (attr == null) {
            return null;
        } else {
            return attr.getValue();
        }
    }
}
