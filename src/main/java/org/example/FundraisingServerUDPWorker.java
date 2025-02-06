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

            double dineroAportado = Double.parseDouble(peticionArgumentos[1]);
            money.setTotalMoney(money.getTotalMoney()+dineroAportado);
            String respuesta = "HAS APORTADO + "+dineroAportado + "y HAY EN TOTAL "+money.getTotalMoney();
            buffer = respuesta.getBytes();

            DatagramPacket answer = new DatagramPacket(buffer, buffer.length, direccion, puertoCliente);
            udpSocket.send(answer);

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
                String respuesta = "HAS APORTADO + "+dineroAportado + "y HAY EN TOTAL "+money.getTotalMoney();
                buffer = respuesta.getBytes();
                DatagramPacket answer = new DatagramPacket(buffer, buffer.length, direcc, puertoC);
                udpSocket.send(answer);

            case "SHOW":
                String respuestaShow = "SE HAN RECAUDADO "+money.getTotalMoney();
                buffer = respuestaShow.getBytes();
                DatagramPacket answerShow = new DatagramPacket(buffer, buffer.length, direcc, puertoC);
                udpSocket.send(answerShow);

            case "QUIT":
                String respuestaQuit = "Gracias por venir";
                buffer = respuestaQuit.getBytes();


        }

    }
}
