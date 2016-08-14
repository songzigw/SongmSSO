package songm.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import songm.sso.CodeUtils;
import songm.sso.Config;
import songm.sso.entity.Backstage;
import songm.sso.service.BackstageService;
import songm.sso.service.CachingConfig;

@Component
public class BackstageServiceImpl implements BackstageService {

    private long MISTIMING = 3 * 1000;

    @Autowired
    private CacheManager cacheManager;

    @Override
    public boolean auth(Backstage back) {
        long curr = back.getCreated().getTime();
        long mis = curr - back.getTimestamp();
        if (mis > MISTIMING || mis < -MISTIMING) {
            return false;
        }

        String key = Config.getInstance().getServerKey();
        String secret = Config.getInstance().getServerSecret();
        if (!key.equals(back.getServerKey())) {
            return false;
        }

        StringBuilder toSign = new StringBuilder(secret)
                .append(back.getNonce()).append(back.getTimestamp());
        String sign = CodeUtils.sha1(toSign.toString());
        if (!sign.equals(back.getSignature())) {
            return false;
        }

        cacheManager.getCache(CachingConfig.BACKSTAGE_ITEMS).put(
                back.getBackId(), back);
        return true;
    }

    @Override
    public boolean quit(Backstage back) {
        return true;
    }
}
