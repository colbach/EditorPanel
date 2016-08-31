package photopanel.boxes.implementations.input;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class ImageByColor extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private ValueParameter pR, pG, pB, pA, pW, pH;
    private MultipleOptionsParameter pT;
    
    public ImageByColor(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        pW = addValueParameter("100", "Breite (in px)");
        pH = addValueParameter("100", "Höhe (in px)");
        
        pR = addValueParameter("0", "R (0-255)");
        pG = addValueParameter("0", "G (0-255)");
        pB = addValueParameter("0", "B (0-255)");
        pA = addValueParameter("255", "A (0-255)");
        
        pT = addMultipleOptionsParameter(new String[] {"ARGB", "RGB"}, 0, "Modus");
        
        setX(x);
        setY(y);
       
    }
    
    public static String NAME = "Bild aus Farbe";
    public static String TITLE = "Bild aus Farbe";
    public static String CATEGORY = Boxes.CATEGORY_IO_EINGABE;
    
    @Override
    public String getCategory() {
        return CATEGORY;
    }
    
    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getTitel() {
        return TITLE;
    }
    
    @Override
    public Datatype[] run(Datatype[] ds) throws Exception {
        
        int type = pT.getIndex() == 0 ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        
        BufferedImage bi = new BufferedImage(pW.getInt(), pH.getInt(), type);
        String name = "R=" + pR.getInt() + ", G=" + pG.getInt() + ", B=" + pG.getInt();
        if(pT.getIndex() == 0)
            name += ", A=" + pA.getInt();
        
        Graphics g = bi.getGraphics();
        if(pT.getIndex() == 0)
            g.setColor(new Color(pR.getInt(), pG.getInt(), pB.getInt(), pA.getInt()));
        else
            g.setColor(new Color(pR.getInt(), pG.getInt(), pB.getInt()));
        g.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        
        Bitmap b = new Bitmap(bi, name);
        
        return new Datatype[] { b };
    }
    
}
