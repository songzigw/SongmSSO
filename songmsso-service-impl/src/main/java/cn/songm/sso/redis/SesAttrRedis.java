package cn.songm.sso.redis;

import cn.songm.common.redis.BaseRedis;

public interface SesAttrRedis extends BaseRedis<Void> {

    public void insertAttr(String sesId, String attrKey, Object attrVal);

    public Object selectAttr(String sesId, String attrKey);
}
