package photopanel.boxes.implementations.input;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class TrueFalseBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private ToogleParameter tp;
    
    public TrueFalseBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        outlets.add(new Outlet(this, Datatype.TRUE_FALSE));
        
        setX(x);
        setY(y);
        
        tp = addToogleParameter(true, "Wert");
       
    }
    
    public static String NAME = "Wahrheitswert";
    public static String TITLE = "Wahrheitswert";
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
    public Datatype[] run(Datatype[] ds) {
        return new Datatype[] {
            new TrueFalse(tp.isSelected(), "Eingabe")
        };
    }
    
}
