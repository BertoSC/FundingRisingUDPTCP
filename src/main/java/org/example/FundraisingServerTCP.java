package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FundraisingServerTCP {
    private final static int PORT = 57780;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            Founds founds = new Founds();
            while (true){
                try{
                    Socket socket = serverSocket.accept();
                    Thread th = new Thread(new FundraisingServerTCPWorker(founds, socket));
                    th.start();


                }catch (IOException ioEx){
                    System.out.println("Error de entrada/salida");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
