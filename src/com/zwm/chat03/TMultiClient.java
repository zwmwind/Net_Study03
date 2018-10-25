package com.zwm.chat03;

import java.io.*;
import java.net.Socket;

/**
 *@className Client
 *@description //TODO 在线聊天室：客户端 实现多个客户
 *
 *@author zhangweiming
 *@date 10:57 AM 2018/10/25
 *@version V1.0
 */
public class TMultiClient {
    public static void main(String[] args) throws IOException {
        System.out.println("---Client---");

        //1.建立连接
        Socket client = new Socket("localhost", 8888);
        new Thread(new Send(client)).start();
        new Thread(new Receive(client)).start();
    }
}
