package cn.songm.sso.redis.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.sso.entity.Attribute;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.AttributeRedis;
import cn.songm.sso.redis.Database.SsoTabs;

@Repository("attributeRedis")
public class AttributeRedisImpl extends BaseRedisImpl implements AttributeRedis {

    protected RedisSerializer<String> getSerializer() {
        return redisTemplate.getStringSerializer();
    }
    
    private String getKey(String sesId) {
        return SsoTabs.SSO_ATTRIBUTE + ":SESSION:" + sesId;
    }

    @Override
    public void insert(Attribute attr) {
        String rkey = getKey(attr.getSesId());
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(rkey);

        ops.put(attr.getKey(), attr.getValue());

        redisTemplate.expire(rkey, Session.TIME_OUT, TimeUnit.MILLISECONDS);
    }

    @Override
    public Attribute queryAttrById(String sesId, String key) {
        String rkey = getKey(sesId);
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(rkey);
        return new Attribute(sesId, key, (String) ops.get(key));
    }

    @Override
    public void delById(String sesId, String key) {
        String rkey = getKey(sesId);
        BoundHashOperations<String, String, Object> ops = redisTemplate
                .boundHashOps(rkey);
        ops.delete(key);
    }

    @Override
    public void delAttrsBySesId(String sesId) {
        String rkey = getKey(sesId);
        redisTemplate.delete(rkey);
    }

    @Override
    public void updateAccess(String sesId) {
        String rkey = getKey(sesId);
        redisTemplate.expire(rkey, Session.TIME_OUT, TimeUnit.MILLISECONDS);
    }

}
