package photopanel.boxes.implementations.merge;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.boxes.datatypes.RGB.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */

public class MixBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    ToogleParameter pR, pG, pB, pA;
    MultipleOptionsParameter pM;
    
    public MixBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        pM = addMultipleOptionsParameter(new String[] {"Addieren", "Subtrahieren", "Aufhellen", "Abdunkeln"}, 0, "Modus");
        
        pR = addToogleParameter(true, "R");
        pG = addToogleParameter(true, "G");
        pB = addToogleParameter(true, "B");
        pA = addToogleParameter(true, "A (falls A unterstützt wird)");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Bilder mischen";
    public static String TITLE = "Bilder mischen";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_ZUSAMMENFÜHREN;
    
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
        Bitmap b1 = (Bitmap) ds[0];
        Bitmap b2 = (Bitmap) ds[1];
        
        BufferedImage bi1 = b1.getImage();
        BufferedImage bi2 = b2.getImage();
        if(bi1.getWidth() < bi2.getWidth()) {
            int maxW = Math.max(bi1.getWidth(), bi2.getWidth());
            int maxH = Math.max(bi1.getHeight(), bi2.getHeight());
            BufferedImage binew = new BufferedImage(maxW, maxH, bi1.getType());
            Graphics g = binew.getGraphics();
            g.drawImage(bi1, 0, 0, null);
            bi1 = binew;
            System.gc();
        }
        
        boolean haveAlpha1 = bi1.getColorModel().hasAlpha();
        boolean haveAlpha2 = bi2.getColorModel().hasAlpha();
        
        for(int iX = 0; iX<bi2.getWidth(); iX++) {
            for(int iY = 0; iY<bi2.getHeight(); iY++) {
                
                int c1 = bi1.getRGB(iX, iY);
                int c2 = bi2.getRGB(iX, iY);
                
                int red1 = (r(c1) + 256) % 256;
                int green1 = (g(c1) + 256) % 256;
                int blue1 = (b(c1) + 256) % 256;
                
                int red2 = (r(c2) + 256) % 256;
                int green2 = (g(c2) + 256) % 256;
                int blue2 = (b(c2) + 256) % 256;
                
                int alpha1 = Byte.MAX_VALUE;
                if(haveAlpha1) {
                    alpha1 = (a(c1) + 256) % 256;
                }
                int alpha2 = Byte.MAX_VALUE;
                if(haveAlpha2) {
                    alpha2 = (a(c2) + 256) % 256;
                }
                
                if(iX == 5 && iY < 100) System.out.println("pre (1) " + red1 + " " + green1 + " " + blue1 + " " + alpha1 + " (2) " + red2 + " " + green2 + " " + blue2 + " " + alpha2);
                
                if(pM.is(0)) {
                    if(pR.isSelected()) {
                        red1 += red2;
                    }
                    if(pG.isSelected()) {
                        green1 += green2;
                    }
                    if(pB.isSelected()) {
                        blue1 += blue2;
                    }
                    if(pA.isSelected()) {
                        alpha1 += alpha2;
                    }
                } else if(pM.is(1)) {
                    if(pR.isSelected()) {
                        red1 -= red2;
                    }
                    if(pG.isSelected()) {
                        green1 -= green2;
                    } 
                    if(pB.isSelected()) {
                        blue1 -= blue2;
                    }
                    if(pA.isSelected()) {
                        alpha1 -= alpha2;
                    }
                } else if(pM.is(2)) {                    
                    if(pR.isSelected()) {
                        red1 = Math.max(red1, red2);
                    }
                    if(pG.isSelected()) {
                        green1 = Math.max(green1, green2);
                    }
                    if(pB.isSelected()) {
                        blue1 = Math.max(blue1, blue2);
                    }
                    if(pA.isSelected()) {
                        alpha1 = Math.max(alpha1, alpha2);
                    }
                } else if(pM.is(3)) {
                    if(pR.isSelected()) {
                        red1 = Math.min(red1, red2);
                    }
                    if(pG.isSelected()) {
                        green1 = Math.min(green1, green2);
                    }
                    if(pB.isSelected()) {
                        blue1 = Math.min(blue1, blue2);
                    }
                    if(pA.isSelected()) {
                        alpha1 = Math.min(alpha1, alpha2);
                    }
                }
                
                if(iX == 5 && iY < 100) System.out.println("post " + red1 + " " + green1 + " " + blue1 + " " + alpha1);
                
                if(red1 < 0) red1 = 0;
                if(green1 < 0) green1 = 0;
                if(blue1 < 0) blue1 = 0;
                if(alpha1 < 0) alpha1 = 0;
                if(red1 > 255) red1 = 255;
                if(green1 > 255) green1 = 255;
                if(blue1 > 255) blue1 = 255;
                if(alpha1 > 255) alpha1 = 255;
                
                if(iX == 5 && iY < 100) System.out.println("post* " + red1 + " " + green1 + " " + blue1 + " " + alpha1);

                if(haveAlpha1) {
                    bi1.setRGB(iX, iY, rgba(red1, green1, blue1, alpha1));
                } else {
                    bi1.setRGB(iX, iY, rgb(red1, green1, blue1));
                }
            }
        }
        
        b1.setImage(bi1);
        return new Datatype[] { b1 };
    }
    
}
