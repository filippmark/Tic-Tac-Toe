package Figures;

import field.Field;

import java.awt.*;

public class Zero extends BasicFigure{
    private int radius = 150;

    public Zero(Field field){
        super(field);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.setColor(super.getColor());
        g2.drawOval(Math.round(super.field.getSize().width / 2) - radius / 2, Math.round(super.field.getSize().height / 2)- radius/2, radius,radius);
    }
}
