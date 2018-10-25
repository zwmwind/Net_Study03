package com.zwm.chat01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *@className Chat
 *@description //TODO 在线聊天室:服务器
 * 目标：实现一个客户可以正常多条收发消息
 *
 *@author zhangweiming
 *@date 10:56 AM 2018/10/25
 *@version V1.0
 */
public class MultiChat {
    public static void main(String[] args) throws IOException {
        System.out.println("---Server---");

        ServerSocket server = new ServerSocket(8888);
        boolean isRunning = true;

        //阻塞式接受连接
        Socket client = server.accept();
        DataOutputStream dos = new DataOutputStream(client.getOutputStream());
        DataInputStream dis = new DataInputStream(client.getInputStream());
        System.out.println("一个客户端建立了连接");
        while (isRunning) {
            //接受消息
            String datas = dis.readUTF();

            //返回消息
            dos.writeUTF(datas);
            dos.flush();
        }
        //释放资源
        dos.close();
        dis.close();
        client.close();
    }
}
