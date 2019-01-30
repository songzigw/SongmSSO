package cn.songm.sso.redis.impl;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.sso.redis.SesAttrRedis;

@Repository("sesAttrRedis")
public class SesAttrRedisImpl extends BaseRedisImpl<Void> implements SesAttrRedis {

    public static final String H_SESATTR_KEY = "sso_h_sesattr/%s";
    public static final String L_USERSES_KEY = "sso_l_userses/%s";
    
    @Override
    public void insertAttr(String sesId, String attrKey, Object attrVal) {
        this.hset(String.format(H_SESATTR_KEY, sesId), attrKey, attrVal);
    }

    @Override
    public Object selectAttr(String sesId, String attrKey) {
        return this.hget(String.format(H_SESATTR_KEY, sesId), attrKey);
    }

    @Override
    public Void serialize(Void entity, RedisConnection connection) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Void unserialize(Void entity, RedisConnection connection) {
        // TODO Auto-generated method stub
        return null;
    }
}
