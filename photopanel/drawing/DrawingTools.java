package photopanel.drawing;

import java.awt.*;
import static photopanel.drawing.DrawingConstants.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class DrawingTools {
    
    public static int getTextWidth(Graphics g, String text) {
        return g.getFontMetrics().stringWidth(text);
    }
    
    public static void drawBoxBackground(Graphics g, int x, int y, int width, int height) {
        
        g.setColor(BOX_SHADOW_COLOR);
        final int shadow = 2;
        g.fillRect(x+shadow, y+shadow, width, height);
        
        g.setColor(BOX_COLOR);
        g.fillRect(x, y, width, height);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        
    }
    
    public static void drawBoxBackgroundActive(Graphics g, int x, int y, int width, int height) {
        
        g.setColor(BOX_SHADOW_COLOR);
        final int shadow = 2;
        g.fillRect(x+shadow, y+shadow, width, height);
        
        g.setColor(BOX_ACTIVE_COLOR);
        g.fillRect(x, y, width, height);
        
        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
        
    }
    
    public static void drawTitle(Graphics g, int x, int y, int width, String text) {
        g.setFont(TITLE_FONT);
        int textwidth = getTextWidth(g, text);
        //g.setColor(BOX_COLOR);
        //g.fillRect(x+width/2-textwidth/2-2, y+3, textwidth + 6, 16);
        
        g.setColor(TITLE_COLOR);
        g.drawString(text, x+width/2-textwidth/2, y+INPUT_TEXT_BOX_HEIGHT-3);
    }
    
    public static void drawSelectBox(Graphics g, int x, int y) {
        g.setColor(PARAMETER_COLOR);
        g.drawRect(x, y, SELECT_BOX_EDGE, SELECT_BOX_EDGE);
    }
    
    public static void drawSelectBoxSelected(Graphics g, int x, int y) {
        g.setColor(OPTION_SELECT_COLOR);
        /*g.drawLine(x, y, x+SELECT_BOX_EDGE, y+SELECT_BOX_EDGE);
        g.drawLine(x+1, y, x+SELECT_BOX_EDGE, y+SELECT_BOX_EDGE-1);
        g.drawLine(x+SELECT_BOX_EDGE, y, x, y+SELECT_BOX_EDGE);
        g.drawLine(x+SELECT_BOX_EDGE-1, y, x, y+SELECT_BOX_EDGE-1);*/
        g.fillRect(x+3, y+3, SELECT_BOX_EDGE-5, SELECT_BOX_EDGE-5);
    }
    
    public static void drawOptionCicle(Graphics g, int x, int y) {
        g.setColor(PARAMETER_COLOR);
        g.drawOval(x, y, OPTION_SELECT_EDGE, OPTION_SELECT_EDGE);
    }
    
    public static void drawOptionCicleSelected(Graphics g, int x, int y) {
        g.setColor(OPTION_SELECT_COLOR);
        g.fillOval(x+2, y+2, OPTION_SELECT_EDGE-4, OPTION_SELECT_EDGE-4);
    }
    
    public static void drawWire(Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(WIRE_COLOR);
        
        drawWireLine(g, x1, y1, x2, y2);
        
        for(int i=0; i<10; i++) {
            g.drawLine(x2-i, y2-i, x2-i, y2+i);
        }
    }
    
    public static void drawPreviewWire(Graphics g, int x1, int y1, int x2, int y2) {
        g.setColor(WIRE_PREVIEW_COLOR);
        ((Graphics2D)g).setStroke(new BasicStroke(6));
        g.drawLine(x1, y1, x2, y2);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
    }
    
    public static void drawWireLine(Graphics g, int x1, int y1, int x2, int y2) {
        //boolean leftToRight = x2 > x1;
        int x11 = x1;
        int y11 = y1;
        int x22 = x2;
        int y22 = y2;
        //if(leftToRight) {
            x11 += ARROW_SPOT;
            x22 -= ARROW_SPOT;
        //} else {
        //    x11 -= ARROW_SPOT;
        //    x22 += ARROW_SPOT;
        //}
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.drawLine(x1, y1, x11, y11);
        g.drawLine(x11, y11, x22, y22);
        g.drawLine(x22, y22, x2, y2);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
    }
    
    public static void drawDottedArea(Graphics g, int x, int y, int width, int height) {
        
        g.setColor(BOX_TITLE_DOTT_COLOR);
        for(int ix=x; ix<x+width; ix+=2) {
            for(int iy=y; iy<y+height; iy+=2) {
                g.drawLine(ix, iy, ix, iy);
            }
        }
        
        
    }
    
    public static void drawX(Graphics g, int x, int y, int edge) {
        
        g.setColor(Color.BLACK);
        ((Graphics2D)g).setStroke(new BasicStroke(2));
        g.drawLine(x, y, x+edge, y+edge);
        g.drawLine(x+edge, y, x, y+edge);
        ((Graphics2D)g).setStroke(new BasicStroke(1));
        
    }
    
}
