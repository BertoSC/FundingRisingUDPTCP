package org.example;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class FundraisingServerUDPWorker implements Runnable {
    Founds money;
    DatagramSocket udpSocket;
    byte [] buffer = new byte[1024];

    public FundraisingServerUDPWorker(Founds money, DatagramSocket udpSocket){
        this.money=money;
        this.udpSocket=udpSocket;
    }

    @Override
    public void run() {
        try {
            DatagramPacket peticionCliente = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(peticionCliente);
            InetAddress direccion = peticionCliente.getAddress();
            int puertoCliente = peticionCliente.getPort();
            String comando = new String(peticionCliente.getData(),0, peticionCliente.getLength(), "UTF-8");
            String [] peticionArgumentos = comando.split(" ");
            gestionarComunicacion(peticionArgumentos, direccion, puertoCliente);

        } catch (UnsupportedEncodingException unsEx){
            System.out.println("NON CHE SOPORTA O ENCODING");

        } catch (IOException ioEX){
            System.out.println("aiaiai algo malo");
        }
    }

    public void gestionarComunicacion(String [] argumentos, InetAddress direcc, int puertoC) throws IOException {
        String comando = argumentos[0];
        System.out.println("Gestionando petición "+comando+" desde la direccion "+direcc+" y desde el puerto " +puertoC);
        switch (comando){
            case "ADD":
                double dineroAportado = Double.parseDouble(argumentos[1]);
                money.setTotalMoney(money.getTotalMoney()+dineroAportado);
                String respuesta = "HAS APORTADO "+dineroAportado + " Y HAY EN TOTAL "+money.getTotalMoney();
                System.out.println("EL CLIENTE HA APORTADO "+dineroAportado);
                buffer = respuesta.getBytes();
                DatagramPacket answer = new DatagramPacket(buffer, buffer.length, direcc, puertoC);
                udpSocket.send(answer);
                break;

            case "SHOW":
                String respuestaShow = "SE HA RECAUDADO "+money.getTotalMoney();
                System.out.println("EL CLIENTE PIDE MOSTRAR EL TOTAL, QUE ASCIENDE A "+money.getTotalMoney());
                buffer = respuestaShow.getBytes();
                DatagramPacket answerShow = new DatagramPacket(buffer, buffer.length, direcc, puertoC);
                udpSocket.send(answerShow);
                break;

            case "QUIT":
                String respuestaQuit = "Gracias por venir";
                System.out.println("CERRANDO CONEXIÓN CON EL CLIENTE");
                buffer = respuestaQuit.getBytes();
                DatagramPacket answerQuit = new DatagramPacket(buffer, buffer.length, direcc, puertoC);
                udpSocket.send(answerQuit);
                break;


        }

    }
}
