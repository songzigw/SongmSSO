package cn.songm.sso.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.songm.common.utils.Sequence;
import cn.songm.sso.entity.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-sso-redis-test.xml"})
public class SessionRedisTest {

    @Autowired
    private SessionRedis sessionRedis;
    
    @Test
    public void testInsert() {
        String sesId = Sequence.getInstance().getSequence(28);
        Session session = new Session(sesId);
        session.init();
        session.setUserId("10000");
        sessionRedis.insert(session);
    }
    
    @Test
    public void testSelectById() {
        String sesId = Sequence.getInstance().getSequence(28);
        Session session = new Session(sesId);
        session.init();
        session.setUserId("10000");
        sessionRedis.insert(session);
        Session s2 = sessionRedis.selectById("abcdefg");
        Assert.assertEquals("保存sesion和查询的不一致", s2, session);
    }
    
}
