package com.zwm.chat04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zhangweiming
 * @version V1.0
 * @className Chat
 * @description //TODO 在线聊天室:服务器 实现多个客户
 * 目标：加入容器实现群聊
 * @date 10:56 AM 2018/10/25
 */
public class Chat {
    private static CopyOnWriteArrayList<Channel> all = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("---Server---");

        ServerSocket server = new ServerSocket(8888);
        boolean isRunning = true;

        //阻塞式接受连接
        while (true) {
            Socket client = server.accept();
            System.out.println("一个客户端建立了连接");
            Channel c = new Channel(client);
            all.add(c);
            new Thread(c).start();
        }
    }

    //一个客户代表一个Channel
    static class Channel implements Runnable {
        private DataOutputStream dos = null;
        private DataInputStream dis = null;
        private boolean isRunning;
        private Socket client;
        private String name;

        public Channel(Socket client) {
            this.client = client;
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                isRunning = true;
                //获取名称
                name = receive();
                this.send("welcome");
                sendOthers(this.name + "来了");
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

        //群聊：获取自己的消息，发给其他人
        //私聊：@xxx:msg
        private void sendOthers(String msg) {

            boolean isPrivate = msg.startsWith("@");
            if (isPrivate) {
                //获取目标和数据
                int idx = msg.indexOf(":");
                String targetName = msg.substring(1, idx);
                msg = msg.substring(idx + 1);
                for (Channel other: all) {
                    if (other.name.equals(targetName)) {
                        other.send(this.name + "悄悄对你说" + msg);
                    }
                }
            } else {
                for (Channel other : all) {
                    if (other != this) {
                        other.send(this.name + "对所有人说" + msg);
                    }
                }
            }
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
            all.remove(this);
            sendOthers(this.name + "bye~");
        }

        @Override
        public void run() {
            while (isRunning) {
                String msg = receive();
                if (!msg.equals("")) {
                    //send(msg);
                    sendOthers(msg);
                }
            }
        }
    }
}
