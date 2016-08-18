package com.example.jacky.connectiontester;

import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//UDP Class*****************************************************************************************
public class UDPConnection implements Runnable {

    public static final String TAG = "UDPConnection";

    String dstAddress;
    int dstPort;
    String msg = "";
    String response = "";

    UDPConnCallback callback;

    public interface UDPConnCallback {
        void onSuccess(String result);
    }

    UDPConnection() {}

    UDPConnection(String addr, int port, String message, UDPConnCallback callback) {
        dstAddress = addr;
        dstPort = port;
        msg = message;
        this.callback = callback;
    }

    public void run() {
        udpConn(dstAddress, dstPort, msg);
    }

    public String udpConn(String dstAddress, int dstPort, String msg) {
        DatagramSocket socket = null;
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        try {
            socket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName(dstAddress);
            sendData = msg.getBytes();

            //Send message to server
            DatagramPacket sendpacket = new DatagramPacket(sendData, sendData.length, serverAddress, dstPort);
            socket.send(sendpacket);
            Log.w(TAG, "Server IP: " + dstAddress + " / " + "Server Port: " + dstPort + " / Type: UDP");

            //Receive message from server
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            response = new String(receivePacket.getData(), 0, receivePacket.getLength());

            this.callback.onSuccess(response);
            Log.w(TAG, "From server: " + response);

        } catch (Exception e) {
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
        return response;
    }
}