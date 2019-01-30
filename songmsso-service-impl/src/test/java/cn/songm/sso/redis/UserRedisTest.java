package cn.songm.sso.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-sso-redis-test.xml"})
public class UserRedisTest {

    @Autowired
    private UserRedis userRedis;
    
    /**
     * 查询用户对象，根据UserId
     */
    @Test
    public void testSelectUserByUid() {
        String userId = "10000";
        userRedis.selectUserByUid(userId);
    }
    
    /**
     * 插入用户对象
     */
    @Test
    public void testInsertUser() {
        String userId = "10000";
        User user = new User();
        user.setUserId(userId);
        userRedis.insertUser(userId, user);
    }
    
    /**
     * 查询用户所有SessionID
     */
    @Test
    public void testSelectSesIdsByUid() {
        String userId = "10000";
        //List<String> l = userRedis.selectSesIdsByUid(userId);
    }

    /**
     * 插入用户的SessionId
     */
    @Test
    public void testInsertUserSesId() {
        String userId = "10000";
        String sesId = "abcdefghijklmn";
        //userRedis.insertUserSesId(userId, sesId);
    }
    
}
