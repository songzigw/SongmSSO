package cn.songm.sso.redis;

public interface UserRedis {

    public void insertUser(String userId, Object user);

    public Object selectUserByUid(String userId);

}
