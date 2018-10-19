package cn.songm.sso.service;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.songm.sso.entity.Session;
import cn.songm.sso.json.JsonUtilsInit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-sso.xml" })
public class SongmSSOServiceTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private SongmSSOService songmSSOService;

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
    
    @Test
    public void testReport() {
        Session ses = songmSSOService.report(null);
        if (ses == null) {
            Assert.fail();
        }
        Session ses2 = songmSSOService.report(ses.getSesId());
        Assert.assertEquals(ses.getSesId(), ses2.getSesId());
        //songmSSOService.logout(ses.getSesId());
    }

    
    @Test
    public void testSetValidateCode() {
        songmSSOService.setValidateCode("88PCDC5KJ4MMCMXBUYDJLJ8JAK5V", "897X");
    }
    
    @Test
    public void testGetValidateCode() {
        String vcode = songmSSOService.getValidateCode("98ENE97NFPAAQ9I8TJ2VCCPO4SCN");
        log.info(vcode);
    }
}
