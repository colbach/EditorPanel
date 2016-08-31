package photopanel.boxes.parameters;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import photopanel.boxes.listener.*;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ToogleParameter implements Parameter, Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    private boolean selected;
    private String description;
    
    private final Box box;
    private final int offsetY;

    public ToogleParameter(Box box, int offsetY, boolean defaultValue, String description) {
        this.selected = defaultValue;
        this.description = description;
        this.box = box;
        this.offsetY = offsetY;
    }

    public void setValue(boolean value) {
        this.selected = value;
    }
    
    public String getValueString() {
        return "" + selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getHeight() {
        return TOOGLE_PARAMETER_HEIGHT_PER_PARAMETER;
    }
    
    @Override
    public int getMinimumWidth(Graphics g) {
        g.setFont(PARAMETER_FONT);
        return SELECT_BOX_EDGE + 10 + getTextWidth(g, description) + 5;
    }

    @Override
    public void drawFill(Graphics g) {
        if(selected) {
            g.setColor(PARAMETER_COLOR);
            drawSelectBoxSelected(g, box.getX()+4, box.getY()+offsetY+8);
        }
    }

    @Override
    public void drawUnfilled(Graphics g) {
        /*g.setColor(Color.GREEN);
        g.fillRect(box.getX(), box.getY()+offsetY, box.getWidth(g), getHeight());*/
        
        g.setColor(PARAMETER_COLOR);
        g.setFont(PARAMETER_FONT);
        drawSelectBox(g, box.getX()+4, box.getY()+offsetY+8);
        g.drawString(description, box.getX() + SELECT_BOX_EDGE + 10, box.getY()+offsetY+MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER-5);
        g.setColor(BOX_SECTION_LINE_COLOR);
        g.drawLine(box.getX()+3, box.getY()+getHeight()+offsetY-1, box.getX()+box.getWidth(g)-3, box.getY()+getHeight()+offsetY-1);
    }

    @Override
    public void whiteOut(Graphics g) {
    }

    @Override
    public boolean reactTo(int x, int y) {
        if(y > box.getY() + offsetY && y < box.getY() + offsetY + getHeight()) {
            selected = !selected;
            return true;
        }
        return false;
    }
}
