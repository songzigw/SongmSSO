package cn.songm.sso.redis;

import cn.songm.common.redis.BaseRedis;
import cn.songm.sso.entity.Session;

public interface SessionRedis extends BaseRedis<Session> {

    /**
     * 插入Session数据
     * @param session
     * @return
     */
    public Session insert(Session session);
    
    /**
     * 查询Session数据
     * @param sesId
     * @return
     */
    public Session selectById(String sesId);
    
    public void delById(String sesId);
    
    public void updateAccess(String sesId);
    
    public void updateUserId(String sesId, String userId);

}
