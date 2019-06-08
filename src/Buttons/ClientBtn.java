package Buttons;

import Chat.Client;
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

import static MainWindow.Main.*;

public class ClientBtn extends BasicBtn{
    private ServerSocket serverSocket;
    private Socket client;
    private SendMsgBtn sendMsgBtn;

    public  ClientBtn(SendMsgBtn sendMsgBtn){
        super();
        this.sendMsgBtn = sendMsgBtn;
        getButton().setText("Join game");
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getButton().setEnabled(false);
                Main.serverBtn.getButton().setEnabled(false);
                startGameAsClient();
            }
        });
    }

    private void startGameAsClient(){
        if((!ipField.getText().equals("")) && (!portField.getText().equals(""))) {
            try {
                client = new Socket(InetAddress.getByName(ipField.getText()), Integer.valueOf(portField.getText()));
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

                Main.leaveBtn.setOut(writer);

                Client clientMsg = new Client(ipField.getText(), 8081, "Zero", Main.allMsgs);
                clientMsg.start();

                sendMsgBtn.setSocket(clientMsg.getClient());
                sendMsgBtn.setNick("Zero");

                UpdateState updateState = new UpdateState(client, reader, writer, sendMsgBtn);
                for (FieldListener fieldListener : Main.fieldListeners) {
                    fieldListener.setSideOfGame("Zero");
                    fieldListener.setSocket(client, reader, writer);
                    fieldListener.setUpdater(updateState);
                }
                updateState.start();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "Please enter CORRECT port and ip address for connection");
                serverBtn.getButton().setEnabled(true);
                clientBtn.getButton().setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Please enter port and ip address for connection");
            serverBtn.getButton().setEnabled(true);
            clientBtn.getButton().setEnabled(true);
        }
    }
}