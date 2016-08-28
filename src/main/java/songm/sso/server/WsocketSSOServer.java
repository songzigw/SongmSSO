/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */

package songm.sso.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import songm.sso.SSOException;
import songm.sso.SSOServer;
import songm.sso.SSOException.ErrorCode;

/**
 * WebSocket连接服务，实现单点登入
 *
 * @author zhangsong
 * @since 0.1, 2016-8-9
 * @version 0.1
 * 
 */
@Component("wsocketSSOServer")
public class WsocketSSOServer implements SSOServer {

    private static final Logger LOG = LoggerFactory
            .getLogger(WsocketSSOServer.class);

    @Value("${server.websocket.port}")
    private int port;
    @Autowired
    private WsocketServerInitializer serverInitializer;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workGroup;
    private ChannelFuture channelFuture;

    public WsocketSSOServer() {
        bossGroup = new NioEventLoopGroup();
        workGroup = new NioEventLoopGroup();
    }

    @Override
    public void start() throws SSOException {
        LOG.info("Starting WebSocketSSOServer... Port:{}", port);

        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(serverInitializer);
            channelFuture = b.bind(port).sync();
        } catch (InterruptedException e) {
            String message = "Start WebSocketSSOServer failure";
            LOG.error(message, e);
            throw new SSOException(ErrorCode.START_ERROR, message, e);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    shutdown();
                }
            });
        }
    }

    @Override
    public void restart() throws SSOException {
        shutdown();
        start();
    }

    @Override
    public void shutdown() {
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
    }

}
