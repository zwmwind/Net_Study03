package com.zwm.chat03;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author zhangweiming
 * @version V1.0
 * @className Send
 * @description //TODO 发送端
 * @date 4:40 PM 2018/10/25
 */
public class Send implements Runnable {
    private BufferedReader console;
    private DataOutputStream dos;
    private Socket client;
    private boolean isRunning;
    public Send(Socket client) {
        this.client = client;
        console = new BufferedReader(new InputStreamReader(System.in));
        try {
            dos = new DataOutputStream(client.getOutputStream());
            isRunning = true;
        } catch (IOException e) {
            e.printStackTrace();
            this.release();
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            String str = getStrFromConsole();
            if (!str.equals("")) {
                send(str);
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
    //从控制台获得消息
    private String getStrFromConsole() {
        String str = null;
        try {
            str =  console.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    private void release() {
        isRunning = false;
        zwmUtils.close(dos,client);
    }
}
