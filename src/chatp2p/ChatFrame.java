package chatp2p;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.*;

@SuppressWarnings("serial")
public class ChatFrame extends JFrame implements KeyListener {

    String userName;
    DatagramSocket socketEnvio;
    DatagramPacket paqueteEnvio;

    JTextArea superiorTextArea;
    JTextArea inferiorTextArea;
    JScrollPane superiorTextAreaWithScroll;

    ChatFrame(DatagramPacket a, String nombre) {
        paqueteEnvio = a;
        userName = nombre;
        try {
            socketEnvio = new DatagramSocket();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(userName);
        this.setSize(700, 700);
        this.setLayout(new FlowLayout());

        superiorTextArea = new JTextArea();

        this.superiorTextAreaWithScroll = new JScrollPane(superiorTextArea);
        this.superiorTextAreaWithScroll.setPreferredSize(new Dimension(650, 600));
        DefaultCaret caret = (DefaultCaret) superiorTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        superiorTextArea.setLineWrap(true);
        superiorTextArea.setEditable(false);
        superiorTextArea.setBackground(Color.LIGHT_GRAY);

        inferiorTextArea = new JTextArea();
        inferiorTextArea.setPreferredSize(new Dimension(650, 20));
        inferiorTextArea.setBackground(Color.PINK);
        inferiorTextArea.setLineWrap(true);
        inferiorTextArea.addKeyListener(this);

        this.add(superiorTextAreaWithScroll);
        this.add(inferiorTextArea);
        this.setVisible(true);
    }

    public void escribirEnAreaDeTextoSuperior(String s) {
        superiorTextArea.append(s + "\n");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            String msg = inferiorTextArea.getText();
            String msg2Send = userName + ": " + msg;

            byte[] data = msg2Send.getBytes();
            paqueteEnvio.setData(data);
            paqueteEnvio.setLength(data.length);

            try {
                socketEnvio.send(paqueteEnvio);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.escribirEnAreaDeTextoSuperior("Yo: " + msg);
            inferiorTextArea.setText(null);
            e.consume();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
