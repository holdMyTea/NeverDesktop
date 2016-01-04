import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.mysql.jdbc.Driver;

//^^^u no

public class Server {
    /*public static void main(String args[]) {
        Serving servr = new Serving();
    }*/
}

class Serving {
    ServerSocket s;
    Socket sock;
    DataInputStream in;
    DataOutputStream out;

    JLabel answear;

    int port = 1488;

    Serving() {
        answear = new JLabel();
        if (connect()) {
            System.out.println("Framing");
            FrameView frame = new FrameView();
            doTheStuff();
        }
    }

    private boolean connect() {
        try {
            System.out.println(InetAddress.getLocalHost().toString());
            s = new ServerSocket(port);
            System.out.println("Waiting for client...");
            sock = s.accept();
            System.out.println("Got a client");
            System.out.println();

            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void doTheStuff() {
        try {
            String line;
            while (true) {
                line = in.readUTF();
                answear.setText(line);
                System.out.println("Waiting for following");
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean send(String message) {
        try {
            System.out.println("Sending: " + message);
            out.writeUTF(message);
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private class FrameView extends JFrame {
        FrameView() {
            try{
                this.setSize(200, 300);
                this.setVisible(true);
                this.setLayout(new BorderLayout());
                this.setResizable(false);

                answear.setSize(200, 100);
                this.add(answear, BorderLayout.PAGE_START);

                JTextField textField = new JTextField();
                this.add(textField, BorderLayout.CENTER);

                JButton buttonSend = new JButton("SEND");
                buttonSend.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        String message = textField.getText();
                        if (!message.isEmpty()) {
                            send(message);
                        }
                    }
                });
                this.add(buttonSend, BorderLayout.PAGE_END);

                this.validate();
                System.out.println("Framed");
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}