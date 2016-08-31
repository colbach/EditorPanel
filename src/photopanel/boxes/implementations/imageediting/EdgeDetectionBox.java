package photopanel.boxes.implementations.imageediting;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class EdgeDetectionBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    SliderParameter sp;
    ToogleParameter disableParameter;

    public EdgeDetectionBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
                
        sp = addSliderParameter(2, 1, 100, "Stärke");
        
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
        addNote("Bei Bildern mit A ist es unter\nUmständen sinnvoll von dem\nErgebnis den A-Wert zu entfernen");
    }
    
    public static String NAME = "Kanten hervorheben";
    public static String TITLE = "Kanten hervorheben";
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
        
        float bf = sp.getFloat();
        float af = (-bf)/4f;
        
        float[] edgeKernel = new float[] {
             0f , af , 0f,
             af , bf , af,
             0f , af , 0f
        };
        Kernel kernel = new Kernel(3, 3, edgeKernel);
        ConvolveOp op = new ConvolveOp(kernel);
        BufferedImage dstImage = op.filter(sourceImage, null);
        
        b.setImage(dstImage);
        
        return new Datatype[] { b };
    }
    
}
