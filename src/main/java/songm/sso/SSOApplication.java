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

    public void setTcpSSOServer(SSOServer tcpSSOServer) {
        this.tcpSSOServer = tcpSSOServer;
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
        tcpSSOServer.start();
        LOG.info("SongSSO Server start finish");
    }

    @Override
    public void restart() throws SSOException {
        tcpSSOServer.restart();
        LOG.info("SongSSO Server restart finish");
    }

    @Override
    public void shutdown() {
        tcpSSOServer.shutdown();
        LOG.info("SongSSO Server shutdown finish");
    }
}
