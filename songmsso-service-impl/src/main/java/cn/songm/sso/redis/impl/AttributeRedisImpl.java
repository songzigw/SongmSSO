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

@Repository("attributeRedis")
public class AttributeRedisImpl extends BaseRedisImpl<Attribute>
        implements AttributeRedis {

    private static final String SES_ID_ATTR = "ses_id/%s/attr";

    protected RedisSerializer<String> getSerializer() {
        return redisTemplate.getStringSerializer();
    }

    public Attribute serialize(Attribute attr, RedisConnection connection) {
        String key = format(SES_ID_ATTR, attr.getSesId());
        if (connection.exists(key.getBytes())) {
            connection.hSet(key.getBytes(), attr.getKey().getBytes(),
                    attr.getValue().getBytes());
        } else {
            Map<byte[], byte[]> d = new HashMap<byte[], byte[]>();
            d.put(attr.getKey().getBytes(), attr.getValue().getBytes());
            connection.hMSet(key.getBytes(), d);
        }
        redisTemplate.expire(key, Session.TIME_OUT, TimeUnit.MILLISECONDS);
        return attr;
    }

    public Attribute unserialize(Attribute attr, RedisConnection connection) {
        String key = format(SES_ID_ATTR, attr.getSesId());
        if (!connection.exists(key.getBytes())) return null;
        if (!connection.hExists(key.getBytes(), attr.getKey().getBytes()))
            return null;
        attr.setValue(new String(
                connection.hGet(key.getBytes(), attr.getKey().getBytes())));
        return attr;
    }

    @Override
    public void insert(Attribute attr) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                serialize(attr, connection);
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
                Attribute attr = new Attribute(sesId, key, null);
                return unserialize(attr, connection);
            }
        });
    }

    @Override
    public void delById(String sesId, String key) {
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.hDel(format(SES_ID_ATTR, sesId).getBytes(),
                        key.getBytes());
            }
        });
    }

    @Override
    public void delAttrsBySesId(String sesId) {
        redisTemplate.delete(format(SES_ID_ATTR, sesId));
    }

    @Override
    public void updateAccess(String sesId) {
        redisTemplate.expire(format(SES_ID_ATTR, sesId), Session.TIME_OUT,
                TimeUnit.MILLISECONDS);
    }

}
