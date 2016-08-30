package photopanel.boxes.datatypes;

import java.io.File;
import photopanel.views.ShowResultFrame;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public abstract class Datatype {
    
    public static final String BITMAP = "Bitmap";
    public static final String SINGLE_VALUE = "Wert";
    public static final String TRUE_FALSE = "Bool";
    public static final String VALUE_ARRAY = "Array";
    
    public abstract String getDatatypeID();
    public abstract String getDatatypeName();
    public abstract String getDescription();
    
    public abstract Datatype copy();
    
    public void show() {
        Datatype thisValue = this;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowResultFrame(thisValue).setVisible(true);
            }
        });
    }
    
    public abstract void saveToFile(File f) throws Exception;
}
