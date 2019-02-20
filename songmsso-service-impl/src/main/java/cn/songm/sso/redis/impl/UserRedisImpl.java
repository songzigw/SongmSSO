package cn.songm.sso.redis.impl;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.sso.redis.UserRedis;

@Repository("userRedis")
public class UserRedisImpl extends BaseRedisImpl<Void> implements UserRedis {

    public static final String _USER_KEY = "sso_user/%s";
    public static final String L_USERSES_KEY = "sso_l_userses/%s";
    
    @Override
    public void insert(String userId, Object user) {
        this.set(String.format(_USER_KEY, userId), user);
    }

    @Override
    public Object selectByUid(String userId) {
        return this.get(String.format(_USER_KEY, userId));
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
