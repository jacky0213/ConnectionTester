package com.example.jacky.connectiontester;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String CONN_INFO = "MainActivity";

    TextView responseTv;
    EditText addrEt, portEt, msgEt;
    Button tcpConnBtn, udpConnBtn;
    String targetAdd;
    int targetPort;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void init(){
        addrEt = (EditText) findViewById(R.id.addrEt);
        portEt = (EditText) findViewById(R.id.portEt);
        msgEt = (EditText) findViewById(R.id.msgEt);

        tcpConnBtn = (Button) findViewById(R.id.tcpConnBtn);
        udpConnBtn = (Button) findViewById(R.id.udpConnBtn);

        responseTv = (TextView) findViewById(R.id.responseTv);


        tcpConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = addrEt.getText().toString();
                targetPort = Integer.parseInt(portEt.getText().toString());
                String message = msgEt.getText().toString();

                TCPConnection tcpConn = new TCPConnection(targetAdd, targetPort, message, new TCPConnection.TCPConnCallback() {
                    @Override
                    public void onSuccess(String result) {
                        counter ++;
                        responseTv.setText("(" + counter + ") From TCP server: " + result);
                    }
                });
                tcpConn.execute();
            }
        });

        udpConnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                targetAdd = addrEt.getText().toString();
                targetPort = Integer.parseInt(portEt.getText().toString());
                String message = msgEt.getText().toString();

                Thread udpConn = new Thread(new UDPConnection(targetAdd, targetPort, message, new UDPConnection.UDPConnCallback() {
                    @Override
                    public void onSuccess(String result) {
                        counter ++;
                        responseTv.setText("(" + counter + ") From UDP server: " + result);
                    }
                }));
                udpConn.start();
            }
        });
    }
}
