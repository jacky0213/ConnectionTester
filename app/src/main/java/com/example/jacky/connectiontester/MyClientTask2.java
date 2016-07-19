package com.example.jacky.connectiontester;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MyClientTask2 implements Runnable {
    String modifiedSentence;

    MyClientTask2(){
        run();
    }
    public void run(){
        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("192.168.1.106");
            String str = "Hello Server";
            DatagramPacket packet = new DatagramPacket(str.getBytes(),str.length(),serverAddress,110);
            socket.send(packet);
            socket.close();
        }
        catch(Exception e){
        }
        finally{
            if(socket != null){
                socket.close();
            }

        }
    }
}