package com.zwm.chat03;

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
            new Thread(new Channel(client)).start();
        }
    }

    //一个客户代表一个Channel
    static class Channel implements Runnable {
        private DataOutputStream dos = null;
        private DataInputStream dis = null;
        private boolean isRunning;
        private Socket client;

        public Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }

        }

        //接受消息
        private String receive() {
            String msg = null;
            try {
                msg = dis.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }
            return msg;
        }
        //发送消息
        private void send(String msg) {
            try {
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                release();
            }
        }

        //释放资源
        private void release() {
            this.isRunning = false;
            zwmUtils.close(dis, dos, client);
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
                    send(msg);
                }
            }
        }
    }
}
