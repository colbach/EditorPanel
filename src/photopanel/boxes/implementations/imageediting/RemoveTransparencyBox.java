package photopanel.boxes.implementations.imageediting;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.boxes.datatypes.RGB.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class RemoveTransparencyBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    ToogleParameter disableParameter;

    private ValueParameter pR, pG, pB;
    
    public RemoveTransparencyBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
        
        addNote("A wird aus Bild gelöscht.\nErgebnis ist ein Bild vom Typ INT_RGB.");
        
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
    }
    
    public static String NAME = "Transparenz entfernen";
    public static String TITLE = "Transparenz entfernen";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_EFFEKTE;
    
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
        if(disableParameter.isSelected()) {
            return ds;
        }
        
        Bitmap b = (Bitmap) ds[0];
        BufferedImage sourceImage = b.getImage();
        
        BufferedImage dstImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        /*Graphics g = dstImage.getGraphics();
        g.setColor(new Color(pR.getInt(), pG.getInt(), pB.getInt()));
        g.fillRect(0, 0, dstImage.getWidth(), dstImage.getHeight());
        g.drawImage(sourceImage, 0, 0, null);*/
        
        for(int iX = 0; iX<dstImage.getWidth(); iX++) {
            for(int iY = 0; iY<dstImage.getHeight(); iY++) {
                int c = sourceImage.getRGB(iX, iY);
                dstImage.setRGB(iX, iY, rgb(r(c), g(c), b(c)));
            }
        }
        
        b.setImage(dstImage);
        
        return new Datatype[] { b };
    }
    
}
