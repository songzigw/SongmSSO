/*
 * Copyright (c) 2016, zhangsong <songm.cn>.
 *
 */
package songm.sso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.google.gson.Gson;

/**
 * 后台（后端）应用服务
 *
 * @author zhangsong
 * @since 0.1, 2016-8-2
 * @version 0.1
 * 
 */
public class BackServer extends Thread {

    private static Gson gson = new Gson();

    // 服务器与客户端链接
    private Socket socket;
    // 客户端账号
    private Account account;

    // 用来实现接受从客户端发来的数据流
    private BufferedReader br;
    // 用来实现向客户端发送信息的打印流
    private PrintStream ps;

    private boolean running;

    public BackServer(Socket socket) throws IOException {
        this.socket = socket;
        
        // 初始化输入输出流
        br = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
        ps = new PrintStream(socket.getOutputStream()); 
        // 读取收到的信息，第一条信息是客户端的账号信息
        String clientInfo = br.readLine();
        Account acc = gson.fromJson(clientInfo, Account.class);
    }
}
