package com.example.jacky.connectiontester;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

//TCP Class*****************************************************************************************
public class TCPConnection extends AsyncTask<Void, Void, String> {
    public static final String CONN_INFO = "TCPConnection";
    Socket socket = null;

    String dstAddress;
    int dstPort;
    String response = "";
    String message;

    TCPConnCallback callback;



    public interface TCPConnCallback{
        void onSuccess(String result);
    }

    TCPConnection(){}

    TCPConnection(String dstAddress, int dstPort, String message, TCPConnCallback callback){
        this.dstAddress = dstAddress;
        this.dstPort = dstPort;
        this.message = message;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... arg0) {
        response = tcpConn(dstAddress, dstPort, message);
        return response;
    }

    public String tcpConn (String dstAddress, int dstPort, String message){
        try {
            Log.i(CONN_INFO, "Attempting to connect to server");
            socket = new Socket(dstAddress, dstPort);
            Log.i(CONN_INFO, "Connection established");

            //Send message to server
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(message);
            bw.newLine();
            bw.flush();
            Log.w(CONN_INFO, "Server IP: " + dstAddress + " / " + "Server Port: " + dstPort + " / Type: TCP");

            //Receive message from server
            InputStreamReader input = new InputStreamReader(socket.getInputStream());
            BufferedReader msg =new BufferedReader(input);
            response = msg.readLine();

            Log.w(CONN_INFO, "From server: " + response);
            Log.i(CONN_INFO, "Connection closed");

            return response;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        this.callback.onSuccess(result);
    }
}

