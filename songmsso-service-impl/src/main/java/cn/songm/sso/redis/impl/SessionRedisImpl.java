package cn.songm.sso.redis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.common.utils.SerializeUtil;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.Database.SessionF;
import cn.songm.sso.redis.Database.SsoTabs;
import cn.songm.sso.redis.SessionRedis;

@Repository("sessionRedis")
public class SessionRedisImpl extends BaseRedisImpl implements SessionRedis {

    protected RedisSerializer<String> getSerializer() {
        return redisTemplate.getStringSerializer();
    }

    private String getRedisKey(String sesId) {
        return SsoTabs.SSO_SESSION + "/" + sesId;
    }

    private Session serialize(Session ses, RedisConnection connection) {
        Map<byte[], byte[]> d = new HashMap<byte[], byte[]>();
        d.put(SessionF.SES_ID.name().getBytes(), ses.getSesId().getBytes());
        if (ses.getUserId() != null) {
            d.put(SessionF.USER_ID.name().getBytes(),
                    ses.getUserId().getBytes());
        }
        d.put(SessionF.CREATED.name().getBytes(),
                SerializeUtil.serialize(ses.getCreated()));
        d.put(SessionF.ACCESS.name().getBytes(),
                SerializeUtil.serialize(ses.getAccess()));

        connection.hMSet(getRedisKey(ses.getSesId()).getBytes(), d);
        // connection.expire(getRedisKey(ses.getSesId()).getBytes(),
        // Session.TIME_OUT);
        redisTemplate.expire(getRedisKey(ses.getSesId()), Session.TIME_OUT,
                TimeUnit.MILLISECONDS);
        return ses;
    }

    private Session unserialize(String sesId, RedisConnection connection) {
        if (!connection.exists(getRedisKey(sesId).getBytes())) {
            return null;
        }
        List<byte[]> vals = connection.hMGet(getRedisKey(sesId).getBytes(),
                SessionF.USER_ID.name().getBytes(),
                SessionF.CREATED.name().getBytes(),
                SessionF.ACCESS.name().getBytes());
        Session ses = new Session(sesId);
        if (vals.get(0) != null) {
            ses.setUserId(new String(vals.get(0)));
        }
        ses.setCreated((Date) SerializeUtil.unserialize(vals.get(1)));
        ses.setAccess((Date) SerializeUtil.unserialize(vals.get(2)));
        return ses;
    }

    @Override
    public Session insert(Session session) {
        return redisTemplate.execute(new RedisCallback<Session>() {
            @Override
            public Session doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return serialize(session, connection);
            }
        });
    }

    @Override
    public Session queryById(String sesId) {
        return redisTemplate.execute(new RedisCallback<Session>() {
            @Override
            public Session doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return unserialize(sesId, connection);
            }
        });
    }

    @Override
    public void delById(String sesId) {
        redisTemplate.delete(getRedisKey(sesId));
    }

    @Override
    public void updateAccess(String sesId) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                if (connection.hSet(getRedisKey(sesId).getBytes(),
                        SessionF.ACCESS.name().getBytes(),
                        SerializeUtil.serialize(new Date()))) {
                    redisTemplate.expire(getRedisKey(sesId), Session.TIME_OUT,
                            TimeUnit.MILLISECONDS);
                    return 1l;
                } else {
                    return 0l;
                }
            }
        });
    }

    @Override
    public void updateUserId(String sesId, String userId) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                if (connection.hSet(getRedisKey(sesId).getBytes(),
                        SessionF.USER_ID.name().getBytes(),
                        userId.getBytes())) {
                    return 1l;
                } else {
                    return 0l;
                }
            }
        });
    }

}
