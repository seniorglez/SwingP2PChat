package chatp2p;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import javax.swing.JOptionPane;

public class ChatB {

    public static void main(String[] args) {
        int puertoEscucha = 3000, puertoDestino = 2000;
        String nombre;
        DatagramSocket socket;
        try {
            socket = new DatagramSocket(puertoEscucha);
            byte[] bufer = new byte[200];
            DatagramPacket paqueteEnReceptor = new DatagramPacket(bufer, bufer.length);
            DatagramPacket paqueteAEnviar = new DatagramPacket(bufer,
                    bufer.length,
                    InetAddress.getByName("localhost"),
                    puertoDestino);
            nombre = JOptionPane.showInputDialog("Introduzca su nombre");
            ChatFrame ventana = new ChatFrame(paqueteAEnviar, nombre);

            while (true) {
                socket.receive(paqueteEnReceptor);
                ventana.escribirEnAreaDeTextoSuperior((new String(bufer)).trim());
                Arrays.fill(bufer, (byte) 0);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}