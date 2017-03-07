package cn.songm.sso.redis.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
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
        return SsoTabs.SSO_SESSION + ":" + sesId;
    }

    @Override
    public Session insert(Session session) {
        String rkey = getRedisKey(session.getSesId());
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(rkey);

        Map<String, Object> data = new HashMap<String, Object>();
        data.put(SessionF.SES_ID.name(), session.getSesId());
        data.put(SessionF.USER_ID.name(), session.getUserId());
        data.put(SessionF.CREATED.name(), session.getCreated());
        data.put(SessionF.ACCESS.name(), session.getAccess());

        ops.putAll(data);
        redisTemplate.expire(rkey, Session.TIME_OUT, TimeUnit.MILLISECONDS);
        return session;
    }

    @Override
    public Session queryById(String sesId) {
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(getRedisKey(sesId));
        Session ses = new Session((String) ops.get(SessionF.SES_ID.name()));
        ses.setUserId((String) ops.get(SessionF.USER_ID.name()));
        ses.setCreated((Date) ops.get(SessionF.CREATED.name()));
        ses.setAccess((Date) ops.get(SessionF.ACCESS.name()));
        return ses;
    }

    @Override
    public void delById(String sesId) {
        redisTemplate.delete(getRedisKey(sesId));
    }

    @Override
    public void updateAccess(String sesId) {
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(getRedisKey(sesId));
        ops.put(SessionF.CREATED.name(), new Date());
        redisTemplate.expire(getRedisKey(sesId), Session.TIME_OUT,
                TimeUnit.MILLISECONDS);
    }

    @Override
    public void updateUserId(String sesId, String userId) {
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(getRedisKey(sesId));
        ops.put(SessionF.USER_ID.name(), userId);
    }

}
