package photopanel.boxes.implementations.imageediting;

import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class NegativeBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    ToogleParameter disableParameter;

    public NegativeBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
                
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
    }
    
    public static String NAME = "Farben umkehren";
    public static String TITLE = "Umkehren";
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
            
            red[i] = (byte)(255 - i);
            green[i] = red[i];
            blue[i] = red[i];
            alpha[i] = i;
                    
            /*red[i] = (byte)(((byte)i) ^ ((byte)256));
            green[i] = (byte)(((byte)i) ^ ((byte)256));
            blue[i] = (byte)(((byte)i) ^ ((byte)256));*/
            
            /*red[i] = (byte)((255 - i)%255);
            green[i] = (byte)(255 - i);
            System.out.println(green[i]);
            blue[i] = (byte)(255 - i);
            //green[i] = (byte) i;*/
            
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
    
    // Alternative: http://stackoverflow.com/questions/8662349/convert-negative-image-to-positive
    
}
