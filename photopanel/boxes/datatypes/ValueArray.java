package photopanel.boxes.datatypes;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ValueArray extends Datatype {
    
    private final double[] values;
    private final String description;

    public ValueArray(ArrayList<Double> values, String description) {
        this.values = new double[values.size()];
        for(int i=0; i<this.values.length; i++) {
            this.values[i] = (double) values.get(i);
        }
        this.description = description;
    }
    
    public ValueArray(double[] values, String description) {
        this.values = values;
        this.description = description;
    }

    public double[] getValues() {
        return values;
    }
    
    public double[] getValuesNormalized0To1() {
        double div = getMaximum() - getMinimum();
        double[] valuesNormalized0To1 = new double[values.length];
        double min = getMinimum();
        
        for(int i=0; i<valuesNormalized0To1.length; i++) {
            double v = values[i] - min;
            valuesNormalized0To1[i] = v/div;
        }
        
        return valuesNormalized0To1;
    }
    
    private boolean maximumCalculated = false;
    private double maximum = Double.MIN_VALUE;
    public double getMaximum() {
        if(!maximumCalculated) {
            for(double value : values) {
                if(maximum < value) {
                    maximum = value;
                }
            }
            maximumCalculated = true;
        }
        return maximum;
    }
    
    private boolean minimumCalculated = false;
    private double minimum = Double.MAX_VALUE;
    public double getMinimum() {
        if(!minimumCalculated) {
            for(double value : values) {
                if(minimum > value) {
                    minimum = value;
                }
            }
            minimumCalculated = true;
        }
        return minimum;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return description + ": " + Arrays.toString(values);
    }

    @Override
    public String getDatatypeID() {
        return Datatype.VALUE_ARRAY;
    }

    @Override
    public String getDatatypeName() {
        return getDatatypeID();
    }
    
    @Override
    public ValueArray copy() {
        return new ValueArray(Arrays.copyOf(values, values.length), description);
    }
    
    @Override
    public void saveToFile(File file) throws Exception {
        if(file.exists()) {
            Object[] options = {"Ja", "Nein"};
            int n = JOptionPane.showOptionDialog(
                null,
                "Die Datei " + file.getName() + " existiert bereits. Wollen sie diese Datei überschreiben?",
                "Datei überschreiben",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            if(n != 0) {
                System.out.println("Abgebrochen");
                return;
            }
        }
        System.out.println("Speicher: " + file.getAbsoluteFile());
        PrintWriter out = new PrintWriter(file);
        out.println(toString());
        out.flush();
        out.close();
    }
}