package photopanel.boxes.implementations.input;

import java.io.Serializable;
import java.util.ArrayList;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ValueArrayBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public ValueArrayBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        outlets.add(new Outlet(this, Datatype.VALUE_ARRAY));
        
        setX(x);
        setY(y);
        
        addValueParameter("0", "Array (mit Leerzeichen getrennt):", 500);
       
    }
    
    public static String NAME = "Wertearray";
    public static String TITLE = "Wertearray";
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
        
        String[] ss = ((ValueParameter)parameters.get(0)).getStringTrimmed().split(" ");
        ArrayList<Double> values = new ArrayList<>(ss.length);
        for(String s : ss) {
            if(s.length()>0) {
                values.add(Double.valueOf(s));
            }
        }
        
        return new Datatype[] {
            new ValueArray(values, "Eingabe")
        };
    }
    
}
