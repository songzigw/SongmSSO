package cn.songm.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.songm.sso.dao.SessionDao;
import cn.songm.sso.service.SongmUserService;

@Service("songmUserService")
public class SongmUserServiceImpl implements SongmUserService {

    //private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SessionDao sessionDao;
    
    @Override
    public void editUserInfo(String userId, String userInfo) {
        sessionDao.updateUserByUid(userId, userInfo);
    }
}
