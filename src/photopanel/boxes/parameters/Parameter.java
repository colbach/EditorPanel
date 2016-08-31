package photopanel.boxes.parameters;

import java.awt.Graphics;
import java.io.Serializable;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public interface Parameter extends Serializable {
    
    public int getHeight();
    public int getMinimumWidth(Graphics g);
    
    public void drawFill(Graphics g); // fuellt Eingabe in Feld
    public void drawUnfilled(Graphics g); // zeichnet Parameter ohne Eingabe zu fuellen
    public void whiteOut(Graphics g); // loecht Inhalt aus Feld

    public boolean reactTo(int x, int y);
    
}
