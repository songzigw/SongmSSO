package songm.sso.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.Constants;
import songm.sso.entity.Account;
import songm.sso.entity.Protocol;
import songm.sso.operation.Operation;
import songm.sso.operation.SSOOperation;
import songm.sso.service.AuthService;

@Component
@ChannelHandler.Sharable
public class SSOServerHandler extends SimpleChannelInboundHandler<Protocol> {

    private static final Logger LOG = LoggerFactory
            .getLogger(SSOServerHandler.class);

    @Autowired
    private SSOOperation ssoOperation;
    @Autowired
    private AuthService authService;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Protocol pro)
            throws Exception {
        Operation op = ssoOperation.find(pro.getOperation());
        // execute operation
        if (op != null) {
            LOG.debug(pro.toString());
            op.action(ctx.channel(), pro);
        } else {
            LOG.warn("Not found operationId: " + pro.getOperation());
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Account acc = ctx.attr(Constants.KEY_ACCOUNT).get();
        if (acc != null) {
            authService.quit(acc);
        }
        LOG.debug("handlerRemoved: {}", acc);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        LOG.error("exceptionCaught", cause);
    }
}
