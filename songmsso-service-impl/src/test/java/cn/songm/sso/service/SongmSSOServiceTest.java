package cn.songm.sso.service;

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
        System.out.print(ses);
    }

    @Test
    public void testGet() {
        Session ses = songmSSOService.report("6UI28LCT3QRBK38CUUJGZON7Q0BZ");
        System.out.print(ses);
    }
}
