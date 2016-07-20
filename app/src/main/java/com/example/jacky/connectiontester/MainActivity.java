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
    EditText targetAddEt, targetPortEt;
    Button tcpConnBtn, udpConnBtn;
    String targetAdd;
    int targetPort;
    DatagramSocket serverSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetAddEt = (EditText) findViewById(R.id.targetAddEt);
        targetPortEt = (EditText) findViewById(R.id.targetPortEt);
        tcpConnBtn = (Button) findViewById(R.id.tcpConnBtn);
        udpConnBtn = (Button) findViewById(R.id.udpConnBtn);
        responseTv = (TextView) findViewById(R.id.responseTv);


        tcpConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = targetAddEt.getText().toString();
                targetPort = Integer.parseInt(targetPortEt.getText().toString());

                MyClientTask tcpConn = new MyClientTask(targetAdd, targetPort);
                tcpConn.execute();
            }
        });


        udpConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = targetAddEt.getText().toString();
                targetPort = Integer.parseInt(targetPortEt.getText().toString());

                Thread udpConn = new Thread(new MyClientTask2());
                udpConn.start();
            }
        });

    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        public static final String CONN_INFO = "Connection Information";
        Socket socket = null;

        String dstAddress;
        int dstPort;
        String response = "";

        MyClientTask(String addr, int port){
            dstAddress = addr;
            dstPort = port;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Log.i(CONN_INFO, "Attemping to connect to server");
                socket = new Socket(dstAddress, dstPort);
                Log.i(CONN_INFO, "Connection established");

                //Send message to server
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write("Hello server - TCP");
                bw.newLine();
                bw.flush();
                Log.w(CONN_INFO, "Server IP: " + targetAdd + " / " + "Server Port: " + targetPort + " / Type: TCP");

                //Receive message from server
                InputStreamReader input = new InputStreamReader(socket.getInputStream());
                BufferedReader msg =new BufferedReader(input);
                response = msg.readLine();

                Log.w(CONN_INFO, "From server: " + response);

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
    }

    public class MyClientTask2 implements Runnable {
        MyClientTask2(){}

        public void run(){
            DatagramSocket socket = null;
            String response = "";

            try{
                socket = new DatagramSocket();
                InetAddress serverAddress = InetAddress.getByName(targetAdd);
                String str = "Hello Server - UDP";

                //Send message to server
                DatagramPacket packet = new DatagramPacket(str.getBytes(),str.length(),serverAddress,targetPort);
                socket.send(packet);
                Log.w(CONN_INFO, "Server IP: " + targetAdd + " / " + "Server Port: " + targetPort + " / Type: UDP");

                //Receive message from server
                socket.receive(packet);
                response = new String(packet.getData(), 0, packet.getLength());
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
