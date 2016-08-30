package photopanel.boxes.implementations.imagesize;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;


/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class CropExpandBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;

    ValueParameter pL, pR, pU, pO;
    ToogleParameter pMid;
    MultipleOptionsParameter pMode;
    
    public CropExpandBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
        
        pMode = addMultipleOptionsParameter(new String[] {"Beschneiden", "Anfügen"}, 0, "Modus");
        
        pL = addValueParameter("0", "links (Angabe in px oder %)");
        pR = addValueParameter("0", "rechts (Angabe in px oder %)");
        pU = addValueParameter("0", "unten (Angabe in px oder %)");
        pO = addValueParameter("0", "oben (Angabe in px oder %)");
        
        //pMid = addToogleParameter(false, "Von Mitte aus betrachten");
    }
    
    public static String NAME = "Beschneiden / Anfügen";
    public static String TITLE = "Beschneiden / Anfügen";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_BILDGROESSE;
    
    
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
        
        Bitmap bitmap = (Bitmap) ds[0];
        String sL = pL.getStringTrimmed(), sR = pR.getStringTrimmed(), sU = pU.getStringTrimmed(), sO = pO.getStringTrimmed();
        int originalWidth = bitmap.getWidth(), originalHeight=bitmap.getHeight();
        int l=stringToInt(sL, originalWidth), r=stringToInt(sR, originalWidth), u=stringToInt(sU, originalHeight), o=stringToInt(sO, originalHeight);
        
        BufferedImage image = bitmap.getImage();
        
        if(pMode.is(0)) {// Bild beschneiden
            BufferedImage subImage = image.getSubimage(l, o, originalWidth-l-r, originalHeight-u-o); //fill in the corners of the desired crop location here
            BufferedImage copyOfImage = new BufferedImage(subImage.getWidth(), subImage.getHeight(), image.getType());
            Graphics g = copyOfImage.createGraphics();
            g.drawImage(subImage, 0, 0, null);
            bitmap.setImage(subImage);
            System.gc();
        } else if(pMode.is(1)) {
            BufferedImage newImage = new BufferedImage(image.getWidth()+l+r, image.getHeight()+o+u, BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(image, l, o, null);
            bitmap.setImage(newImage);
            System.gc();
        }
        
        return new Datatype[] { bitmap };
    }
    
    public int stringToInt(String s, int rel) {
        s = s.trim();
        if(s.endsWith("%")) {
            s = s.substring(0, s.length()-1);
            s = s.trim();
            return (int)((Double.valueOf(s) / 100) * rel);
        } else {
            s = s.replaceAll("px", "");
            return (int)(Double.valueOf(s) * 1d);
        }
    }
    
}
