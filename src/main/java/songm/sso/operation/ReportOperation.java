package songm.sso.operation;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.SSOException;
import songm.sso.entity.Protocol;
import songm.sso.entity.Session;
import songm.sso.service.SessionService;

@Component
public class ReportOperation extends AbstractOperation {

    private final Logger LOG = LoggerFactory.getLogger(ReportOperation.class);

    public static final int OP = 2;
    public static final int OP_REPLY = 3;

    @Autowired
    private SessionService sessionService;

    @Override
    public int operation() {
        return OP;
    }

    @Override
    public void action(Channel ch, Protocol pro) throws SSOException {
        this.checkAuth(ch);

        String sessionId = new String(pro.getBody());
        Session ses = sessionService.create(sessionId);
        LOG.debug("User report sessionId:{}", ses.getId());

        pro.setOperation(OP_REPLY);
        pro.setBody(ses.getId().getBytes());
        ch.writeAndFlush(pro);
    }

}
