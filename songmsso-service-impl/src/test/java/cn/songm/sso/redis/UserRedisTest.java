package cn.songm.sso.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:app-sso-redis-test.xml" })
public class UserRedisTest {

    @Autowired
    private UserRedis userRedis;

    private User insertUser() {
        String userId = "10000";
        User user = new User();
        user.setUserId(userId);
        userRedis.insert(userId, user);
        return user;
    }

    /**
     * 插入用户对象
     */
    @Test
    public void testInsert() {
        User u = insertUser();
        User u2 = (User) userRedis.selectByUid(u.getUserId());
        Assert.assertEquals(u.toString(), u2.toString());
    }

}
