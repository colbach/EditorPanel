package photopanel.boxes.implementations.imageanalyse;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class AttributeBox extends Box implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    MultipleOptionsParameter aP;

    public AttributeBox(int x, int y) {
        if(x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }
        
        inlets.add(new Inlet(this, Datatype.BITMAP));
        
        outlets.add(new Outlet(this, Datatype.SINGLE_VALUE));
        outlets.add(new Outlet(this, Datatype.BITMAP));
        
        aP = addMultipleOptionsParameter(new String[] {"Breite", "Höhe", "Bildtyp"}, 0, "Attribut");
        
        setX(x);
        setY(y);
    }
    
    public static String NAME = "Attribut ermitteln";
    public static String TITLE = "Attribut";
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
    public Datatype[] run(Datatype[] ds) throws Exception {
        Bitmap bi = (Bitmap) ds[0];
        int a = aP.getIndex();
        
        double[] hist = new double[256];
        SingleValue sv;
        String bes = "";
        
        if(a == 0) {
            bes = "Breite";
            sv = new SingleValue(bi.getWidth(), bes);
            
        } else if(a == 1) {
            bes = "Höhe";
            sv = new SingleValue(bi.getHeight(), bes);
            
        } else if(a == 2) {
            bes = "Bildtyp";
            int typ = bi.getImage().getType();
            String typBes;
            switch (typ) {
                case BufferedImage.TYPE_3BYTE_BGR:
                    typBes = "TYPE_3BYTE_BGR";
                    break;
                case BufferedImage.TYPE_4BYTE_ABGR:
                    typBes = "TYPE_4BYTE_ABGR";
                    break;
                case BufferedImage.TYPE_4BYTE_ABGR_PRE:
                    typBes = "TYPE_4BYTE_ABGR_PRE";
                    break;
                case BufferedImage.TYPE_BYTE_BINARY:
                    typBes = "TYPE_BYTE_GRAY";
                    break;
                case BufferedImage.TYPE_BYTE_INDEXED:
                    typBes = "TYPE_BYTE_INDEXED";
                    break;
                case BufferedImage.TYPE_CUSTOM:
                    typBes = "TYPE_CUSTOM";
                    break;
                case BufferedImage.TYPE_INT_ARGB:
                    typBes = "TYPE_INT_ARGB";
                    break;
                case BufferedImage.TYPE_INT_ARGB_PRE:
                    typBes = "TYPE_INT_ARGB_PRE";
                    break;
                case BufferedImage.TYPE_INT_BGR:
                    typBes = "TYPE_INT_BGR";
                    break;
                case BufferedImage.TYPE_INT_RGB:
                    typBes = "TYPE_INT_RGB";
                    break;
                case BufferedImage.TYPE_USHORT_555_RGB:
                    typBes = "TYPE_USHORT_555_RGB";
                    break;
                case BufferedImage.TYPE_USHORT_565_RGB:
                    typBes = "TYPE_USHORT_565_RGB";
                    break;
                case BufferedImage.TYPE_USHORT_GRAY:
                    typBes = "TYPE_USHORT_GRAY";
                    break;
                default:
                    typBes = "Unbekannt";
                    break;
            }
            
            sv = new SingleValue(typBes, typ, bes);
            
        } else {
            throw new Exception("Unbekannte Auswahl");
        }
        
        return new Datatype[] { sv, bi };
    }
    
}
