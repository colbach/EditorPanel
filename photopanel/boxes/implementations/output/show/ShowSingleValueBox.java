package photopanel.boxes.implementations.output.show;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ShowSingleValueBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    ToogleParameter disableParameter;

    public ShowSingleValueBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.SINGLE_VALUE));
        
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Wert ausgeben";
    public static String TITLE = "Ausgeben";
    public static String CATEGORY = Boxes.CATEGORY_IO_AUSGABE_ZEIGEN;
    
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
        if(!disableParameter.isSelected())
            ds[0].show();
        return new Datatype[0];
    }
    
}
