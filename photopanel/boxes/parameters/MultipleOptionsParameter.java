package photopanel.boxes.parameters;

import java.awt.Graphics;
import java.io.Serializable;

import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class MultipleOptionsParameter implements Parameter, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String[] options;
    private int index;
    private String description;
    
    private final Box box;
    private final int offsetY;

    public MultipleOptionsParameter(Box box, int offsetY, String[] options, int index, String description) {
        this.options = options;
        this.index = index;
        this.offsetY = offsetY;
        this.box = box;
        this.description = description;
    }

    public String[] getOptions() {
        return options;
    }

    public int getIndex() {
        return index;
    }
    
    public boolean is(int i) {
        return i == index;
    }
    
    public boolean is(String s) {
        return options[index].trim().equalsIgnoreCase(s.trim());
    }

    public void setIndex(int index) {
        if(index >= 0 && index < options.length)
            this.index = index;
    }
    
    
    // === Zeichnen ===

    @Override
    public int getHeight() {
        return (options.length+1) * MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER;
    }

    @Override
    public void drawFill(Graphics g) {
        int y = box.getY()+offsetY + (index+1)*MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER+(MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER-OPTION_SELECT_EDGE)/2;
        int x = box.getX()+4;
        drawOptionCicleSelected(g, x, y);
    }

    @Override
    public void drawUnfilled(Graphics g) {
        /*g.setColor(Color.RED);
        g.fillRect(box.getX(), box.getY()+offsetY, box.getWidth(g), getHeight());*/
        
        // zeichne Titel
        g.setFont(PARAMETER_TITLE_FONT);
        g.setColor(PARAMETER_COLOR);
        g.drawString(description, box.getX() + 5, box.getY()+offsetY+MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER-5);
        
        // zeichne Auswahl
        g.setColor(PARAMETER_COLOR);
        g.setFont(PARAMETER_FONT);
        int y = box.getY()+offsetY+MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER;
        for(String o : options) {
            drawOptionCicle(g, box.getX()+4, y+(MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER-OPTION_SELECT_EDGE)/2);
            g.drawString(o, box.getX() + OPTION_SELECT_EDGE + 10, y+MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER-8);
            y += MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER;
        }
        g.setColor(BOX_SECTION_LINE_COLOR);
        g.drawLine(box.getX()+3, box.getY()+getHeight()+offsetY-1, box.getX()+box.getWidth(g)-3, box.getY()+getHeight()+offsetY-1);
    }

    @Override
    public void whiteOut(Graphics g) {
    }

    @Override
    public int getMinimumWidth(Graphics g) {
        g.setFont(PARAMETER_TITLE_FONT);
        int m = getTextWidth(g, description) + 10;
        g.setFont(PARAMETER_FONT);
        for(String o : options) {
            int om = OPTION_SELECT_EDGE + 10 + getTextWidth(g, o) + 5;
            if(om > m)
                m = om;
        }
        return m;
        //return 20;
    }

    @Override
    public boolean reactTo(int x, int y) {
        if(y > box.getY() + offsetY && y < box.getY() + offsetY + getHeight()) {
            for(int i=1; i<=options.length+1; i++) {
                if(y < box.getY() + offsetY + i*MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER) {
                    setIndex(i-2);
                    break;
                }
            }
            return true;
        }
        return false;
    }
    
}
