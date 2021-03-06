package photopanel.boxes.implementations.input;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class ImageFileFilter extends FileFilter {
       
    @Override
    public boolean accept(File f) {
        if(f == null)
            return false;
        else if(f.getName().toLowerCase().endsWith(".jpg"))
            return true;
        else if(f.getName().toLowerCase().endsWith(".jpeg"))
            return true;
        else if(f.getName().toLowerCase().endsWith(".png"))
            return true;
        else if(f.getName().toLowerCase().endsWith(".bmp"))
            return true;
        else
            return false;
    }

    @Override
    public String getDescription() {
        return ".jpg .jpeg .png .bmp";
    }
}
