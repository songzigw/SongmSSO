package songm.sso.service;

import songm.sso.entity.Session;

public interface SessionService {

    public Session getSession(String sessionId);

    public Session create(String sessionId);

    public void remove(String sessionId);

    public void setAttribute(String sessionId, String name, Object obj);

    public Object getAttribute(String sessionId, String name);
}
