package Buttons;

import Chat.Client;
import MainWindow.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class SendMsgBtn extends BasicBtn{
    private Socket socket;
    private String nick;
    private boolean flag;
    private String mesg;
    private BufferedWriter out;

    public SendMsgBtn(){
        super();
        getButton().setText("Send");
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isFlag()){
                    setMessage(Main.message.getText());
                    Main.message.setText("");
                    try {
                        sendMsg();
                    } catch (IOException excp) {
                        excp.printStackTrace();
                    }
                }
            }
        });
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.flag = true;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setMessage(String message) {
        this.mesg = message;
    }

    public void sendMsg() throws IOException {
        try{
            if ((!mesg.equals("stop"))) {
                String temp = nick + ": " + mesg + '\n';
                out.write(temp);
                out.flush();
                Main.allMsgs.append(temp);
            }
            else{
                out.write(mesg + '\n');
                out.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
