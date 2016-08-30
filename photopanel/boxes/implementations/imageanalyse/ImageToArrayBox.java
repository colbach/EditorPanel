package photopanel.boxes.implementations.imageanalyse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingConstants.*;
import static photopanel.boxes.datatypes.RGB.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class ImageToArrayBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    MultipleOptionsParameter kanalParameter;
    ValueParameter pStart, pEnd, pZeile;

    public ImageToArrayBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.VALUE_ARRAY));
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        BufferedImage bi = new BufferedImage(150, 61, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setFont(PARAMETER_FONT);
        g.setColor(Color.BLACK);
        g.drawLine(10, 10, 110, 10); g.drawLine(110, 10, 10, 20);
        g.drawLine(10, 20, 110, 20); g.drawLine(110, 20, 10, 30);
        g.drawLine(10, 30, 110, 30); g.drawLine(110, 30, 10, 40);
        g.drawLine(10, 40, 110, 40); g.drawLine(110, 40, 10, 50);
        g.drawLine(10, 50, 110, 50); g.drawLine(110, 50, 10, 60);
        g.drawLine(10, 60, 110, 60);
        addNote("Pixel werden Zeile für Zeile hintereinander\ngehangen.", bi);
        
        kanalParameter = addMultipleOptionsParameter(new String[] {"R", "G", "B"/*, "A (falls A unterstützt wird)"*/}, 0, "Kanal");
        
        pStart = addValueParameter("", "Bereich beschränken (Anfang)");
        pEnd = addValueParameter("", "Bereich beschränken (Ende)");
        pZeile = addValueParameter("", "Auf Zeile beschränken");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Farbwerte extrahieren";
    public static String TITLE = "Farbwerte extrahieren";
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
        Bitmap b = (Bitmap) ds[0];
        int width = b.getWidth();
        int height = b.getHeight();
        String bes = "";
        if(kanalParameter.getIndex() == 0) {
            bes = "R";
        } else if(kanalParameter.getIndex() == 1) {
            bes = "G";
        } else if(kanalParameter.getIndex() == 2) {
            bes = "B";
        }// else if(kanalParameter.getIndex() == 3) {
        //    bes = "A";
        //}
        
        int kanal = kanalParameter.getIndex();
        int start;
        if(pStart.getStringTrimmed().length() > 0)
            start = pStart.getInt();
        else
            start = 0;
        
        int end;
        if(pEnd.getStringTrimmed().length() > 0) {
            end = pEnd.getInt();
            if(end > b.getWidth() * b.getHeight() - 1) {
                end = b.getWidth() * b.getHeight() - 1;
            }
        }
        else
            end = b.getWidth() * b.getHeight()-1;
        
        if(pZeile.getStringTrimmed().length() > 0) {
            start = pZeile.getInt() * width;
            end = (pZeile.getInt()+1) * width-1;
        }
        
        bes += " [" + start + ", " + end + "]";
            
        double[] values = new double[end-start+1];
        
        for(int i=0; i<values.length; i++) {
            int rgb = b.getRGB(i%width, i/width);
            if(kanalParameter.getIndex() == 0) {
                values[i] = r(rgb);
            } else if(kanalParameter.getIndex() == 1) {
                values[i] = g(rgb);
            } else if(kanalParameter.getIndex() == 2) {
                values[i] = b(rgb);
            }// else if(kanalParameter.getIndex() == 3) {
            //    values[i] = a(rgb);
            //}
        }
        
        return new Datatype[] { new ValueArray(values, bes), b };
    }
}
