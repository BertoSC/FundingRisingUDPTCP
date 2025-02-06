package org.example;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class FundraisingClientUDP {
    public static final int PORT = 50000;
    public static final String HOST = "localhost";
    public static void main(String[] args) {
        byte [] bufferSend = new byte[1024];
        byte [] bufferRespuesta = new byte[1024];

        try{
            System.out.println("WELCOME TO THE CLIENT SIDE OF THE FUNDRAISING PROGRAM");
            InetAddress serverAdress = InetAddress.getByName(HOST);
            DatagramSocket udpSocket = new DatagramSocket();
            Scanner sc = new Scanner(System.in);
            //showMenu(sc);
            while (true) {
                System.out.println("ENTER YOUR COMMAND: ");
                String peticion = sc.nextLine();
                bufferSend = peticion.getBytes();

                DatagramPacket paqueteSend = new DatagramPacket(bufferSend, bufferSend.length, serverAdress, PORT);
                udpSocket.send(paqueteSend);

                DatagramPacket respuestaServer = new DatagramPacket(bufferRespuesta, bufferRespuesta.length);
                udpSocket.receive(respuestaServer);
                String respuesta = new String(respuestaServer.getData(), 0, respuestaServer.getLength(), "UTF-8");
                System.out.println(respuesta);
                if (peticion.equals("QUIT")){
                   break;
                }
            }

            udpSocket.close();
        }catch (SocketException socEx){
            System.out.println("Socketeado el problemita");
        }catch (UnknownHostException ueEx){
            System.out.println("Non sei quen eres");
        }catch (IOException ioEx){
            System.out.println("Enter sandman EXECUTED");
        }
    }

    private static void showMenu(Scanner sc) {
        System.out.println("WELCOME TO THE CLIENT SIDE OF THE FUNDRAISING PROGRAM");

    }
}
