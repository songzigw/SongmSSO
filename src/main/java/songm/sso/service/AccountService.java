package songm.sso.service;

import songm.sso.entity.Account;


public interface AccountService {

    boolean auth(Account account);

    boolean quit(Account account);

}
