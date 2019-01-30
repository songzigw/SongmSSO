package cn.songm.sso.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.songm.common.dao.BaseDaoImpl;
import cn.songm.sso.dao.SessionDao;
import cn.songm.sso.entity.Session;

@Repository("sessionDao")
public class SessionDaoImpl extends BaseDaoImpl<Session> implements SessionDao {

    @Override
    public Session selectOneById(Object id) {
        return super.selectOneById(id);
    }

    @Override
    public void updateAccess(String sesId, long access) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sesId", sesId);
        paramMap.put("access", access);
        this.update(paramMap);
    }

    @Override
    public List<Session> selectListByUid(String userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        return this.selectListByColumn(paramMap);
    }

    @Override
    public void updateUserId(String sesId, String userId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("sesId", sesId);
        paramMap.put("userId", userId);
        this.update(paramMap);
    }
}
