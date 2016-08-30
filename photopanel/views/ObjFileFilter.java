package photopanel.views;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ObjFileFilter extends FileFilter {
    private static final String ENDUNG = "obj";
       
    @Override
    public boolean accept(File f) {
        if(f == null)
            return false;
        else if(f.isDirectory()) // Ordner anzeigen
            return true;
        else // true, wenn File gewuenschte Endung besitzt
            return f.getName().toLowerCase().endsWith("." + ENDUNG);
    }

    @Override
    public String getDescription() {
        return "Nur " + ENDUNG + "-Dateien";
    }
}
