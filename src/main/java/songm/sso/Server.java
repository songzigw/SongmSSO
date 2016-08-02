/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */

package songm.sso;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * 单点服务器
 *
 * @author zhangsong
 * @since 0.1, 2016-8-2
 * @version 0.1
 * 
 */
public class Server {

    private static Logger logger = Logger.getLogger(Server.class);

    /** 服务器名称 */
    private String name;
    /** 服务器端口 */
    private int port;

    private ServerSocket server;

    public Server() {
        super();
        this.name = Config.getInstance().getServerName();
        this.port = Config.getInstance().getServerPort();
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        return port;
    }

    public void start() throws IOException {
        // 显示服务名字和端口号
        logger.info(name + " server start, port: " + port);

        server = new ServerSocket(port);
        do {
            // 等待连接请求
            Socket client = server.accept();
            // 为连接请求分配一个线程
            (new ServerThread(client)).start();
        } while (true);
    }

}
