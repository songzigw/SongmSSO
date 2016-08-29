package songm.sso.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import songm.sso.Constants;
import songm.sso.entity.Backstage;
import songm.sso.entity.Protocol;
import songm.sso.operation.Operation;
import songm.sso.operation.SSOOperation;
import songm.sso.service.BackstageService;

@Component
@ChannelHandler.Sharable
public class SSOServerHandler extends SimpleChannelInboundHandler<Protocol> {

    private static final Logger LOG = LoggerFactory
            .getLogger(SSOServerHandler.class);

    @Autowired
    private SSOOperation ssoOperation;
    @Autowired
    private BackstageService authService;

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Protocol pro)
            throws Exception {
        Operation op = ssoOperation.find(pro.getOperation());
        if (op != null) {
            op.action(ctx.channel(), pro);
        } else {
            LOG.warn("Not found operation: " + pro.getOperation());
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Backstage acc = ctx.attr(Constants.KEY_BACKSTAGE).get();
        if (acc != null) {
            authService.quit(acc);
        }
        LOG.debug("HandlerRemoved: {}", acc);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        LOG.error("ExceptionCaught", cause);
    }
}
