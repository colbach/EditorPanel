package photopanel.boxes.datatypes;

import java.io.File;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import photopanel.views.ShowResultFrame;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class SingleValue extends Datatype {
    
    
    private double valueAsNumber = Double.NaN;
    private String valueAsString = null;
    private final String description;

    public SingleValue(double number, String description) {
        this.valueAsNumber = number;
        this.description = description;
    }
    
    public SingleValue(String string, String description) {
        this.valueAsString = string;
        this.description = description;
    }
    
    public SingleValue(String string, double number, String description) {
        this.valueAsString = string;
        this.valueAsNumber = number;
        this.description = description;
    }

    public double getDouble() {
        if(valueAsNumber == Double.NaN) {
            return Double.valueOf(valueAsString);
        } else {
            return valueAsNumber;
        }
    }
    
    private String numberString = null;
    public String getNumberAsString() {
        
        if(numberString == null) {
            if(valueAsNumber - ((int)valueAsNumber) == 0) {
                numberString = String.valueOf((int)valueAsNumber);
            } else {
                numberString = String.valueOf(valueAsNumber);
            }
        }
        
        return numberString;
    }
    
    public String getString() {
        if(valueAsString == null) {
            return getNumberAsString();
        } else {
            return valueAsString;
        }
    }
    
    public String getDescription() {
        return description;
    }

    public String getNumberAndString() {
        if(valueAsString == null) {
            return getNumberAsString();
        } else {
            return getNumberAsString() + " ("+ valueAsString + ")";
        }
    }
    
    @Override
    public String toString() {
        return description + ": " + getNumberAsString();
    }

    @Override
    public String getDatatypeID() {
        return Datatype.SINGLE_VALUE;
    }

    @Override
    public String getDatatypeName() {
        return getDatatypeID();
    }
    
    public SingleValue copy() {
        return new SingleValue(valueAsString, valueAsNumber, description);
    }
    
    public void show() {
        SingleValue thisValue = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowResultFrame(thisValue).setVisible(true);
            }
        });
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
