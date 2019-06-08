package Buttons;


import Chat.Client;
import Chat.Server;
import field.FieldListener;
import MainWindow.Main;
import field.FieldListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerBtn extends BasicBtn {

    private ServerSocket serverSocket;
    private Socket client;
    private SendMsgBtn sendMsgBtn;

    public ServerBtn(SendMsgBtn sendMsgBtn) {
        super();
        this.sendMsgBtn = sendMsgBtn;
        getButton().setText("Start game");
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getButton().setEnabled(false);
                Main.clientBtn.getButton().setEnabled(false);
                startGameAsServer();
            }
        });
    }

    private void startGameAsServer() {
        try {
            serverSocket = new ServerSocket(0);
            JOptionPane.showMessageDialog(null, "Wait an opponent to connect.\nPort  " + serverSocket.getLocalPort() + " for connection.");
            client = serverSocket.accept();

            Server server = new Server(8081);
            server.start();

            Client clientMsg = new Client("127.0.0.1", 8081, "Cross", Main.allMsgs);
            clientMsg.start();

            sendMsgBtn.setSocket(clientMsg.getClient());
            sendMsgBtn.setNick("Cross");

            JOptionPane.showMessageDialog(null, "You are joined by a player");
            BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            UpdateState updateState = new UpdateState(client, reader, writer, sendMsgBtn);
            updateState.setFlag(true);

            Main.leaveBtn.setOut(writer);

            for (FieldListener fieldListener : Main.fieldListeners) {
                fieldListener.setSideOfGame("Cross");
                fieldListener.setSocket(client, reader, writer);
                fieldListener.setUpdater(updateState);
            }
            updateState.start();
        } catch (Exception e) {

        }
    }
}
