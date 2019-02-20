package cn.songm.sso.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.songm.sso.entity.Session;
import cn.songm.sso.json.JsonUtilsInit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-sso.xml" })
@Transactional
public class SSOServiceTest {

    @Autowired
    private SSOService ssoService;

    @BeforeClass
    public static void setUpBeforeClass() {
        JsonUtilsInit.initialization();
    }

    @AfterClass
    public static void tearDownAfterClass() {

    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }
    
    /**
     * 用户报道前在没有传递SessionId的情况下
     * 服务器给客户端分配一个全新SessionId
     * 以后每次报道相关属性要发生变化，尤其是access属性
     */
    @Test
    public void testReport() {
        Session ses = ssoService.report(null);
        Assert.assertNotNull("服务器分配Session失败", ses);
        Session ses2 = ssoService.report(ses.getSesId());
        Assert.assertEquals(ses.getSesId(), ses2.getSesId());
        Assert.assertFalse(ses.getAccess().equals(ses2.getAccess()));
    }

    /**
     * 在SessionId不存在（过期）的情况下
     * 系统会分配一个全新的SessionId
     */
    @Test
    public void testReport2() {
        String sesId = "abcedrfadafsg";
        Session ses = ssoService.getSession(sesId);
        if (ses != null) throw new RuntimeException();
        // 重新分配Session
        ses = ssoService.report(sesId);
        Assert.assertNotNull("服务器分配Session失败", ses);
        Assert.assertFalse("从新分配的SessionId不能与旧的相同", sesId.equals(ses.getSesId()));
    }
}
