package photopanel.boxes.implementations.imageediting;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingConstants.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class Convolve3x3Box extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    ValueParameter v00, v01, v02,
                   v10, v11, v12,
                   v20, v21, v22;
    
    ToogleParameter disableParameter;

    public Convolve3x3Box(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        setX(x);
        setY(y);
        
        BufferedImage bi = new BufferedImage(150, 55, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setFont(PARAMETER_FONT);
        g.setColor(Color.BLACK);
        g.drawString("M =", 0, 35);
        g.drawString("m00", 30, 20);
        g.drawString("m10", 30, 35);
        g.drawString("m20", 30, 50);
        g.drawString("m01", 60, 20);
        g.drawString("m11", 60, 35);
        g.drawString("m21", 60, 50);
        g.drawString("m02", 90, 20);
        g.drawString("m12", 90, 35);
        g.drawString("m22", 90, 50);
        g.drawLine(25, 5, 25, 53);
        g.drawLine(118, 5, 118, 53);
        g.drawLine(113, 5, 118, 5);
        g.drawLine(113, 53, 118, 53);
        g.drawLine(25, 5, 30, 5);
        g.drawLine(25, 53, 30, 53);
        
        addNote("Matrix M angeben.\nDie Summe aller Elementen der\nMatrix muss 1 ergeben damit die\nHelligkeit des Bildes erhalten bleibt.", bi);
        
        v00 = addValueParameter("0", "m00", 130);
        v01 = addValueParameter("0", "m01", 130);
        v02 = addValueParameter("0", "m02", 130);
        v10 = addValueParameter("0", "m10", 130);
        v11 = addValueParameter("1", "m11", 130);
        v12 = addValueParameter("0", "m12", 130);
        v20 = addValueParameter("0", "m20", 130);
        v21 = addValueParameter("0", "m21", 130);
        v22 = addValueParameter("0", "m22", 130);
        
        disableParameter = addToogleParameter(false, "Deaktivieren");
        
    }
    
    public static String NAME = "Faltung (Erweitert)";
    public static String TITLE = "Faltung";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_EFFEKTE;
    
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
        if(disableParameter.isSelected()) {
            return ds;
        }
        
        Bitmap b = (Bitmap) ds[0];
        BufferedImage sourceImage = b.getImage();
        
        BufferedImage dstImage = null;
        float[] m = new float[] {
             v00.getFloat(), v01.getFloat(), v02.getFloat(),
             v10.getFloat(), v11.getFloat(), v12.getFloat(),
             v20.getFloat(), v21.getFloat(), v22.getFloat()
        };
        Kernel kernel = new Kernel(3, 3, m);
        ConvolveOp op = new ConvolveOp(kernel);
        dstImage = op.filter(sourceImage, null);
        
        b.setImage(dstImage);
        
        return new Datatype[] { b };
    }
    
}
