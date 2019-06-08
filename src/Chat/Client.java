package Chat;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread{
    private Socket client;
    private static BufferedWriter out;
    static String nick;
    private int port;
    private String ipAddr;
    private JTextArea msgs;

    public Client(String ipAddr, int port, String nickName, JTextArea textArea){
        nick = nickName;
        this.port = port;
        this.ipAddr = ipAddr;
        this.msgs = textArea;

        try {
            this.client = new Socket(InetAddress.getByName(ipAddr), port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        super.run();
        try {
            BufferedWriter outTemp = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            outTemp.write(nick + '\n');
            outTemp.flush();
            BufferedReader inTemp = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String servAns = inTemp.readLine();
            if ((client.isConnected()) && (servAns.equals("Ok"))) {
                System.out.println("all is fine");
                InMessages in = new InMessages(client, msgs);
                in.start();
            }
            else {
                if (servAns.equals("No"))
                    System.out.println("Ooops, change your nick and try again");
                else
                    System.out.println("Ooops, check ip and port");
            }
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    public  Socket getClient() {
        return client;
    }
}

class InMessages extends Thread{
    Socket socket;
    JTextArea textArea;

    public InMessages (Socket socket,  JTextArea textArea){
        this.socket = socket;
        this.textArea = textArea;
    }

    @Override
    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message = null;
            while ((message == null) || (!message.equals("stop"))){
                message = in.readLine();
                if (!(message.equals("stop"))) {
                    textArea.append(message + "\n");
                }
            }
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

