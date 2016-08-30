package photopanel.boxes.implementations.structure.copy;

import java.io.Serializable;
import photopanel.boxes.Box;
import photopanel.boxes.Boxes;
import photopanel.boxes.Inlet;
import photopanel.boxes.Outlet;
import photopanel.boxes.datatypes.Datatype;
import photopanel.boxes.datatypes.ValueArray;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class CopyValueArrayBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public CopyValueArrayBox(int x, int y) {
        if (x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }

        inlets.add(new Inlet(this, Datatype.VALUE_ARRAY));

        outlets.add(new Outlet(this, Datatype.VALUE_ARRAY));
        outlets.add(new Outlet(this, Datatype.VALUE_ARRAY));

        setX(x);
        setY(y);
    }

    public static String NAME = "Werte-Array kopieren";
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
        ValueArray va = (ValueArray) ds[0];
        return new Datatype[] {va, new ValueArray(va.getValues().clone(), va.getDescription())};
    }
}
