package cn.songm.sso.redis;

public interface UserRedis {

    public void insert(String userId, Object user);

    public Object selectByUid(String userId);

}
