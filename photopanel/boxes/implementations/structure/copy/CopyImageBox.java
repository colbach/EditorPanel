package photopanel.boxes.implementations.structure.copy;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class CopyImageBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public CopyImageBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Bild kopieren";
    public static String TITLE = "Kopieren";
    public static String CATEGORY = Boxes.CATEGORY_STRUKTUR_KOPIEREN;
    
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
        Bitmap bi = (Bitmap) ds[0];
        Bitmap copy = bi.copy();
        return new Datatype[] {bi, copy};
    }
    
}
