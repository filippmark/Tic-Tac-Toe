package Buttons;

import MainWindow.Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;

public class LeaveBtn extends BasicBtn{
    private BufferedWriter out;
    private boolean flag = false;

    public LeaveBtn(){
        super();
        getButton().setText("Leave");
        getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag) {
                    try {
                        out.write("leave");
                        out.write("\n");
                        out.flush();
                        flag = false;
                    } catch (IOException excp) {
                        excp.printStackTrace();
                    }
                }
            }
        });
    }

    public void setOut(BufferedWriter writer) {
        this.out = writer;
        this.flag = true;
    }
}
