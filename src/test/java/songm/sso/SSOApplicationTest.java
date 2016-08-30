package songm.sso;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application.xml"}) 
public class SSOApplicationTest {

    @Autowired
    private SSOApplication ssoApplication;
    
    @Test
    public void startTest() {
        try {
            ssoApplication.start();
        } catch (SSOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testRestart() {
        try {
            ssoApplication.restart();
        } catch (SSOException e) {
            e.printStackTrace();
        }
        ssoApplication.shutdown();
    }

    @Test
    public void testShutdown() {
        ssoApplication.shutdown();
    }
}
