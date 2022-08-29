package com.zyb;

import java.io.IOException;
import java.net.Socket;

/**
 * @author：Z1084
 * @description：
 * @create：2022-08-26 10:53
 */
public class TcpBioClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8000);
        socket.getOutputStream().write("我是客户端".getBytes());
        socket.getOutputStream().flush();
        byte[] bytes = new byte[1024];
        int read = socket.getInputStream().read(bytes);
        if (read != -1) {
            System.out.println("收到的消息:" + new String(bytes, 0, read));
        }
    }
}
