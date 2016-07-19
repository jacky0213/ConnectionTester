package com.example.jacky.connectiontester;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.Buffer;


public class MainActivity extends AppCompatActivity {
    public static final String CONN_INFO = "Connection Information";

    TextView responseTv;
    EditText targetAddEt, targetPortEt;
    Button connBtn, clearBtn;
    String targetAdd;
    int targetPort;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetAddEt = (EditText) findViewById(R.id.targetAddEt);
        targetPortEt = (EditText) findViewById(R.id.targetPortEt);
        connBtn = (Button) findViewById(R.id.connBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
        responseTv = (TextView) findViewById(R.id.responseTv);

        //targetAddEt.setText(SERVERADDRESS);
        //targetPortEt.setText(String.valueOf(SERVERPORT));


        connBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = targetAddEt.getText().toString();
                targetPort = Integer.parseInt(targetPortEt.getText().toString());

                MyClientTask myClientTask = new MyClientTask(targetAdd, targetPort);
                myClientTask.execute();

                Log.w(CONN_INFO, "Server IP: " + targetAdd + " / " + "Server Port: " + targetPort);
            }
        });


        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //responseTv.setText("Response");
                //targetAddEt.setText("");
                //targetPortEt.setText("");
            }
        });


    }
        /*

    */




}
