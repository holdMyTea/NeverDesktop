import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class CustomConnection implements Runnable{

    Server server;

    ServerSocket serverSocket;
    Socket socket;
    DataInputStream in;
    DataOutputStream out;
    int port;
    int number;     //sequence number of this connection in array, for GUI (that doesn't exist)

    CustomConnection(Server server, int port, int number) {
        this.server = server;
        this.port = port;
        this.number = number;
        serverSocket = null;
        socket = null;
        in = null;
        out = null;

        System.out.println("Connection #" + number + " on port " + port + " was created");

        new Thread(this).start();
    }

    @Override
    public void run() {
        if (connect()) {
            System.out.println("Connection #" + number + " on port " + port + " connected");
            if (loggingIn()) {
                System.out.println("Connection #" + number + " on port " + port + " logged in");
                messageRecieving();
            }
        }
    }

    private boolean connect() {
        try {
            System.out.println(InetAddress.getLocalHost().toString());
            serverSocket = new ServerSocket(port);
            System.out.println("Waiting for client...");
            socket = serverSocket.accept();
            System.out.println("Got a client");
            System.out.println();

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket.close();
                serverSocket.close();
                in.close();
                out.close();
            } catch (Exception innerEx) {
                e.printStackTrace();
            }
            return false;
        }
    }

    public boolean loggingIn() {
        try {
            String log, pass;
            log = in.readUTF();
            System.out.println("Login recieved: "+log);;
            if (log.contains("_login_")) {
                pass = in.readUTF();
                System.out.println("Password recieved: "+pass);
                if (pass.contains("_pass_")) {
                    if (server.logger(log, pass)) {
                        return true;
                    }
                }
            }
            System.out.println("No fcuking log/pass coincedsdasdasdafrgfuergijnce");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void messageRecieving() {
        try {
            out.writeBoolean(true);
            System.out.println("Connection #" + number + " on port " + port + " began reciving messages");
            String line;
            System.out.println(Integer.toString(in.available()));
            while (true) {
                //line = Integer.toString(in.read());
                line = in.readUTF();
                System.out.println("Connection #" + number + " on port " + port + " recived: " + line);
                server.messageRecieved(line, number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message, int num) {
        try {
            out.writeUTF("_" + num + "_" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prooveRecieving(String message) {
        try {
            out.writeUTF(":" + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getNumber() {
        return number;
    }
}
