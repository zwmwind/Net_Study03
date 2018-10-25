package com.zwm.chat04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *@className Client
 *@description //TODO 在线聊天室：客户端 实现多个客户
 *
 *@author zhangweiming
 *@date 10:57 AM 2018/10/25
 *@version V1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("---Client---");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入用户名");


        //1.建立连接
        Socket client = new Socket("localhost", 8888);

        new Thread(new Send(client, br.readLine())).start();
        new Thread(new Receive(client)).start();
    }
}
