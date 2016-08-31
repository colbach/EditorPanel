package photopanel.boxes.implementations.input;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class ImageFromDialog extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    ToogleParameter mP;
    ValueParameter nP;
    File last;
    
    public ImageFromDialog(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        mP = addToogleParameter(true, "Auswahl merken");
        nP = addValueParameter("Name");
        
        setX(x);
        setY(y);
       
    }
    
    public static String NAME = "Bild über Dialog";
    public static String TITLE = "Bild";
    public static String CATEGORY = Boxes.CATEGORY_IO_EINGABE;
    
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
    public Datatype[] run(Datatype[] ds) throws Exception {
        if(mP.isSelected() && last != null) {
            BufferedImage bi = ImageIO.read(last);
            Bitmap b = new Bitmap(bi, last.getName());
            return new Datatype[] {b};
        } else {
            last = null;
        }
        JFileChooser chooser = new JFileChooser(".");
        chooser.setDialogTitle(nP.getStringTrimmed());
        chooser.setFileFilter(new ImageFileFilter());
        chooser.setAcceptAllFileFilterUsed(false);
        int rueckgabeWert = chooser.showOpenDialog(null);
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println(file.getAbsolutePath());
            if(file.exists()) {
                BufferedImage bi = ImageIO.read(file);
                Bitmap b = new Bitmap(bi, nP.getStringTrimmed());
                if(mP.isSelected()) {
                    last = file;
                }
                return new Datatype[] {b};
            } else {
                throw new Exception(file.getAbsolutePath() + " existiert nicht");
            }
        }
        throw new Exception("Abgebrochen");
    }
    
}
