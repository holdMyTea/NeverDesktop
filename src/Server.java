import javax.swing.*;

public class Server {


    JLabel answear;
    SQLonnector sqLonnector;
    CustomConnection[] connections;

    public static void main(String args[]) {
        Server servr = new Server();
    }

    Server() {
        sqLonnector = new SQLonnector();
        connections = new CustomConnection[]{new CustomConnection(this, 1488, 0)};
    }


    public boolean logger(String login, String pass) {
        System.out.println("Logger logs: "+login+" and "+pass);
        return sqLonnector.cheqUser(login, pass)!=0;
    }


    public void messageRecieved(String message, int numOfConnectionRecieved) {
        try {
            for (int i = 0; i < connections.length; i++) {
                if (i == numOfConnectionRecieved) {
                    connections[i].prooveRecieving(message);
                } else {
                    connections[i].sendMessage(message,numOfConnectionRecieved);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /*private class FrameView extends JFrame {
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
    }*/

    /*private class TableMaker{
        TableMaker(int rows){
            String[] rowTitles = {"number","port","state"};
            String[] colTitles = {"1","2","3"};

            JTable table = new JTable(rowTitles,colTitles);
        }
    }*/
}