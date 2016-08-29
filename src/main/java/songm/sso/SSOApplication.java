/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */
package songm.sso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单点登入应用
 *
 * @author zhangsong
 * @since 0.1, 2016-8-9
 * @version 0.1
 * 
 */
public class SSOApplication implements SSOServer {

    private static Logger LOG = LoggerFactory.getLogger(SSOApplication.class);

    private SSOServer tcpSSOServer;
    private SSOServer wsocketSSOServer;

    public void setTcpSSOServer(SSOServer tcpSSOServer) {
        this.tcpSSOServer = tcpSSOServer;
    }

    public void setWsocketSSOServer(SSOServer wsocketSSOServer) {
        this.wsocketSSOServer = wsocketSSOServer;
    }

    public void init() {
        try {
            this.start();
        } catch (SSOException e) {
            LOG.error("SongmSSOServer start error", e);
        }
    }
    
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
