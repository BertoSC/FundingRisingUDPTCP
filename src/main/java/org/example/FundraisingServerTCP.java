package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FundraisingServerTCP {
    private final static int PORT = 57780;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true){
                try{
                    Socket socket = serverSocket.accept();
                    Founds founds = new Founds();
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
