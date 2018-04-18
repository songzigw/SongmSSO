package cn.songm.sso.service;

public interface SongmUserService {

    /**
     * 编辑用户信息
     * @param sessionId
     * @param userInfo
     */
    public void editUserInfo(String userId, String userInfo);
}
