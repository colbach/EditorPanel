package photopanel.boxes.implementations.structure.logic;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class RedruceValueArrayBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private MultipleOptionsParameter options;

    public RedruceValueArrayBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.VALUE_ARRAY));
        
        outlets.add(new Outlet(this, Datatype.SINGLE_VALUE));
        
        options = addMultipleOptionsParameter(
                new String[] {
                    "Minimum", "Maximum", "Summe", "Produkt", "Durchschnitt", "Meridian"
                }
                , 2, "Methode");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Werte-Array auf Wert reduzieren";
    public static String TITLE = "Redruce";
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
        
        if(options.getIndex() == 0) {
            double min = Double.MAX_VALUE;
            ValueArray va = (ValueArray) ds[0];
            for(double v : va.getValues()) {
                if(v < min) {
                    min = v;
                }
            }
            return new Datatype[] { new SingleValue(min, "Minimum")};
            
        } else if(options.getIndex() == 1) {
            double max = Double.MIN_VALUE;
            ValueArray va = (ValueArray) ds[0];
            for(double v : va.getValues()) {
                if(v > max) {
                    max = v;
                }
            }
            return new Datatype[] { new SingleValue(max, "Maximum")};
            
        } else if(options.getIndex() == 2) {
            double sum = 0;
            ValueArray va = (ValueArray) ds[0];
            for(double v : va.getValues()) {
                sum += v;
            }
            return new Datatype[] { new SingleValue(sum, "Summe")};
            
        } else if(options.getIndex() == 3) {
            double prod = 1;
            ValueArray va = (ValueArray) ds[0];
            for(double v : va.getValues()) {
                prod *= v;
            }
            return new Datatype[] { new SingleValue(prod, "Produkt")};
            
        } else if(options.getIndex() == 4) {
            double sum = 0;
            ValueArray va = (ValueArray) ds[0];
            for(double v : va.getValues()) {
                sum += v;
            }
            return new Datatype[] { new SingleValue(sum/va.getValues().length, "Durchschnitt")};
            
        } else if(options.getIndex() == 5) {
            double sum = 0;
            double[] va = ((ValueArray) ds[0]).getValues();
            if(va.length%2 == 0) {
                return new Datatype[] { new SingleValue(va[va.length/2-1]/2 + va[va.length/2]/2, "Meridian")};
            } else {
                return new Datatype[] { new SingleValue(va[va.length/2]/2, "Meridian")};
            }
        } else {
            throw new UnsupportedOperationException("Unbestimmte Option!");
        }
    }
    
}
