package photopanel.boxes;

import java.awt.Graphics;
import java.io.Serializable;
import photopanel.boxes.datatypes.Datatype;

import static photopanel.drawing.DrawingTools.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class Outlet implements Let, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Box box;
    private final String datatype;
    private Inlet wiredInlet = null;
    
    public Outlet(Box box, String datatype) {
        this.box = box;
        this.datatype = datatype;
    }
    
    public boolean isSameDatatype(Inlet outlet) {
        return outlet.getDatatypeID().equals(datatype);
    }
    
    public boolean wireWith(Inlet inlet) {
        if(inlet.getDatatypeID().equals(datatype) && !inlet.isWired()) {
            inlet.setWiredOutlet(this);
            wiredInlet = inlet;
            return true;
        }
        return false;
    }
    
    public boolean isWired() {
        return wiredInlet != null;
    }
    
    public void unwire() {
        if(wiredInlet != null) {
            wiredInlet.setWiredOutlet(null);
            wiredInlet = null;
        }
    }

    public Inlet getWiredInlet() {
        return wiredInlet;
    }

    public void setWiredInlet(Inlet wiredInlet) {
        this.wiredInlet = wiredInlet;
    }
    
    public String getDatatypeID() {
        return datatype;
    }

    public Box getBox() {
        return box;
    }
    
    public void draw(Graphics g) {
        if(wiredInlet != null) {
            int x1 = box.getX() + box.getWidth(g);
            int y1 = box.getYPositionForOutlet(this);
            int x2 = wiredInlet.getBox().getX();
            int y2 = wiredInlet.getBox().getYPositionForInlet(wiredInlet);
            drawWire(g, x1, y1, x2, y2);
        }
    }
    
    public void give(Datatype data) {
        if(!data.getDatatypeID().equals(getDatatypeID())) {
            throw new IllegalArgumentException("Falscher Datentyp! " + data.getDatatypeID() + " != " + getDatatypeID());
        } else {
            if(wiredInlet != null) {
                wiredInlet.give(data);
            } else {
                System.out.println("Kein Inlet verbunden " + data.getDatatypeID() + " wird verworfen.");
            }
        }
    }
}
