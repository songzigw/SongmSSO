package cn.songm.sso.redis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.common.utils.SerializeUtil;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.SessionRedis;

@Repository("sessionRedis")
public class SessionRedisImpl extends BaseRedisImpl<Session>
        implements SessionRedis {

    public static final byte[] SES_FIELD_SESID = "ses_id".getBytes();
    public static final byte[] SES_FIELD_USER_ID = "user_id".getBytes();
    public static final byte[] SES_FIELD_CREATED = "created".getBytes();
    public static final byte[] SES_FIELD_ACCESS = "access".getBytes();
    
    public static final String H_SES_ID_KEY = "h_session/%s";
    public static final byte[] L_SESSION_KEY = "l_session".getBytes();

//    protected RedisSerializer<String> getSerializer() {
//        return redisTemplate.getStringSerializer();
//    }

    public Session serialize(Session ses, RedisConnection connection) {
        Map<byte[], byte[]> d = new HashMap<byte[], byte[]>();

        d.put(SES_FIELD_SESID, ses.getSesId().getBytes());
        if (ses.getUserId() != null) {
            d.put(SES_FIELD_USER_ID, ses.getUserId().getBytes());
        }
        d.put(SES_FIELD_CREATED, SerializeUtil.serialize(ses.getCreated()));
        d.put(SES_FIELD_ACCESS, SerializeUtil.serialize(ses.getAccess()));

        String key = format(H_SES_ID_KEY, ses.getSesId());
        connection.hMSet(key.getBytes(), d);

        redisTemplate.expire(key, Session.TIME_OUT, TimeUnit.MILLISECONDS);
        return ses;
    }

    public Session unserialize(Session ses, RedisConnection connection) {
        String key = format(H_SES_ID_KEY, ses.getSesId());
        if (!connection.exists(key.getBytes())) return null;
        List<byte[]> vals = connection.hMGet(key.getBytes(), SES_FIELD_USER_ID,
                SES_FIELD_CREATED, SES_FIELD_ACCESS);
        if (vals.get(0) != null) {
            ses.setUserId(new String(vals.get(0)));
        }
        ses.setCreated((Date) SerializeUtil.unserialize(vals.get(1)));
        ses.setAccess((Long) SerializeUtil.unserialize(vals.get(2)));
        return ses;
    }

    @Override
    public Session insert(Session session) {
        return redisTemplate.execute(new RedisCallback<Session>() {
            @Override
            public Session doInRedis(RedisConnection connection)
                    throws DataAccessException {
                //connection.lPush(L_SESSION_KEY, session.getSesId().getBytes());
                //connection.expire(L_SESSION_KEY, Session.TIME_OUT);
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
                Session ses = new Session(sesId);
                return unserialize(ses, connection);
            }
        });
    }

    @Override
    public void delById(String sesId) {
        redisTemplate.delete(format(H_SES_ID_KEY, sesId));
    }

    @Override
    public void updateAccess(String sesId) {
        String key = format(H_SES_ID_KEY, sesId);
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                if (connection.hSet(key.getBytes(), SES_FIELD_ACCESS,
                        SerializeUtil.serialize(new Date()))) {
                    redisTemplate.expire(key, Session.TIME_OUT,
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
        String key = format(H_SES_ID_KEY, sesId);
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                if (connection.hSet(key.getBytes(), SES_FIELD_USER_ID,
                        userId.getBytes())) {
                    return 1l;
                } else {
                    return 0l;
                }
            }
        });
    }

}
