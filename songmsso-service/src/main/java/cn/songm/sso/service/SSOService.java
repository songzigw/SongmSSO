package cn.songm.sso.service;

import java.util.List;

import cn.songm.sso.entity.Session;

/**
 * 单点服务接口
 * 
 * @author zhangsong
 *
 */
public interface SSOService {
    /**
     * 获取Session
     * @param sesId
     * @return
     */
    public Session getSession(String sesId);
    
    /**
     * 客户端报道
     * @param sesId
     * @return
     */
    public Session report(String sesId);

    /**
     * 用户登入登记
     * @param sesId
     * @param userId
     * @param user
     * @return
     */
    public Session login(String sesId, String userId, Object user);

    /**
     * 用户退出登记
     * @param sesId
     */
    public void logout(String sesId);
    
    /**
     * 获取用户ID
     * @param sesId
     * @return
     */
    public String getUserId(String sesId);
    
    /**
     * 获取用户信息
     * @param sesId
     * @return
     */
    public Object getUser(String sesId);

    /**
     * 设置属性
     * @param sesId
     * @param itemKey
     * @param itemVal
     */
    public void setAttr(String sesId, String itemKey, Object itemVal);

    /**
     * 获取单个属性
     * @param sesId
     * @param itemKey
     * @return
     */
    public Object getAttr(String sesId, String itemKey);
    
    public void delAttr(String sesId, String itemKey);
    
    /**
     * 编辑用户信息
     * @param sesId
     * @param user
     */
    public void editUser(String userId, Object user);

    /**
     * 获取用户的所有Session
     * @param userId
     * @return
     */
    public List<Session> getSessionsByUid(String userId);
    
    /**
     * 获取所有在线的Session
     * @return
     */
    public List<Session> getSessionsOnline();
    
}
