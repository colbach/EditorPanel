package photopanel.boxes.implementations.imageediting;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ByteLookupTable;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.io.Serializable;
import photopanel.boxes.listener.*;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class TuneColorBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    ToogleParameter disableParameter;
    SliderParameter pR, pG, pB, pA;

    public TuneColorBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        pR = addSliderParameter(0, -1, 1, "R");
        pG = addSliderParameter(0, -1, 1, "G");
        pB = addSliderParameter(0, -1, 1, "B");
        pA = addSliderParameter(0, -1, 1, "A (falls A unterstützt wird)");
        
        setX(x);
        setY(y);
                
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
    }
    
    public static String NAME = "Farben anpassen";
    public static String TITLE = "Farben anpassen";
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
    
    private static final short[] invertTable;
    static {
	invertTable = new short[256];
	for (int i = 0; i < 256; i++) {
	    invertTable[i] = (short) (255 - i);
	}
    }
    
    @Override
    public Datatype[] run(Datatype[] ds) {
        if(disableParameter.isSelected()) {
            return ds;
        }
        
        Bitmap b = (Bitmap) ds[0];
        BufferedImage sourceImage = b.getImage();
        
        short[] red = new short[256];
        short[] green = new short[256];
        short[] blue = new short[256];
        short[] alpha = new short[256];

        for (short i = 0; i < 256; i++) {
            red[i] = faktor(i, pR.getDouble());
            green[i] = faktor(i, pG.getDouble());
            blue[i] = faktor(i, pB.getDouble());
            alpha[i] = i;
        }

        short[][] data;
        if(sourceImage.getColorModel().hasAlpha()) {
            data = new short[][] {
                red, green, blue, alpha
            };
        } else {
            data = new short[][] {
                red, green, blue
            };
        }
        
        LookupTable lookupTable = new ShortLookupTable(0, data);
        LookupOp op = new LookupOp(lookupTable, null);
        BufferedImage destinationImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());

        destinationImage = op.filter(sourceImage, destinationImage);
        
        b.setImage(destinationImage);

        return new Datatype[] { b };
    }
    
    public short faktor(short v, double faktor) {
        long vv = Math.round(v + faktor * multi(v));
        System.out.println("v=" + v + " faktor=" + faktor + " -> " + vv);
        vv = vv % Short.MAX_VALUE;
        return (short)vv;
    }
    
    public short multi(short v) {
        return (short)(255 - v);
    }
    
}
