package cn.songm.sso.dao.impl;

import org.springframework.stereotype.Repository;

import cn.songm.common.dao.BaseDaoImpl;
import cn.songm.sso.dao.SessionDao;
import cn.songm.sso.entity.Session;

@Repository("sessionDao")
public class SessionDaoImpl extends BaseDaoImpl<Session> implements SessionDao {

}
