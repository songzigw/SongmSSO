package cn.songm.sso.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.songm.sso.entity.Session;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-sso.xml" })
public class SongmSSOServiceTest {

    @Autowired
    private SongmSSOService songmSSOService;

    @Test
    public void testReport() {
        Session ses = songmSSOService.report(null);
        if (ses == null) {
            Assert.fail();
        }
        Session ses2 = songmSSOService.report(ses.getSesId());
        Assert.assertEquals(ses.getSesId(), ses2.getSesId());
        songmSSOService.logout(ses.getSesId());
    }

}
