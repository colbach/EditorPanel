package photopanel.boxes.implementations.imageanalyse;

import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.boxes.datatypes.RGB.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class HistogramBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    MultipleOptionsParameter kanalParameter;
    ToogleParameter cumulativeParameter;

    public HistogramBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.VALUE_ARRAY));
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        cumulativeParameter = addToogleParameter(false, "Cumulative");
        kanalParameter = addMultipleOptionsParameter(new String[] {"Rot", "Grün", "Blau", "Helligkeit"}, 0, "Kanal");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Histogramm erstellen";
    public static String TITLE = "Histogramm";
    public static String CATEGORY = Boxes.CATEGORY_BILDANALYSE;
    
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
        Bitmap bi = (Bitmap) ds[0];
        int kanal = kanalParameter.getIndex();
        
        double[] hist = new double[256];
        String bes = "";
        if(kanal == 0) {
            bes = "Rot";
        } else if(kanal == 1) {
            bes = "Grün";
        } else if(kanal == 2) {
            bes = "Blau";
        } else if(kanal == 3) {
            bes = "Helligkeit";
        }
        for(int ix=0; ix<bi.getWidth(); ix++) {
            for(int iy=0; iy<bi.getHeight(); iy++) {
                if(kanal == 0) {
                    byte r = r(bi.getRGB(ix, iy));
                    hist[(r+256)%256] = hist[(r+256)%256]+1;
                } else if(kanal == 1) {
                    byte g = g(bi.getRGB(ix, iy));
                    hist[(g+256)%256] = hist[(g+256)%256]+1;
                } else if(kanal == 2) {
                    byte b = b(bi.getRGB(ix, iy));
                    hist[(b+256)%256] = hist[(b+256)%256]+1;
                } else if(kanal == 3) {
                    byte r = r(bi.getRGB(ix, iy));
                    byte g = g(bi.getRGB(ix, iy));
                    byte b = b(bi.getRGB(ix, iy));
                    byte h = (byte)(256d * Math.sqrt(0.299*Math.pow(r/256d,2) + 0.587*Math.pow(g/256d,2) + 0.114*Math.pow(b/256d,2)));
                    hist[(b+256)%256] = hist[(h+256)%256] + 1;
                }
            }
        }
        
        if(cumulativeParameter.isSelected()) {
            for(int i=0; i<hist.length; i++) {
                hist[i] = hist[i] + (i>0 ? hist[i-1] : 0);
            }
        }
        
        return new Datatype[] { new ValueArray(hist, bes), bi };
    }
    
}
