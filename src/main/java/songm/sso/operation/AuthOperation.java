package songm.sso.operation;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.JsonUtils;
import songm.sso.entity.Account;
import songm.sso.entity.Protocol;
import songm.sso.service.AccountService;

@Component
public class AuthOperation extends AbstractOperation {

    private final Logger logger = LoggerFactory.getLogger(AuthOperation.class);

    public static final int OP = 0;
    public static final int OP_REPLY = 1;

    @Autowired
    private AccountService accountService;

    @Override
    public Integer op() {
        return OP;
    }

    @Override
    public void action(Channel ch, Protocol pro) throws Exception {
        Account acc = JsonUtils.fromJson(pro.getBody(), Account.class);

        // check token
        if (accountService.auth(acc)) {
            // put auth token
            setAccount(ch, acc);
            logger.debug("auth ok");
        } else {
            logger.debug("auth fail");
        }

        pro.setOperation(OP_REPLY);
        ch.writeAndFlush(pro);
    }

}
