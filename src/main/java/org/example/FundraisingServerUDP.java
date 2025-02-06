package org.example;

import java.net.DatagramSocket;
import java.net.SocketException;

public class FundraisingServerUDP {
    public static final int PORT = 50000;
    public static void main(String[] args) {
        DatagramSocket udpSocket;
        try {
            udpSocket = new DatagramSocket(PORT);
            Founds money = new Founds();
            while (true){
                Thread th = new Thread(new FundraisingServerUDPWorker(money, udpSocket));
                th.start();
            }
        } catch (SocketException ex){
            System.out.println("UPS, problemicas");
        }
    }
}
