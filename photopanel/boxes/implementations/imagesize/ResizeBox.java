package photopanel.boxes.implementations.imagesize;

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

public class ResizeBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    ValueParameter pB, pH;
    ToogleParameter pMid;
    MultipleOptionsParameter pMode;
    
    public ResizeBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
        
        addNote("Negative sowie Positive Werte möglich.\nBei leerem Feld wird automatisch ein\nproportionaler Wert eingefügt.");
        
        pB = addValueParameter("100%", "Breite (Angabe in px oder %)");
        pH = addValueParameter("100%", "Höhe (Angabe in px oder %)");
        
        //pMid = addToogleParameter(false, "Von Mitte aus betrachten");
    }
    
    public static String NAME = "Vergrößern / Verkleinern";
    public static String TITLE = "Vergrößern / Verkleinern";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_BILDGROESSE;
    
    
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
    public Datatype[] run(Datatype[] ds) {
        
        Bitmap bitmap = (Bitmap) ds[0];
        int originalWidth = bitmap.getWidth(), originalHeight=bitmap.getHeight();
        String sB = pB.getStringTrimmed(), sH = pH.getStringTrimmed();
        int w = -1, h = -1;
        if(sB.length() > 0 && sH.length() > 0) {
            w=stringToInt(sB, originalWidth);
            h=stringToInt(sH, originalHeight);
        } else if(sB.length() == 0 && sH.length() > 0) {
            h=stringToInt(sH, originalHeight);
            w=originalHeight * h / originalWidth;
        } else if(sB.length() > 0 && sH.length() == 0) {
            w=stringToInt(sB, originalWidth);
            h=originalHeight * w / originalWidth;
        } else {
            return new Datatype[] { bitmap };
        }
        
        BufferedImage image = bitmap.getImage();
        BufferedImage newImage = new BufferedImage(w, h, image.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, w, h, null);
        bitmap.setImage(newImage);
        
        return new Datatype[] { bitmap };
    }
    
    public int stringToInt(String s, int rel) {
        s = s.trim();
        if(s.endsWith("%")) {
            s = s.substring(0, s.length()-1);
            s = s.trim();
            return (int)((Double.valueOf(s) / 100) * rel);
        } else {
            s = s.replaceAll("px", "");
            return (int)(Double.valueOf(s) * 1d);
        }
    }
    
}
