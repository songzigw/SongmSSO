package cn.songm.sso.redis;

import cn.songm.sso.entity.Attribute;

public interface AttributeRedis {

    public void insert(Attribute attr);
    
    public Attribute queryAttrById(String sesId, String key);
    
    public void delById(String sesId, String key);
    
    public void delAttrsBySesId(String sesId);
    
    public void updateAccess(String sesId);
}
