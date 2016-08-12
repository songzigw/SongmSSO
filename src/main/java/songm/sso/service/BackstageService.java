package songm.sso.service;

import songm.sso.entity.Backstage;


public interface BackstageService {

    boolean auth(Backstage back);

    boolean quit(Backstage back);

}
