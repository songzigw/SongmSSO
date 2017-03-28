package cn.songm.sso.redis.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import cn.songm.common.redis.BaseRedisImpl;
import cn.songm.sso.entity.Attribute;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.AttributeRedis;
import cn.songm.sso.redis.Database.SsoTabs;

@Repository("attributeRedis")
public class AttributeRedisImpl extends BaseRedisImpl
        implements AttributeRedis {

    protected RedisSerializer<String> getSerializer() {
        return redisTemplate.getStringSerializer();
    }

    private String getRedisKey(String sesId) {
        StringBuilder sbui = new StringBuilder();
        sbui.append(SsoTabs.SSO_SESSION).append("/").append(sesId).append("/")
                .append(SsoTabs.SSO_ATTRIBUTE);
        return sbui.toString();
    }

    private Attribute serialize(Attribute attr, RedisConnection connection) {
        Map<byte[], byte[]> d = new HashMap<byte[], byte[]>();
        d.put(attr.getKey().getBytes(), attr.getValue().getBytes());

        connection.hMSet(getRedisKey(attr.getSesId()).getBytes(), d);
        redisTemplate.expire(getRedisKey(attr.getSesId()), Session.TIME_OUT,
                TimeUnit.MILLISECONDS);
        return attr;
    }

    private Attribute unserialize(String sesId, String key,
            RedisConnection connection) {
        if (!connection.exists(getRedisKey(sesId).getBytes())) {
            return null;
        }
        if (!connection.hExists(getRedisKey(sesId).getBytes(), key.getBytes())) {
            return null;
        }
        String val = new String(
                connection.hGet(getRedisKey(sesId).getBytes(), key.getBytes()));
        return new Attribute(sesId, key, val);
    }

    @Override
    public void insert(Attribute attr) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                if (connection
                        .exists(getRedisKey(attr.getSesId()).getBytes())) {
                    connection.hSet(getRedisKey(attr.getSesId()).getBytes(),
                            attr.getKey().getBytes(),
                            attr.getValue().getBytes());
                } else {
                    serialize(attr, connection);
                }
                return 1l;
            }
        });
    }

    @Override
    public Attribute queryAttrById(String sesId, String key) {
        return redisTemplate.execute(new RedisCallback<Attribute>() {
            @Override
            public Attribute doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return unserialize(sesId, key, connection);
            }
        });
    }

    @Override
    public void delById(String sesId, String key) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.hDel(getRedisKey(sesId).getBytes(),
                        key.getBytes());
            }
        });
    }

    @Override
    public void delAttrsBySesId(String sesId) {
        redisTemplate.delete(getRedisKey(sesId));
    }

    @Override
    public void updateAccess(String sesId) {
        redisTemplate.expire(getRedisKey(sesId), Session.TIME_OUT,
                TimeUnit.MILLISECONDS);
    }

}
