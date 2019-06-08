package field;

import Figures.BasicFigure;

import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    private BasicFigure basicFigure = null;
    private boolean flag = false;
    private String side = null;

    public Field(){
        setBackground(Color.blue);
        setMinimumSize(new Dimension(25, 25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (flag)
            basicFigure.draw(g);
    }

    public void drawFigure(BasicFigure basicFigure){
        this.basicFigure = basicFigure;
        this.flag = true;
        repaint();
    }

    public void setSide(String side){
        this.side = side;
    }

    public String getSide(){
        return this.side;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
