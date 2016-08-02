/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */
package songm.sso;

import java.net.Socket;

/**
 * 客户端连接请求线程
 *
 * @author zhangsong
 * @since 0.1, 2016-8-2
 * @version 0.1
 * 
 */
public class ServerThread extends Thread {

    // 服务器与客户端之间的socket
    Socket client;

    public ServerThread(Socket client) {
        this.client = client;
    }
}
