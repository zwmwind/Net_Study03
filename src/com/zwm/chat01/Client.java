package com.zwm.chat01;

import java.io.*;
import java.net.Socket;

/**
 *@className Client
 *@description //TODO 在线聊天室：客户端
 *
 *@author zhangweiming
 *@date 10:57 AM 2018/10/25
 *@version V1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("---Client---");

        //1.建立连接
        Socket client = new Socket("localhost", 8888);

        //2.客户端发送消息
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String msg = console.readLine();

        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        dos.writeUTF(msg);
        dos.flush();

        //获取消息
        DataInputStream dis = new DataInputStream(client.getInputStream());
        msg = dis.readUTF();
        System.out.println(msg);

        //释放资源
        dos.close();
        dis.close();
        client.close();

    }
}
