package cn.songm.sso.dao;

import java.util.List;

import cn.songm.common.dao.BaseDao;
import cn.songm.sso.entity.Session;

public interface SessionDao extends BaseDao<Session> {

    public void updateAccess(String sesId, long access);
    
    public void updateUserId(String sesId, String userId);

    public List<Session> selectListByUid(String userId);
}
