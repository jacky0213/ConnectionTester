package com.example.jacky.connectiontester;

import android.util.Log;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class UDPTest {
    String addr;
    int port;
    String message;

    DatagramSocket socket;
    InetAddress IPAddress;
    DatagramPacket sendPacket;
    DatagramPacket receivePacket;

    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];

    public UDPTest(String addr, int port, String message) {
        try {
            this.addr = addr;
            this.port = port;
            this.message = message;
            IPAddress = InetAddress.getByName(addr);
            socket = new DatagramSocket();
            UDPTest();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UDPTest() throws Exception {
        sendData = message.getBytes();
        sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        socket.send(sendPacket);
        receive();
    }

    public void receive(){
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
        socket.close();

        String result = message;
        assertEquals(result, response);
    }
}
