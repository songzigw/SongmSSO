package cn.songm.sso.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.songm.common.utils.Sequence;
import cn.songm.sso.entity.Session;
import cn.songm.sso.redis.SessionRedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-sso-mybatis-test.xml"})
@Transactional
public class SessionDaoTest {

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private SessionRedis sessionRedis;

    private Session createSession() {
        String sesId = Sequence.getInstance().getSequence(28);
        Session s = new Session(sesId);
        int i = sessionDao.insert(s);
        Assert.assertTrue("会话数据插入不等于1", i == 1);
        return s;
    }
    
    @Test
    public void testInsert() {
        Session s = createSession();
        Session s2 = sessionDao.selectOneById(s.getSesId());
        Assert.assertEquals("插入数据前后不一致", s, s2);
    }
    
    @Test
    public void testSelectOneById() {
        // 创建Session
        Session s = createSession();
        // 从缓存中清除
        sessionRedis.delById(s.getSesId());
        // 缓存中没有数据，从数据库中查询，并同步到缓存
        Session s2 = sessionDao.selectOneById(s.getSesId());
        Assert.assertEquals(s, s2);
        Session s3 = sessionRedis.selectById(s.getSesId());
        Assert.assertEquals(s2, s3);
    }
    
    /**
     * 修改会话访问时间
     */
    @Test
    public void testUpdateAccess() {
        // 创建会话
        String sesId = Sequence.getInstance().getSequence(28);
        Session s = new Session(sesId);
        sessionDao.insert(s);
        // 修改会话实时访问时间
        sessionDao.updateAccess(sesId, System.currentTimeMillis());
        // 查询修改后的结果，version+1，updated已修改，access已修改
        Session s2 = sessionDao.selectOneById(sesId);
        Assert.assertTrue("数据版本号没有加1", s2.getVersion().equals(s.getVersion()+1));
        Assert.assertFalse("数据更新值没有变化", s2.getUpdated().equals(s.getUpdated()));
        Assert.assertFalse("数据访问值没有变化", s.getAccess().equals(s2.getAccess()));
    }
    
    /**
     * 查询用户所有的Session
     */
    @Test
    public void testSelectListByUid() {
        String userId = "10000";
        Session s1 = new Session(Sequence.getInstance().getSequence(28));
        s1.setUserId(userId);
        Session s2 = new Session(Sequence.getInstance().getSequence(28));
        s2.setUserId(userId);
        Session s3 = new Session(Sequence.getInstance().getSequence(28));
        s3.setUserId(userId);
        List<Session> list = new ArrayList<>();
        list.add(s1);
        list.add(s2);
        list.add(s3);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        for (Session s : list) {
            s.init();
            c.add(Calendar.MINUTE, -1);
            s.setCreated(c.getTime());
        }
        sessionDao.insert(list);
        
        List<Session> list2 = sessionDao.selectListByUid(userId);
        Assert.assertArrayEquals("查询出的用户session和预期不一致", list.toArray(), list2.toArray());
    }
}
