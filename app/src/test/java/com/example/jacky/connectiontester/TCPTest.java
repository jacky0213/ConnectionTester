package com.example.jacky.connectiontester;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;

public class TCPTest {
    String message;
    String addr;
    int port;
    Socket socket;

    public TCPTest(String addr, int port, String message) {
        try {
            this.addr = addr;
            this.port = port;
            socket = new Socket(addr, port);
            this.message = message;
            TCPTest();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TCPTest() throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(message);
        bw.newLine();
        bw.flush();

        InputStreamReader input = new InputStreamReader(socket.getInputStream());
        BufferedReader rmsg = new BufferedReader(input);
        String response = rmsg.readLine();

        String result = message;
        socket.close();
        assertEquals(result, response);
    }
}
