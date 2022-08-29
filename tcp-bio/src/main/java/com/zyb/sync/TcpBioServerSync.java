package com.zyb.sync;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author：Z1084
 * @description：同步阻塞线程
 * @create：2022-08-26 10:23
 */
public class TcpBioServerSync {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            //获取到连接的信息
            System.out.println("等待连接");
            Socket accept = serverSocket.accept();
            System.out.println("有连接进来了");
            handler(accept);

        }
    }

    public static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println("准备read。。");
        //这里也会进行阻塞
        int count = socket.getInputStream().read(bytes);
        if (count != -1) {
            System.out.println("读取到的数据:" + new String(bytes, 0, count));
        }
        //回复消息
        socket.getOutputStream().write("hello client bio".getBytes());
        socket.getOutputStream().flush();

    }
}
