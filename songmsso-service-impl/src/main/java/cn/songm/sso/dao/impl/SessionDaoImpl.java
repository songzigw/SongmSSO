package cn.songm.sso.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.songm.common.dao.BaseDaoImpl;
import cn.songm.sso.dao.SessionDao;
import cn.songm.sso.entity.Session;

@Repository("sessionDao")
public class SessionDaoImpl extends BaseDaoImpl<Session> implements SessionDao {

    private static final String SQL_UPDATE_BY_USERID = "updateByUserId";
    
    @Override
    public int updateUserByUid(String userId, String userInfo) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("updated", new Date());
        paramMap.put("userId", userId);
        paramMap.put("userInfo", userInfo);
        return sessionTemplate.update(getStatement(SQL_UPDATE_BY_USERID), paramMap);
    }
}
