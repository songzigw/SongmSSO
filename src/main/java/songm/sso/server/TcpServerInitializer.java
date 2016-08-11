/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */

package songm.sso.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Tcp管道初始化
 *
 * @author zhangsong
 * @since 0.1, 2016-8-9
 * @version 0.1
 * 
 */
@Component
public class TcpServerInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private TcpProtocolCodec protocolCodec;
    @Autowired
    private SSOServerHandler serverHandler;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(protocolCodec);
        ch.pipeline().addLast(serverHandler);
    }

}
