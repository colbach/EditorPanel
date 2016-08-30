package photopanel.boxes.implementations.structure.copy;

import java.io.Serializable;
import photopanel.boxes.Box;
import photopanel.boxes.Boxes;
import photopanel.boxes.Inlet;
import photopanel.boxes.Outlet;
import photopanel.boxes.datatypes.Datatype;
import photopanel.boxes.datatypes.TrueFalse;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class CopyTrueFalseBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    public CopyTrueFalseBox(int x, int y) {
        if (x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }

        inlets.add(new Inlet(this, Datatype.TRUE_FALSE));

        outlets.add(new Outlet(this, Datatype.TRUE_FALSE));
        outlets.add(new Outlet(this, Datatype.TRUE_FALSE));

        setX(x);
        setY(y);
    }

    public static String NAME = "Wahrheitswert kopieren";
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
        TrueFalse tf = (TrueFalse) ds[0];
        return new Datatype[] {tf, new TrueFalse(tf.isTrue(), tf.getDescription())};
    }
}
