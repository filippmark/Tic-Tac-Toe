package Figures;

import field.Field;

import java.awt.*;

public abstract class BasicFigure {
    protected Field field;
    private Color color = Color.BLACK;

    public BasicFigure(Field field){
        this.field = field;
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public abstract void draw(Graphics g);
}
