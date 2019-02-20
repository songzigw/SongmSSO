package cn.songm.sso.redis;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:app-sso-redis-test.xml"})
public class SesAttrRedisTest {

    @Autowired
    private SesAttrRedis sesAttrRedis;
    
    /**
     * 添加session的属性
     */
    @Test
    public void testInsertAttr() {
        String sesId = "abcdefg";
        String attrKey = "attrk1";
        String attrVal = "attrv1";
        sesAttrRedis.insertAttr(sesId, attrKey, attrVal);
        
        String val = (String)sesAttrRedis.selectAttr(sesId, attrKey);
        System.out.println(val);
        Assert.assertEquals(val, attrVal);
    }
}
