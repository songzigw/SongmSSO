package songm.sso.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import songm.sso.entity.Account;
import songm.sso.service.AuthService;
import songm.sso.service.CachingConfig;

/**
 * 受权
 * <p>
 * Created by Tony on 4/14/16.
 */
@Component
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean auth(Account account) {
        cacheManager.getCache(CachingConfig.AUTH_CLIENT).put(account.getKey(),
                account);

        logger.debug("auth serverId={}, userId={}, token={}");
        return true;
    }

    @Override
    public boolean quit(Account account) {
        return true;
    }
}
