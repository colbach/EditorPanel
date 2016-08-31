package photopanel.boxes.implementations.output.save;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class SaveBitmapViaDialogBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    ToogleParameter disableParameter;

    public SaveBitmapViaDialogBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Bild via Dialog speichern";
    public static String TITLE = "Speichern";
    public static String CATEGORY = Boxes.CATEGORY_IO_AUSGABE_SPEICHERN;
    
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

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
    @Override
    public Datatype[] run(Datatype[] ds) throws Exception {
        if(!disableParameter.isSelected()) {
            
            JFileChooser chooser = new JFileChooser(".");
            int rueckgabeWert = chooser.showSaveDialog(null);
            if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    ds[0].saveToFile(file);
                } catch (Exception exception) {
                    exception.printStackTrace();

                    JOptionPane.showMessageDialog(
                        null,
                        "Fehler: " + exception.getMessage(),
                        "Fehler",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        }
        return ds;
    }
    
}
