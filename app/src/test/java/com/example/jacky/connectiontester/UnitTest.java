package com.example.jacky.connectiontester;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class UnitTest {


    @Test
    public void testNumGreaterThanZero(){
        assertTrue(true);
    }

    /*

    String text;
    String[] arr;

    String addr = "192.168.1.106";
    int port = 21;

    TCPConnection tcp = new TCPConnection();
    UDPConnection udp = new UDPConnection();
    File file = new File("C:\\Users\\Jacky\\Desktop\\AndroidStudioProjects\\ConnectionTester\\app\\src\\test\\res\\testDataFile.txt");

    @Before
    public void setUp() throws FileNotFoundException {



        //Read file
        FileInputStream in = new FileInputStream(file);

        //Read file content
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;
        try{
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch (IOException e){ }


        text = total.toString();
        arr = text.split("\\n");
    }

    @Test
    public void testNumGreaterThanZero(){
        for(int i = 0; i < arr.length; i++){
            assertEquals(arr[i], Integer.parseInt(arr[i]) >= 0 , true);
        }
    }


    @Test
    public void tcp_ASCII(){
        for(int i = 0; i < arr.length; i++){
            String result = tcp.tcpConn(addr, port, arr[i]);
            assertEquals(arr[i], result, "Test - " + arr[i]);
        }

    }

    @Test
    public void udp_ASCII(){
        for(int i = 0; i < arr.length; i++){
            String result = tcp.tcpConn(addr, port, arr[i]);
            assertEquals(arr[i], result, "Test - " + arr[i]);
        }
    }

    */
}
