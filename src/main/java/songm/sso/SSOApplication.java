/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */
package songm.sso;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 单点登入应用
 *
 * @author zhangsong
 * @since 0.1, 2016-8-9
 * @version 0.1
 * 
 */
@Component
public class SSOApplication implements SSOServer {

    private static Logger LOG = LoggerFactory.getLogger(SSOApplication.class);

    @Resource(name = "tcpSSOServer")
    private SSOServer tcpSSOServer;
    @Resource(name = "wsocketSSOServer")
    private SSOServer wsocketSSOServer;

    @Override
    public void start() throws SSOException {
        LOG.info("SongSSO Server starting");

        tcpSSOServer.start();
        wsocketSSOServer.start();

        LOG.info("SongSSO Server start finish");
    }

    @Override
    public void restart() throws SSOException {
        LOG.info("SongSSO Server restart...");

        tcpSSOServer.restart();
        wsocketSSOServer.restart();

        LOG.info("SongSSO Server restart finish");
    }

    @Override
    public void shutdown() {
        LOG.info("SongSSO Server shutdown...");

        tcpSSOServer.shutdown();
        wsocketSSOServer.shutdown();

        LOG.info("SongSSO Server shutdown finish");
    }
}
