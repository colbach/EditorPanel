package photopanel.boxes.implementations.structure.logic;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class LogicOperatorBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private MultipleOptionsParameter options;

    public LogicOperatorBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.TRUE_FALSE));
        inlets.add(new Inlet(this, Datatype.TRUE_FALSE));
        
        outlets.add(new Outlet(this, Datatype.TRUE_FALSE));
        
        options = addMultipleOptionsParameter(
                new String[] {
                    "XOR", "OR", "AND"
                }
                , 0, "Operator");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Logischer Operator auf Wahrheitswert";
    public static String TITLE = "Operator";
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
        boolean b2 = ((TrueFalse) ds[1]).isTrue();
        
        if(options.getIndex() == 0) {
            return new Datatype[] { new TrueFalse(b1 ^ b2, "XOR")};
            
        } else if(options.getIndex() == 1) {
            return new Datatype[] { new TrueFalse(b1 | b2, "OR")};
            
        } else if(options.getIndex() == 2) {
            return new Datatype[] { new TrueFalse(b1 & b2, "AND")};
            
        } else {
            throw new UnsupportedOperationException("Unbestimmte Option!");
        }
        
    }
    
}
