package photopanel.boxes.implementations.input;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class SingleValueBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public SingleValueBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        outlets.add(new Outlet(this, Datatype.SINGLE_VALUE));
        
        setX(x);
        setY(y);
        
        addValueParameter("0", "Wert (Reelle Zahl):", 200);
       
    }
    
    public static String NAME = "Einzelner Wert";
    public static String TITLE = "Einzelner Wert";
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
            new SingleValue(Double.valueOf(((ValueParameter)parameters.get(0)).getStringTrimmed()), "Eingabe")
        };
    }
    
}
