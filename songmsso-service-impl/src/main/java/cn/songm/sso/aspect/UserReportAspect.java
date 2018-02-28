package cn.songm.sso.aspect;

import javax.jms.JMSException;
import javax.jms.Message;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cn.songm.common.utils.JsonUtils;
import cn.songm.sso.entity.Session;

public class UserReportAspect {

    @Autowired
    private JmsTemplate jmsTemplate;

    private static final Logger LOG = LoggerFactory
            .getLogger(UserReportAspect.class);

    public void afterReturning(JoinPoint point, Session result) {
        String cName = point.getTarget().getClass().getName();
        String mName = point.getSignature().getName();
        LOG.info(cName + "." + mName + " Return: {}", result);
        jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(javax.jms.Session s)
                    throws JMSException {
                String str = JsonUtils.getInstance().toJson(result, Session.class);
                JsonObject jObj = new JsonParser().parse(str).getAsJsonObject();
                Object[] objs = point.getArgs();
                //jObj.addProperty("url", objs[1].toString());
                return s.createTextMessage(jObj.toString());
            }
        });
    }
}
