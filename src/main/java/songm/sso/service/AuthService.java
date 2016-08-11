package songm.sso.service;

import songm.sso.entity.Account;


public interface AuthService {

    boolean auth(Account account);

    boolean quit(Account account);

}
