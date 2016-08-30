package photopanel.boxes.parameters;

import java.awt.Graphics;
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
public class SliderParameter implements Parameter, DragListening, Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    private double value;
    private double maximum;
    private double minimum;
    private String description;

    private final Box box;
    private final int offsetY;
    
    private boolean onlyAcceptWholeValues = false;
    
    public SliderParameter(Box box, int offsetY, double defaultValue, double minimum, double maximum, String description) {
        this.value = defaultValue;
        this.maximum = Math.max(maximum, minimum);
        this.minimum = Math.min(maximum, minimum);;
        this.description = description;
        this.box = box;
        this.offsetY = offsetY;
    }

    public boolean isOnlyAcceptWholeValues() {
        return onlyAcceptWholeValues;
    }

    public void setOnlyAcceptWholeValues(boolean onlyAcceptWholeValues) {
        this.onlyAcceptWholeValues = onlyAcceptWholeValues;
    }
    
    public void onlyAcceptWholeValues() {
        onlyAcceptWholeValues = true;
    }
    
    public void setValue(double value) {
        if(onlyAcceptWholeValues) {
            value = Math.round(value);
        }
        if(value > maximum) {
            value = maximum;
        } else if(value < minimum) {
            value = minimum;
        }
        this.value = value;
    }

    public double getDouble() {
        return value;
    }
    
    public float getFloat() {
        return (float) value;
    }
    
    public int getInt() {
        return (int) Math.round(value);
    }
    
    public String getValueString() {
        if(value - ((int)value) == 0) {
            return String.valueOf((int)value);
        } else {
            return String.valueOf(round(value, 2));
        }
    }
    
    private static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    
    public double getValueFrom0To1() {
        double range = maximum-minimum;
        double subbed = value-minimum;
        return subbed/range;
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public int getHeight() {
        return SLIDER_PARAMETER_HEIGHT;
    }

    @Override
    public void drawFill(Graphics g) {
        
        // Beschriftung zeichnen
        String valueString = getValueString();
        int textWidth = getTextWidth(g, valueString);
        g.setFont(PARAMETER_FONT);
        g.setColor(PARAMETER_COLOR);
        g.drawString(valueString, box.getX()+box.getWidth(g)/2-textWidth/2, box.getY()+offsetY+getHeight()-7);
        
        // Sliderposition zeichnen
        int barWidth = box.getWidth(g) - 20;
        int x = box.getX() + (int)(10 + getValueFrom0To1()*barWidth);
        g.setColor(PARAMETER_COLOR);
        g.fillOval(x-5, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 - 2 , 10, 10);
        
    }

    @Override
    public void drawUnfilled(Graphics g) {
        /*g.setColor(Color.yellow);
        g.fillRect(box.getX(), box.getY()+offsetY, box.getWidth(g), getHeight());*/
        
        // zeichne Titel
        g.setFont(PARAMETER_TITLE_FONT);
        g.setColor(PARAMETER_COLOR);
        g.drawString(description, box.getX() + 5, box.getY()+offsetY+MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER-5);
        
        // zeichne Slider-Linie
        g.setColor(PARAMETER_COLOR);
        g.drawLine(box.getX()+10, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 + 3, box.getX()+box.getWidth(g)-10, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 + 3);
        g.drawLine(box.getX()+10, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 - 5 + 3, box.getX()+10, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 + 5 + 3);
        g.drawLine(box.getX()+box.getWidth(g)-10, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 - 5 + 3, box.getX()+box.getWidth(g)-10, box.getY()+getHeight()+offsetY-1 - SLIDER_PARAMETER_HEIGHT/2 + 5 + 3);
        
        // zeichne Trennlinie
        g.setColor(BOX_SECTION_LINE_COLOR);
        g.drawLine(box.getX()+3, box.getY()+getHeight()+offsetY-1, box.getX()+box.getWidth(g)-3, box.getY()+getHeight()+offsetY-1);
    }

    @Override
    public void whiteOut(Graphics g) {}
    
    @Override
    public int getMinimumWidth(Graphics g) {
        g.setFont(PARAMETER_TITLE_FONT);
        return Math.max(getTextWidth(g, description) + 10, 100);
    }

    @Override
    public boolean reactTo(int x, int y) {
        if(y > box.getY() + offsetY && y < box.getY() + offsetY + getHeight()) {
            setSlider(x);
            registerDragListeningParameter(this);
            return true;
        }
        return false;
    }
    
    @Override
    public void reactToDrag(int x, int y) {
        setSlider(x);
    }
    
    public void setSlider(int x) {
        int barWidth = box.getCachedWidth() - 20;
        int sliderX = box.getX() + (int)(10 + getValueFrom0To1()*barWidth);
        int movedX = x - sliderX;
        double valueChange = movedX / (double)barWidth * (maximum-minimum);
        setValue(value + valueChange);
    }
}
