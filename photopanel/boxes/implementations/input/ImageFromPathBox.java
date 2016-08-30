package photopanel.boxes.implementations.input;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import javax.imageio.ImageIO;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class ImageFromPathBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    ValueParameter pathParameter;

    public ImageFromPathBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
        
        pathParameter = addValueParameter("", "Dateipfad:", 200);
       
    }
    
    public static String NAME = "Bild aus Pfad laden";
    public static String TITLE = "Bild aus Pfad";
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
        File file = new File(pathParameter.getStringTrimmed());
        System.out.println(file.getAbsolutePath());
        if(file.exists()) {
            BufferedImage bi = ImageIO.read(file);
            Bitmap b = new Bitmap(bi, file.getName());
            return new Datatype[] {b};
        } else {
            throw new Exception(file.getAbsolutePath() + " existiert nicht");
        }
    }
    
}
