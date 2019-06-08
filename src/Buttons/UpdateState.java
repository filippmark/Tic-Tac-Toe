package Buttons;

import field.Field;
import field.FieldListener;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

import static MainWindow.Main.*;
import static workWithXml.workWithXml.*;

public class UpdateState extends Thread{
    private BufferedReader in = null;
    private BufferedWriter out = null;
    private Socket socket = null;
    private boolean flag = false;
    private boolean flagEndCon = true;
    private SendMsgBtn sendMsgBtn;

    public UpdateState(Socket socket, BufferedReader in, BufferedWriter out, SendMsgBtn sendMsgBtn) {
        this.in = in;
        this.out = out;
        this.socket = socket;
        this.sendMsgBtn = sendMsgBtn;
    }

    @Override
    public void run() {
        super.run();
        String state = "entry";

        while ((state != null) && (!state.equals("exit")) && (!state.equals("leave"))){
            try {
                state = in.readLine();
                System.out.println("here");
                if ((!state.equals("exit")) && (!state.equals("leave"))) {
                    setGameFieldState(state);
                    if ((checkForWinner(stringToXml(getGameFieldState())) != null) && (!checkForWinner(stringToXml(getGameFieldState())).equals(""))) {
                        breakConnection(true, null);
                        break;
                    } else {
                        setFlag(true);
                    }
                } else {
                    if (state.equals("exit")) {
                        breakConnection(false, null);
                        break;
                    }else{
                        breakConnection(true, "Oooops, your opponent leaves game");
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void breakConnection(boolean flag, String result){
        try {
            if (flag) {
                if (result == null)
                    result = checkForWinner(stringToXml(getGameFieldState())) + " won!";
                out.write("exit");
                out.write("\n");
                out.flush();
            }
            allMsgs.setText("");
            sendMsgBtn.setMessage("stop");
            sendMsgBtn.sendMsg();
            sendMsgBtn.setFlag(false);
            in.close();
            out.close();
            socket.close();
            if (flag)
                JOptionPane.showMessageDialog(null, result);
            for (FieldListener fieldListener:fieldListeners) {
                fieldListener.setSideOfGame(null);
            }
            for (Field field: fields) {
                field.setSide(null);
                field.setFlag(false);
                field.repaint();
            }
            serverBtn.getButton().setEnabled(true);
            clientBtn.getButton().setEnabled(true);
            setFlagEnCon(false);
            setFlag(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean getFlag(){
        return this.flag;
    }

    public boolean getFlagEndCon(){
        return this.flagEndCon;
    }

    public boolean setFlagEnCon(boolean flag){
        return this.flagEndCon = flag;
    }
}
