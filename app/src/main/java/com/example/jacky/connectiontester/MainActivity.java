package com.example.jacky.connectiontester;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public static final String CONN_INFO = "Connection Information";

    TextView responseTv;
    EditText tcpAddrEt, tcpPortEt, tcpMsgEt, udpAddrEt, udpPortEt, udpMsgEt;
    Button tcpConnBtn, udpConnBtn;
    String targetAdd;


    int targetPort;
    //11
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tcpAddrEt = (EditText) findViewById(R.id.tcpAddrEt);
        tcpPortEt = (EditText) findViewById(R.id.tcpPortEt);
        tcpMsgEt = (EditText) findViewById(R.id.tcpMsgEt);
        udpAddrEt = (EditText) findViewById(R.id.udpAddrEt);
        udpPortEt = (EditText) findViewById(R.id.udpPortEt);
        udpMsgEt = (EditText) findViewById(R.id.tcpMsgEt);

        tcpConnBtn = (Button) findViewById(R.id.tcpConnBtn);
        udpConnBtn = (Button) findViewById(R.id.udpConnBtn);

        responseTv = (TextView) findViewById(R.id.responseTv);


        tcpConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = tcpAddrEt.getText().toString();
                targetPort = Integer.parseInt(tcpPortEt.getText().toString());
                String message = tcpMsgEt.getText().toString();

                MyClientTask tcpConn = new MyClientTask(targetAdd, targetPort, message);
                tcpConn.execute();
            }
        });


        udpConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = udpAddrEt.getText().toString();
                targetPort = Integer.parseInt(udpPortEt.getText().toString());
                String message = udpMsgEt.getText().toString();

                Thread udpConn = new Thread(new MyClientTask2(targetAdd, targetPort, message));
                udpConn.start();
            }
        });

    }

    //TCP Class*****************************************************************************************
    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        public static final String CONN_INFO = "Connection Information";
        Socket socket = null;

        String dstAddress;
        int dstPort;
        String response = "";
        String message;

        MyClientTask(String addr, int port, String message){
            dstAddress = addr;
            dstPort = port;
            this.message = message;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Log.i(CONN_INFO, "Attemping to connect to server");
                socket = new Socket(dstAddress, dstPort);
                Log.i(CONN_INFO, "Connection established");

                //Send message to server
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(message);
                bw.newLine();
                bw.flush();
                Log.w(CONN_INFO, "Server IP: " + targetAdd + " / " + "Server Port: " + targetPort + " / Type: TCP");

                //Receive message from server
                InputStreamReader input = new InputStreamReader(socket.getInputStream());
                BufferedReader msg =new BufferedReader(input);
                response = msg.readLine();

                Log.w(CONN_INFO, "From server: " + response);
                Log.i(CONN_INFO, "Connection closed");

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
        protected void onPostExecute(Void result) {
            responseTv.setText(response);
            super.onPostExecute(result);
        }

        public String getResponse(){
            return response;
        }

    }


    //UDP Class*****************************************************************************************
    public class MyClientTask2 implements Runnable {
        MyClientTask2(){}

        String dstAddress;
        int dstPort;
        String msg = "";
        String response = "";

        MyClientTask2(String addr, int port, String message){
            dstAddress = addr;
            dstPort = port;
            msg = message;
        }

        public void run(){
            DatagramSocket socket = null;
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];

            try{
                socket = new DatagramSocket();
                InetAddress serverAddress = InetAddress.getByName(dstAddress);
                sendData = msg.getBytes();

                //Send message to server
                DatagramPacket sendpacket = new DatagramPacket(sendData, sendData.length, serverAddress, dstPort);
                socket.send(sendpacket);
                Log.w(CONN_INFO, "Server IP: " + dstAddress + " / " + "Server Port: " + dstPort + " / Type: UDP");

                //Receive message from server
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);
                response = new String(receivePacket.getData(), 0, receivePacket.getLength());
                Log.w(CONN_INFO, "From server: " + response);

                responseTv.setText(response);
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
}
