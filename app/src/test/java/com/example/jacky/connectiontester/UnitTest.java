package com.example.jacky.connectiontester;


import org.junit.Test;

public class UnitTest {

    String addr = "192.168.1.106";
    int port = 21;
    TCPTest tcp;

    @Test
    public void tcp_ASCII(){
        TCPTest tcp_ASCII = new TCPTest(addr, port, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    }
    @Test
    public void tcp_CHI(){
        TCPTest tcp_CHI = new TCPTest(addr, port, "一號戒備信號現正生效。天文台表示，強烈熱帶風暴妮妲已增強為颱風。");
    }
    @Test
    public void udp_ASCII(){
        TCPTest udp_ASCII = new TCPTest(addr, port, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    }
    @Test
    public void udp_CHI(){
        TCPTest udp_CHI = new TCPTest(addr, port, "一號戒備信號現正生效。天文台表示，強烈熱帶風暴妮妲已增強為颱風。");
    }


}
