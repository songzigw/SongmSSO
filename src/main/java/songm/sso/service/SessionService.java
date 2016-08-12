package songm.sso.service;

import songm.sso.entity.Backstage;
import songm.sso.entity.Session;

public interface SessionService {

    public Session create(Backstage back);
}
