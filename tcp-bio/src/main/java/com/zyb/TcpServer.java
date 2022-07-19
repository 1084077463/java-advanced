package com.zyb;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author：Z1084
 * @description：BIO模型 总结：使用默认的bio模型时,只要获取到一个Socket连接，就可以一直从socket中去写入和获取数据。下面这种方式使用多线程的方式来进行处理
 * 每创建一个socket就启动一个线程去进行操作，如果客户端数据较多时，容易将cpu跑满。这也就是为啥会退出轮询的模式来进行操作。
 * @create：2022-07-18 17:51
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("有新连接：" + socket.getRemoteSocketAddress().toString() + ",端口:" + socket.getPort());
            SocketThread socketThread = new TcpServer.SocketThread(socket);
            socketThread.start();

        }
    }


    static class SocketThread extends Thread {
        private Socket socket;
        private DataInputStream inputStream;
        private OutputStream outputStream;

        SocketThread(Socket socket) throws IOException {
            this.socket = socket;
            this.inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            this.outputStream = socket.getOutputStream();
            socket.setSoTimeout(0);
        }

        @Override
        public void run() {
            System.out.println("线程执行");
            while (true) {
                try {
                    int length = inputStream.available();
                    if (length > 0) {
                        final byte[] msgArray = new byte[length];
                        inputStream.readFully(msgArray, 0, length);
                        System.out.println("socket:" + socket.getPort() + "打印的内容:" + new String(msgArray));
                        outputStream.write(msgArray);
                        outputStream.flush();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
