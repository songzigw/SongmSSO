package songm.sso.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import songm.sso.CodeUtils;
import songm.sso.Config;
import songm.sso.entity.Account;
import songm.sso.service.AccountService;
import songm.sso.service.CachingConfig;

@Component
public class AccountServiceImpl implements AccountService {

    private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private long MISTIMING = 3 * 1000;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean auth(Account account) {
        long curr = account.getCreated().getTime();
        long mis = curr - account.getTimestamp();
        if (mis > MISTIMING || mis < -MISTIMING) {
            return false;
        }

        String key = Config.getInstance().getServerKey();
        String secret = Config.getInstance().getServerSecret();
        if (!key.equals(account.getServerKey())) {
            return false;
        }

        StringBuilder toSign = new StringBuilder(secret).append(
                account.getNonce()).append(account.getTimestamp());
        String sign = CodeUtils.hexSHA1(toSign.toString());
        if (!sign.equals(account.getSignature())) {
            return false;
        }

        cacheManager.getCache(CachingConfig.AUTH_CLIENT).put(
                account.getClientId(), account);
        logger.debug("Auth success, clientId={}", account.getClientId());
        return true;
    }

    @Override
    public boolean quit(Account account) {
        return true;
    }
}
