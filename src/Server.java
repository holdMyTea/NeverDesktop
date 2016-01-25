import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Server {

    SQLonnector sqLonnector;
    CustomConnection[] connections;

    public static void main(String args[]) {
        Server servr = new Server();
    }

    Server() {
        sqLonnector = new SQLonnector();
        connections = new CustomConnection[]{new CustomConnection(this, 1488, 0),new CustomConnection(this, 1489, 1)};
        FrameView frameView = new FrameView();
    }


    public boolean logger(String login, String pass) {
        System.out.println("Logger logs: "+login+" and "+pass);
        return sqLonnector.cheqUser(login, pass)!=0;
    }


    public void messageRecieved(String message) {
        try {
            for (int i = 0; i < connections.length; i++) {
                connections[i].sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private class FrameView extends JFrame {
        FrameView() {
            try{
                this.setSize(200, 300);
                this.setVisible(true);
                this.setLayout(new BorderLayout());
                this.setResizable(false);

                JTextField textField = new JTextField();
                this.add(textField, BorderLayout.CENTER);

                JButton buttonSend = new JButton("SEND");
                buttonSend.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent arg0) {
                        String message = textField.getText();
                        if (!message.isEmpty()) {
                            message = "_server_"+message;
                            messageRecieved(message);
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