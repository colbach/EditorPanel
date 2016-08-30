package photopanel.boxes.parameters;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

import photopanel.boxes.listener.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class NotePseudoParameter implements Parameter, DragListening, Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    private String description;
    private ImageIcon image;

    private final Box box;
    private final int offsetY;
    
    private boolean onlyAcceptWholeValues = false;
    
    public NotePseudoParameter(Box box, int offsetY, String description) {
        this.description = description;
        this.box = box;
        this.offsetY = offsetY;
    }
    
    public NotePseudoParameter(Box box, int offsetY, String description, BufferedImage image) {
        this.description = description;
        this.box = box;
        this.offsetY = offsetY;
        this.image = new ImageIcon(image);
    }
    
    public String getDescription() {
        return description;
    }

    @Override
    public int getHeight() {
        int h = getLines() * NOTE_PARAMETER_HEIGHT_PER_LINE + 8;
        if(image != null) {
            h += image.getIconHeight() + 4;
        }
        return h;
    }
    
    private int chachedLines = -1;
    private int getLines() {
        if(chachedLines == -1) {
            chachedLines = description.split("\n").length;
        }
        return chachedLines;
    }

    @Override
    public void drawFill(Graphics g) {        
    }

    @Override
    public void drawUnfilled(Graphics g) {
        /*g.setColor(Color.yellow);
        g.fillRect(box.getX(), box.getY()+offsetY, box.getWidth(g), getHeight());*/
        
        // zeichne Titel
        g.setFont(PARAMETER_FONT);
        g.setColor(PARAMETER_COLOR);
        int off = NOTE_PARAMETER_HEIGHT_PER_LINE;
        if(image != null) {
            g.drawImage(image.getImage(), box.getX() + 5, box.getY()+offsetY+3, null);
            off += image.getIconHeight() + 4;
        }
        for(String line : description.split("\n")) {
            g.drawString(line, box.getX() + 5, box.getY()+offsetY+off);
            off += NOTE_PARAMETER_HEIGHT_PER_LINE;
        }
        
        // zeichne Trennlinie
        g.setColor(BOX_SECTION_LINE_COLOR);
        g.drawLine(box.getX()+3, box.getY()+getHeight()+offsetY-1, box.getX()+box.getWidth(g)-3, box.getY()+getHeight()+offsetY-1);
    }

    @Override
    public void whiteOut(Graphics g) {}
    
    private int chachedMinimumWidth = -1;
    @Override
    public int getMinimumWidth(Graphics g) {
        g.setFont(PARAMETER_FONT);
        if(chachedMinimumWidth == -1) {
            if(image != null) {
                chachedMinimumWidth = image.getIconWidth();
            }
            for(String line : description.split("\n")) {
                chachedMinimumWidth = Math.max(chachedMinimumWidth, getTextWidth(g, line) + 9);
            }
        }
        return chachedMinimumWidth;
    }

    @Override
    public boolean reactTo(int x, int y) {
        return false;
    }
    
    @Override
    public void reactToDrag(int x, int y) {
    }
}
