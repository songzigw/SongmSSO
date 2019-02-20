package cn.songm.sso.redis.impl;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.common.redis.annotations.HashField;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.SessionRedis;

@Repository("sessionRedis")
public class SessionRedisImpl extends BaseRedisImpl<Session>
        implements SessionRedis {

    public static final String L_SESSION_KEY = "sso_l_session";
    public static final String H_SESSION_KEY = "sso_h_session/%s";

    public static final String SES_FIELD_ACCESS = "access";
    public static final String SES_FIELD_USER_ID = "user_id";
    public static final String  SES_FIELD_VERSION = "version";
    public static final String  SES_FIELD_UPDATE = "updated";
    
    public Session serialize(Session ses, RedisConnection connection) {
        return null;
    }

    public Session unserialize(Session ses, RedisConnection connection) {
        return null;
    }

    public void serialize(String key,  Object obj) {
        Map<String, Object> map = new HashMap<>();

        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fids = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fids, true);
            try {
                for (Field f : fids) {
                    if (f.isAnnotationPresent(HashField.class)) {
                        HashField hm = f.getAnnotation(HashField.class);
                        map.put(hm.value(), f.get(obj));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        this.hmset(key, map);
    }
    
    public Object unserialize(String key, Class<?> cla) {
        if (!this.hasKey(key)) return null;
        Map<Object, Object> map = this.hmget(key);
        
        Object obj = null;
        try {
            obj = cla.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        Class<?> clazz = obj.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fids = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fids, true);
            try {
                for (Field f : fids) {
                    if (f.isAnnotationPresent(HashField.class)) {
                        HashField hm = f.getAnnotation(HashField.class);
                        f.set(obj, map.get(hm.value()));
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        
        return obj;
    }
    
    @Override
    public Session insert(Session session) {
        session.init();
        String key = String.format(H_SESSION_KEY, session.getSesId());
        serialize(key, session);
        this.expire(key, Session.TIME_OUT);
        return session;
    }

    @Override
    public Session selectById(String sesId) {
        String key = String.format(H_SESSION_KEY, sesId);
        return (Session) unserialize(key, Session.class);
    }

    @Override
    public void delById(String sesId) {
        this.del(String.format(H_SESSION_KEY, sesId));
    }

    @Override
    public void updateAccess(String sesId, Session ses) {
        String key = String.format(H_SESSION_KEY, sesId);
        this.hset(key, SES_FIELD_VERSION, ses.getVersion());
        this.hset(key, SES_FIELD_UPDATE, ses.getUpdated());
        this.hset(key, SES_FIELD_ACCESS, ses.getAccess());
        this.expire(key, Session.TIME_OUT);
    }

    @Override
    public void updateUserId(String sesId, Session ses) {
        String key = String.format(H_SESSION_KEY, sesId);
        this.hset(key, SES_FIELD_VERSION, ses.getVersion());
        this.hset(key, SES_FIELD_UPDATE, ses.getUpdated());
        this.hset(key, SES_FIELD_USER_ID, ses.getUserId());
    }

}
