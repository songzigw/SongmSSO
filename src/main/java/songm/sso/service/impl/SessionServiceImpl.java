package songm.sso.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.entity.Session;
import songm.sso.event.SessionListenerManager;
import songm.sso.service.SessionService;
import songm.sso.utils.Sequence;

@Component
public class SessionServiceImpl implements SessionService {

    private Map<String, Session> sesItems = new HashMap<String, Session>();
    @Autowired
    private SessionListenerManager sessionListenerManager;

    @Override
    public Session create(String sessionId) {
        Session ses = getSession(sessionId);
        if (ses != null) {
            return ses;
        }

        sessionId = Sequence.getInstance().getSequence(28);
        ses = new Session(sessionId);
        sesItems.put(sessionId, ses);

        sessionListenerManager.triggerCreate(ses);
        return ses;
    }

    @Override
    public Session getSession(String sessionId) {
        if (sessionId == null) {
            return null;
        }
        return sesItems.get(sessionId);
    }

    @Override
    public void remove(String sessionId) {
        sesItems.remove(sessionId);
    }

    @Override
    public void setAttribute(String sessionId, String name, Object obj) {
        Session ses = getSession(sessionId);
        if (ses == null) {
            return;
        }
        ses.setAttribute(name, obj);
    }

    @Override
    public Object getAttribute(String sessionId, String name) {
        Session ses = getSession(sessionId);
        if (ses == null) {
            return null;
        }
        return ses.getAttribute(name);
    }

}
