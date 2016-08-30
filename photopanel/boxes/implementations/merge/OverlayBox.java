package photopanel.boxes.implementations.merge;

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
public class OverlayBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    SliderParameter sp;
    ToogleParameter autoResizeP;
    ValueParameter xP, yP;
    
    public OverlayBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        xP = addValueParameter("0", "Offset X");
        yP = addValueParameter("0", "Offset Y");
        
        autoResizeP = addToogleParameter(true, "Bildgrösse anpassen");
        
        setX(x);
        setY(y);
        
    }
    
    public static String NAME = "Bilder übereinander legen";
    public static String TITLE = "Hinzufügen";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_ZUSAMMENFÜHREN;
    
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
        Bitmap b1 = (Bitmap) ds[0];
        Bitmap b2 = (Bitmap) ds[1];
        
        BufferedImage bi1 = b1.getImage();
        BufferedImage bi2 = b2.getImage();
        if(autoResizeP.isSelected() && (bi1.getWidth() < bi2.getWidth()+xP.getInt() || bi1.getHeight() < bi2.getHeight()+yP.getInt())) {
            int maxW = Math.max(bi1.getWidth(), bi2.getWidth()+xP.getInt());
            int maxH = Math.max(bi1.getHeight(), bi2.getHeight()+yP.getInt());
            BufferedImage binew = new BufferedImage(maxW, maxH, BufferedImage.TYPE_INT_ARGB);
            Graphics g = binew.getGraphics();
            g.drawImage(bi1, 0, 0, null);
            bi1 = binew;
            System.gc();
        }
        
        Graphics g = bi1.getGraphics();
        g.drawImage(bi2, xP.getInt(), yP.getInt(), null);
        
        b1.setImage(bi1);
        
        return new Datatype[] { b1 };
    }
    
}
