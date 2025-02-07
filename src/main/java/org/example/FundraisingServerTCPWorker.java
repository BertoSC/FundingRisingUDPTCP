package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;

public class FundraisingServerTCPWorker implements Runnable{
    private Founds founds;
    private Socket socket;
    private BufferedReader flujoIn;
    private PrintWriter flujoOut;

    public FundraisingServerTCPWorker(Founds founds, Socket socket) throws IOException {
        this.founds = founds;
        this.socket = socket;
        this.flujoIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.flujoOut = new PrintWriter(socket.getOutputStream(), true);

    }

    @Override
    public void run() {
        String comando;
        try {
            while ((comando = flujoIn.readLine()) != null) {
                InetAddress direccionCliente = socket.getInetAddress();
                int puertoCliente = socket.getPort();
                String [] peticionArgumentos = comando.split(" ");
                gestionarComunicacion(peticionArgumentos, direccionCliente, puertoCliente);

            }
        } catch (IOException ioEx){
            System.out.println("ERROR IO AMIGO");
        }
    }

    private void gestionarComunicacion(String[] peticionArgumentos, InetAddress direccionCliente, int puertoCliente) {
        String peticion = peticionArgumentos[0];
        System.out.println("Gestionando petición "+peticion+" desde la direccion "+direccionCliente+" y el puerto "+puertoCliente);
        switch (peticion){
            case "ADD":
                double dineroAportado = Double.parseDouble(peticionArgumentos[1]);
                founds.setTotalMoney(founds.getTotalMoney()+dineroAportado);
                String respuesta = "HAS APORTADO "+dineroAportado + " Y HAY EN TOTAL "+founds.getTotalMoney();
                System.out.println("EL CLIENTE HA APORTADO "+dineroAportado);
                flujoOut.println(respuesta);
                break;

            case "SHOW":
                String respuestaShow = "SE HA RECAUDADO "+founds.getTotalMoney();
                System.out.println("EL CLIENTE PIDE MOSTRAR EL TOTAL, QUE ASCIENDE A "+founds.getTotalMoney());
                flujoOut.println(respuestaShow);
                break;

            case "QUIT":
                String respuestaQuit = "Gracias por venir";
                System.out.println("CERRANDO CONEXIÓN CON EL CLIENTE");
                flujoOut.println(respuestaQuit);
                break;

        }


    }
}
