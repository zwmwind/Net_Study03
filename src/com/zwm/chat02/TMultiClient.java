package com.zwm.chat02;

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
        boolean isRunning = true;
        DataInputStream dis = new DataInputStream(client.getInputStream());
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        //2.客户端发送消息
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
        while (isRunning) {
            String msg = console.readLine();

            dos.writeUTF(msg);
            dos.flush();

            //获取消息
            msg = dis.readUTF();
            System.out.println(msg);


        }
        //释放资源
        dos.close();
        dis.close();
        client.close();
    }
}
