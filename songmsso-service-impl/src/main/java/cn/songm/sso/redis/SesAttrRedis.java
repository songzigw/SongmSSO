package cn.songm.sso.redis;

public interface SesAttrRedis {

    public void insertAttr(String sesId, String attrKey, Object attrVal);

    public Object selectAttr(String sesId, String attrKey);
}
