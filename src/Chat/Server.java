package Chat;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread{
    static ArrayList<Connection> connections = new ArrayList<Connection>();
    static ArrayList<String> nicknames = new ArrayList<String>();
    static Socket clientSocket;
    private static BufferedReader in;
    static int port = 0;

    public Server(int newPort){
        port = newPort;
    }

    @Override
    public void run() {
        super.run();
        boolean flag = true;
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while (flag || (connections.size() != 0)) {
                clientSocket = serverSocket.accept();
                BufferedReader inTemp = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String nick = inTemp.readLine();
                System.out.println(nick);
                BufferedWriter outTemp = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                if ((nicknames.size() == 0) || (nicknames.indexOf(nick) == -1)) {
                    outTemp.write("Ok" + '\n');
                    outTemp.flush();
                    nicknames.add(nick);
                    connections.add(new Connection(clientSocket, nick, inTemp, outTemp));
                    flag = false;
                } else {
                    outTemp.write("No" + '\n');
                    outTemp.flush();
                    outTemp.close();
                    inTemp.close();
                    clientSocket.close();
                }
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

}

class ResendMessages extends Thread{
    Socket socket;
    BufferedReader in;
    String nick;

    public ResendMessages (Socket socket, BufferedReader in, String nick){
        this.in = in;
        this.socket = socket;
        this.nick = nick;
    }

    @Override
    public void run() {
        try {
            String message = null;
            while ((message == null) || (!message.equals("stop"))) {
                message = in.readLine();
                if (!(message.equals("stop"))) {
                    for (Connection el : Server.connections) {
                        try  {
                            if (!socket.equals(el.socket)) {
                                el.out.write(message + '\n');
                                el.out.flush();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            Connection connection = Server.connections.remove(Server.nicknames.indexOf(nick));
            Server.nicknames.remove(Server.nicknames.indexOf(nick));
            connection.out.write("stop" + '\n');
            connection.out.flush();
            connection.out.close();
            connection.in.close();
            connection.socket.close();
            System.out.println(Server.connections.size() );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Connection{
    Socket socket;
    String nick;
    BufferedReader in;
    BufferedWriter out;
    ResendMessages inMes;

    public Connection(Socket socket, String nick, BufferedReader in, BufferedWriter out){
        this.socket = socket;
        this.nick = nick;
        this.in = in;
        this.out = out;
        inMes = new ResendMessages(socket, in, nick);
        inMes.start();
    }

}
