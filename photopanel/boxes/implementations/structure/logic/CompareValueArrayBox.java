package photopanel.boxes.implementations.structure.logic;

import java.io.Serializable;
import javax.swing.JOptionPane;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class CompareValueArrayBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private MultipleOptionsParameter options;

    public CompareValueArrayBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.VALUE_ARRAY));
        inlets.add(new Inlet(this, Datatype.VALUE_ARRAY));
        
        outlets.add(new Outlet(this, Datatype.VALUE_ARRAY));
        
        options = addMultipleOptionsParameter(
                new String[] {
                    "Subtrahieren", "Addieren", "Durchschnitt", "Produkt", "Maximum", "Minimum"
                }
                , 0, "Methode");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Werte-Arrays vergleichen";
    public static String TITLE = "Vergleichen";
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
        double[] va1 = ((ValueArray)ds[0]).getValues();
        double[] va2 = ((ValueArray)ds[0]).getValues();
        if(va1.length != va2.length) {
            JOptionPane.showMessageDialog(null, "Das erste Array hat eine Länge von " + va1.length + " und das zweite Array hat eine Länge von " + va2.length + ". Damit diese Richtig verglichen werden können sollen diese gleich lang sein. Das Ergebniss wird eventuell nicht aussagekräftig sein!");
        }
        
        if(options.getIndex() == 0) {
            double[] vs = new double[Math.max(va1.length, va2.length)];
            for(int i=0; i<vs.length; i++) {
                double v1 = 0;
                if(va1.length > i)
                    v1 = va1[i];
                double v2 = 0;
                if(va2.length > i)
                    v2 = va2[i];
                vs[i] = v1 - v2;
            }
            return new Datatype[] { new ValueArray(vs, "Subtraktion")};
            
        } else if(options.getIndex() == 1) {
            double[] vs = new double[Math.max(va1.length, va2.length)];
            for(int i=0; i<vs.length; i++) {
                double v1 = 0;
                if(va1.length > i)
                    v1 = va1[i];
                double v2 = 0;
                if(va2.length > i)
                    v2 = va2[i];
                vs[i] = v1 + v2;
            }
            return new Datatype[] { new ValueArray(vs, "Addition")};
            
        } else if(options.getIndex() == 2) {
            double[] vs = new double[Math.max(va1.length, va2.length)];
            for(int i=0; i<vs.length; i++) {
                double v1 = 0;
                if(va1.length > i)
                    v1 = va1[i];
                double v2 = 0;
                if(va2.length > i)
                    v2 = va2[i];
                vs[i] = (v1 + v2) / 2;
            }
            return new Datatype[] { new ValueArray(vs, "Durchschnitt")};
            
        } else if(options.getIndex() == 3) {
            double[] vs = new double[Math.max(va1.length, va2.length)];
            for(int i=0; i<vs.length; i++) {
                double v1 = 1;
                if(va1.length > i)
                    v1 = va1[i];
                double v2 = 1;
                if(va2.length > i)
                    v2 = va2[i];
                vs[i] = v1 * v2;
            }
            return new Datatype[] { new ValueArray(vs, "Produkt")};
            
        } else if(options.getIndex() == 4) {
            double[] vs = new double[Math.max(va1.length, va2.length)];
            for(int i=0; i<vs.length; i++) {
                double v1 = Double.MIN_VALUE;
                if(va1.length > i)
                    v1 = va1[i];
                double v2 = Double.MIN_VALUE;
                if(va2.length > i)
                    v2 = va2[i];
                vs[i] = Math.max(v1, v2);
            }
            return new Datatype[] { new ValueArray(vs, "Maximum")};
            
        } else if(options.getIndex() == 5) {
            double[] vs = new double[Math.max(va1.length, va2.length)];
            for(int i=0; i<vs.length; i++) {
                double v1 = Double.MAX_VALUE;
                if(va1.length > i)
                    v1 = va1[i];
                double v2 = Double.MAX_VALUE;
                if(va2.length > i)
                    v2 = va2[i];
                vs[i] = Math.min(v1, v2);
            }
            return new Datatype[] { new ValueArray(vs, "Minimum")};
        } else {
            throw new UnsupportedOperationException("Unbestimmte Option!");
        }
        
    }
    
}
