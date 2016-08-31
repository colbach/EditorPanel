package photopanel.boxes.implementations.structure.logic;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class LogicNotBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private MultipleOptionsParameter options;

    public LogicNotBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.TRUE_FALSE));
        
        outlets.add(new Outlet(this, Datatype.TRUE_FALSE));
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Logisches NOT auf Wahrheitswert";
    public static String TITLE = "NOT";
    public static String CATEGORY = Boxes.CATEGORY_STRUKTUR_LOGIK;
    
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
        boolean b1 = ((TrueFalse) ds[0]).isTrue();
        
        return new Datatype[] { new TrueFalse(!b1, "NOT")};
    }
    
}
