/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */

package songm.sso;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 单点登入应用
 *
 * @author zhangsong
 * @since 0.1, 2016-8-9
 * @version 0.1
 * 
 */
@SpringBootApplication
@ComponentScan("songm.sso")
public class SSOApplication implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(SSOApplication.class);

    @Resource(name = "tcpSSOServer")
    private SSOServer tcpSSOServer;
    @Resource(name = "webSocketSSOServer")
    private SSOServer webSocketSSOServer;

    @Override
    public void run(String... args) throws Exception {
        try {
            tcpSSOServer.start();
            webSocketSSOServer.start();

            Thread.currentThread().join();
        } catch (Exception e) {
            LOG.error("startup error!", e);
        }
    }
}
