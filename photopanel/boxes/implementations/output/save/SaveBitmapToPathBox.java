package photopanel.boxes.implementations.output.save;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class SaveBitmapToPathBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    ToogleParameter disableParameter;
    ValueParameter pathParameter;
    ValueParameter filenameParameter;

    public SaveBitmapToPathBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        pathParameter = addValueParameter("./Ausgabe/", "Verzeichnis:", 350);
        filenameParameter = addValueParameter("bild.png", "Dateiname (Wenn Feld leer wird Zeitstempel verwendet):", 100);
        
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Bild in Pfad speichern";
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
    
    private static String EXTENTION = ".png";

    private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
    @Override
    public Datatype[] run(Datatype[] ds) throws Exception {
        if(!disableParameter.isSelected()) {
            
            String path = pathParameter.getStringTrimmed();
            if(!path.endsWith("/")) {
                path += "/";
            }
            File pathFile = new File(path);
            pathFile.mkdirs();
            if(filenameParameter.getStringTrimmed().length() > 0) {
                path += filenameParameter.getStringTrimmed();
            } else {
                path += DATE_FORMAT.format(new Date()) + EXTENTION;
            }
            
            File file = new File(path);
            ds[0].saveToFile(file);
        }
        return ds;
    }
    
}
