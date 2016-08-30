package photopanel.boxes.implementations.structure;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class TrueFalseSwitchBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private MultipleOptionsParameter op;
    
    
    public TrueFalseSwitchBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.TRUE_FALSE));
        inlets.add(new Inlet(this, Datatype.TRUE_FALSE));
        
        outlets.add(new Outlet(this, Datatype.SINGLE_VALUE));
        
        op = addMultipleOptionsParameter(new String[] {"1", "2"}, 0, "Eingang");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Wahrheitswert umschalten";
    public static String TITLE = "Umschalten";
    public static String CATEGORY = Boxes.CATEGORY_STRUKTUR_UMSCHALTEN;
    
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
        return new Datatype[] {ds[op.getIndex()]};
    }
    
}
