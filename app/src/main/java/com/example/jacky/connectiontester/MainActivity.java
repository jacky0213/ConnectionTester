package com.example.jacky.connectiontester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class MainActivity extends AppCompatActivity {
    public static final String CONN_INFO = "Connection Information";

    TextView responseTv;
    EditText targetAddEt, targetPortEt;
    Button connBtn, clearBtn;
    String targetAdd;
    int targetPort;
    DatagramSocket serverSocket = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetAddEt = (EditText) findViewById(R.id.targetAddEt);
        targetPortEt = (EditText) findViewById(R.id.targetPortEt);
        connBtn = (Button) findViewById(R.id.connBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        responseTv = (TextView) findViewById(R.id.responseTv);


        connBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = targetAddEt.getText().toString();
                targetPort = Integer.parseInt(targetPortEt.getText().toString());

                MyClientTask tcpConn = new MyClientTask(targetAdd, targetPort);
                tcpConn.execute();

                Log.w(CONN_INFO, "Server IP: " + targetAdd + " / " + "Server Port: " + targetPort);
            }
        });

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = targetAddEt.getText().toString();
                targetPort = Integer.parseInt(targetPortEt.getText().toString());

                Thread udpConn = new Thread(new MyClientTask2());
                udpConn.start();
            }
        });


    }
}
