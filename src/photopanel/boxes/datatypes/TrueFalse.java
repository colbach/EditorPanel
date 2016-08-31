package photopanel.boxes.datatypes;

import java.io.File;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class TrueFalse extends Datatype {
    
    private final boolean state;
    private final String description;

    public TrueFalse(boolean state, String description) {
        this.state = state;
        this.description = description;
    }
    
    public boolean isTrue() {
        return state;
    }
    
    public boolean isFalse() {
        return !state;
    }

    public String getDescription() {
        return description;
    }
    
    public String getValueAsString() {
        return String.valueOf(state);
    }
    
    @Override
    public String toString() {
        return description + ": " + getValueAsString();
    }

    @Override
    public String getDatatypeID() {
        return Datatype.TRUE_FALSE;
    }

    @Override
    public String getDatatypeName() {
        return getDatatypeID();
    }
    
    @Override
    public TrueFalse copy() {
        return new TrueFalse(state, description);
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
