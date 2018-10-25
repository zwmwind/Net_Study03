package com.zwm.chat02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author zhangweiming
 * @version V1.0
 * @className Chat
 * @description //TODO 在线聊天室:服务器 实现多个客户
 * 目标：解决上述问题，用多线程
 * 问题：1.代码不好维护
 *      2.客户端读写没有分开，必须先写后读
 * 需要封装
 *
 * @date 10:56 AM 2018/10/25
 */
public class TMultiChat {
    public static void main(String[] args) throws IOException {
        System.out.println("---Server---");

        ServerSocket server = new ServerSocket(8888);
        boolean isRunning = true;

        //阻塞式接受连接
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");

            new Thread(() -> {
                DataOutputStream dos = null;
                try {
                    dos = new DataOutputStream(client.getOutputStream());
                    DataInputStream dis = new DataInputStream(client.getInputStream());
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
                } catch (IOException e) {
                    e.printStackTrace();
                    //isRunning = false;
                }

            }).start();
        }
    }
}
