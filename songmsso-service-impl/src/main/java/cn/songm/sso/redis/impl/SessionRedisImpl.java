package cn.songm.sso.redis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.SessionRedis;

@Repository("sessionRedis")
public class SessionRedisImpl extends BaseRedisImpl<Session>
        implements SessionRedis {

    public static final String SES_FIELD_SESID = "ses_id";
    public static final String SES_FIELD_USER_ID = "user_id";
    public static final String SES_FIELD_CREATED = "created";
    public static final String SES_FIELD_ACCESS = "access";
    
    public static final String L_SESSION_KEY = "sso_l_session";
    public static final String H_SESSION_KEY = "sso_h_session/%s";

    public Session serialize(Session ses, RedisConnection connection) {
        return null;
    }

    public Session unserialize(Session ses, RedisConnection connection) {
        return null;
    }

    @Override
    public Session insert(Session session) {
        session.init();
        Map<String, Object> map = new HashMap<>();
        map.put(SES_FIELD_SESID, session.getSesId());
        map.put(SES_FIELD_USER_ID, session.getUserId());
        map.put(SES_FIELD_CREATED, session.getCreated());
        map.put(SES_FIELD_ACCESS, session.getAccess());
        String key = String.format(H_SESSION_KEY, session.getSesId());
        this.hmset(key, map);
        this.expire(key, Session.TIME_OUT/1000);
        return session;
    }

    @Override
    public Session selectById(String sesId) {
        String key = String.format(H_SESSION_KEY, sesId);
        if (!this.hasKey(key)) return null;
        Map<Object, Object> map = this.hmget(key);
        Session session = new Session();
        session.setSesId(sesId);
        session.setUserId((String)map.get(SES_FIELD_USER_ID));
        session.setCreated((Date)map.get(SES_FIELD_CREATED));
        session.setAccess((Long)map.get(SES_FIELD_ACCESS));
        return session;
    }

    @Override
    public void delById(String sesId) {
        this.del(String.format(H_SESSION_KEY, sesId));
    }

    @Override
    public void updateAccess(String sesId) {
        String key = String.format(H_SESSION_KEY, sesId);
        this.hset(key, SES_FIELD_ACCESS, System.currentTimeMillis());
        this.expire(key, Session.TIME_OUT/1000);
    }

    @Override
    public void updateUserId(String sesId, String userId) {
        String key = String.format(H_SESSION_KEY, sesId);
        this.hset(key, SES_FIELD_USER_ID, userId);
    }

}
