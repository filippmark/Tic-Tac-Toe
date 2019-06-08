package Figures;

import field.Field;

import java.awt.*;

public class Cross extends BasicFigure{

    public Cross(Field field){
        super(field);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.RED);
        g2.drawLine(0, 0, super.field.getSize().width, super.field.getSize().height);
        g2.drawLine(super.field.getSize().width, 0, 0, super.field.getSize().height);
    }
}
