package com.example.serverlab03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

class ClientHandler implements Runnable {
    final DataInputStream dis;
    final DataOutputStream dos;
    Socket s;
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.dis = dis;
        this.dos = dos;
        this.s = s;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                received = dis.readUTF();
                System.out.println(received);
                try {
                    HelloController.queue.put(received);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}