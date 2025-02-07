package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class FundraisingClientTCP {
    private static final String HOST = "localhost";
    private static final int PORT = 57780;
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(HOST, PORT);
            var flujoIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            var flujoOut = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("BIENVENIDO A LA PARTE DE CLIENTE DEL SERVICIO FUNDRAISING");
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("Introduce el comando de tu petición: ");
                String peticion = sc.nextLine();
                flujoOut.println(peticion);
                String respuesta = flujoIn.readLine();
                System.out.println(respuesta);
                if (peticion.equals("QUIT")){
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
