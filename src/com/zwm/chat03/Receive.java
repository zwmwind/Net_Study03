package com.zwm.chat03;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *@className Receive
 *@description //TODO 接收端
 *
 *@author zhangweiming
 *@date 4:39 PM 2018/10/25
 *@version V1.0
 */
public class Receive implements Runnable{
    private DataInputStream dis;
    private Socket client;
    private boolean isRunning;
    public Receive(Socket client) {
        this.client = client;
        try {
            dis = new DataInputStream(client.getInputStream());
            isRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
    }
    @Override
    public void run() {
        while (isRunning) {
            String msg = reveive();
            if (!msg.equals("")) {
                System.out.println(msg);
            }
        }
    }

    //接收消息
    private String reveive() {
        String msg = null;
        try {
            msg = dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            release();
        }
        return msg;
    }
    private void release() {
        zwmUtils.close(dis, client);
        isRunning = false;
    }
}
