package field;

import Buttons.UpdateState;
import Figures.Cross;
import Figures.Zero;


import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static MainWindow.Main.clientBtn;
import static MainWindow.Main.fieldListeners;
import static MainWindow.Main.serverBtn;
import static workWithXml.workWithXml.checkForWinner;
import static workWithXml.workWithXml.getGameFieldState;
import static workWithXml.workWithXml.stringToXml;

public class FieldListener extends MouseAdapter {
    private Field field;
    private String sideOfGame = null;
    private Socket socket = null;
    private BufferedReader in = null;
    private BufferedWriter out  = null;
    private UpdateState updater = null;

    public FieldListener(Field field){
        this.field = field;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.sideOfGame != null){
            if (this.field.getSide() == null) {
                System.out.println(updater.getFlag());
                if(updater.getFlag() && updater.getFlagEndCon()) {
                    if (this.sideOfGame.equals("Cross")) {
                        Cross cross = new Cross(this.field);
                        this.field.drawFigure(cross);
                        this.field.setSide("Cross");
                    } else {
                        Zero zero = new Zero(this.field);
                        this.field.drawFigure(zero);
                        this.field.setSide("Zero");
                    }
                    updater.setFlag(false);
                    try {
                        if ((checkForWinner(stringToXml(getGameFieldState())) != null) && (!checkForWinner(stringToXml(getGameFieldState())).equals("")))
                            JOptionPane.showMessageDialog(null, checkForWinner(stringToXml(getGameFieldState()))+ " won!");
                        out.write(getGameFieldState());
                        out.write("\n");
                        out.flush();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }else{
            JOptionPane.showMessageDialog(null, "Start or join the game first");
        }
    }

    public void setSideOfGame(String sideOfGame){
        this.sideOfGame = sideOfGame;
    }

    public void setSocket(Socket socket, BufferedReader in, BufferedWriter out) {
        try {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setUpdater(UpdateState updater){
        this.updater = updater;
    }
}

