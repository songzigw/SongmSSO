package songm.sso.operation;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.JsonUtils;
import songm.sso.entity.Backstage;
import songm.sso.entity.Protocol;
import songm.sso.entity.Session;
import songm.sso.service.BackstageService;
import songm.sso.service.SessionService;

@Component
public class AuthOperation extends AbstractOperation {

    private final Logger logger = LoggerFactory.getLogger(AuthOperation.class);

    public static final int OP = 0;
    public static final int OP_REPLY = 1;

    @Autowired
    private BackstageService backstageService;
    @Autowired
    private SessionService sessionService;

    @Override
    public int operation() {
        return OP;
    }

    @Override
    public void action(Channel ch, Protocol pro) throws Exception {
        Backstage back = JsonUtils.fromJson(pro.getBody(), Backstage.class);

        if (backstageService.auth(back)) {
            setBackstage(ch, back);
            logger.debug("auth ok");

            // 创建session
            Session s = sessionService.create(back);
            pro.setOperation(OP_REPLY);
            pro.setBody(JsonUtils.toJson(s).getBytes());
            ch.writeAndFlush(pro);
        } else {
            logger.debug("auth fail");
        }
    }

}
