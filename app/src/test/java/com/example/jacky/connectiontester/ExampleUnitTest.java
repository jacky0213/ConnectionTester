package com.example.jacky.connectiontester;

import android.util.Log;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest extends MainActivity{
    String testMessage = "Hello server";
    String addr = "192.168.1.106";
    int port = 21;

    @Test
    public void getTCPResult() throws Exception {
        Socket socket = new Socket(addr, port);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(testMessage);
        bw.newLine();
        bw.flush();

        InputStreamReader input = new InputStreamReader(socket.getInputStream());
        BufferedReader msg =new BufferedReader(input);
        String response = msg.readLine();

        String result = testMessage;
        assertEquals(response, result);
    }

    @Test
    public void getUDPResult() throws Exception {
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        DatagramSocket socket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(addr);

        sendData = testMessage.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        socket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
        socket.close();

        String result = testMessage;
        assertEquals(response, result);
    }

}