package photopanel.boxes.parameters;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.io.Serializable;

import photopanel.boxes.listener.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;
import static photopanel.StaticManager.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ValueParameter implements Parameter, KeyListening, Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    private String value;
    private double maximum;
    private double minimum;
    private String description;
    
    private final Box box;
    private final int offsetY;
    private int forcedFieldWidth = DEFAULT_INPUT_TEXT_BOX_WIDTH;

    public ValueParameter(Box box, int offsetY, String defaultValue, String description) {
        this.value = defaultValue;
        this.maximum = maximum;
        this.minimum = minimum;
        this.description = description;
        this.box = box;
        this.offsetY = offsetY;
    }

    public ValueParameter(Box box, int offsetY, String defaultValue, String description, int forcedFieldWidth) {
        this(box, offsetY, defaultValue, description);
        this.forcedFieldWidth = forcedFieldWidth;
    }

    public String getStringTrimmed() {
        return value.trim();
    }
    
    public String getStringUntrimmed() {
        return value;
    }
    
    public double getDouble() {
        return value.length() > 0 ? Double.valueOf(value.trim()) : 0;
    }
    
    public int getInt() {
        return value.length() > 0 ? Integer.valueOf(value.trim()) : 0;
    }
    
    public float getFloat() {
        return value.length() > 0 ? Float.valueOf(value.trim()) : 0;
    }

    public void setString(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }
    
    public boolean isProcentage() {
        return value.trim().endsWith("%");
    }
    
    public float getProcentage() {
        String s = value.trim();
        if(s.endsWith("%")) {
            s = s.substring(0, s.length()-1);
            s = s.trim();
            return (int)(Double.valueOf(s) / 100);
        }
        throw new RuntimeException("Kein Prozentsatz!");
    }

    @Override
    public int getHeight() {
        return VALUE_PARAMETER_HEIGHT_PER_PARAMETER;
    }
    
    @Override
    public int getMinimumWidth(Graphics g) {
        g.setFont(PARAMETER_TITLE_FONT);
        return getTextWidth(g, description) + 15 + forcedFieldWidth;
    }

    @Override
    public void drawFill(Graphics g) {
        g.clipRect(box.getX() + box.getWidth(g) - forcedFieldWidth - 5, box.getY()+offsetY + 5, forcedFieldWidth, INPUT_TEXT_BOX_HEIGHT);
        g.setFont(INPUT_TEXT_FONT);
        g.drawString(value, box.getX() + box.getWidth(g) - forcedFieldWidth - 1, box.getY()+offsetY +INPUT_TEXT_BOX_HEIGHT);
        g.setClip(null);
    }

    @Override
    public void drawUnfilled(Graphics g) {
        /*g.setColor(Color.CYAN);
        g.fillRect(box.getX(), box.getY()+offsetY, box.getWidth(g), getHeight());*/
        
        g.setFont(PARAMETER_TITLE_FONT);
        g.setColor(PARAMETER_COLOR);
        g.drawString(description, box.getX()+ 5, box.getY()+offsetY+20);
        
        g.setColor(PARAMETER_COLOR);
        g.drawRect(box.getX() + box.getWidth(g) - forcedFieldWidth - 5, box.getY()+offsetY + 5, forcedFieldWidth, INPUT_TEXT_BOX_HEIGHT);
        
        // zeichne Trennlinie
        g.setColor(BOX_SECTION_LINE_COLOR);
        g.drawLine(box.getX()+3, box.getY()+getHeight()+offsetY-1, box.getX()+box.getWidth(g)-3, box.getY()+getHeight()+offsetY-1);
    }

    @Override
    public void whiteOut(Graphics g) {
    }

    @Override
    public boolean reactTo(int x, int y) {
        if(y > box.getY() + offsetY && y < box.getY() + offsetY + getHeight()) {
            registerKeyListeningParameter(this);
            return true;
        }
        return false;
    }

    @Override
    public void react(char c) {
        if(((int) c) == 8) {
            if(value.length() > 0) {
                value = value.substring(0, value.length()-1);
            }
        } else if(c == 22) {
            try { 
                String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                value += data;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            value += c;
        }
    }
    
}
