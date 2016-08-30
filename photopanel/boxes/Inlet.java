package photopanel.boxes;

import java.awt.Graphics;
import java.io.Serializable;
import photopanel.boxes.datatypes.Datatype;
import static photopanel.drawing.DrawingTools.drawWire;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class Inlet implements Let, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final Box box;
    private final String datatype;
    private Outlet wiredOutlet = null;
    
    private Datatype data = null;

    public Inlet(Box box, String datatype) {
        this.box = box;
        this.datatype = datatype;
    }
    
    public boolean isSameDatatype(Outlet outlet) {
        return outlet.getDatatypeID() == datatype;
    }
    
    public boolean wireWith(Outlet outlet) {
        if(outlet.getDatatypeID() == datatype && !outlet.isWired()) {
            outlet.setWiredInlet(this);
            wiredOutlet = outlet;
            return true;
        }
        return false;
    }
    
    public boolean isWired() {
        return wiredOutlet != null;
    }
    
    public void unwire() {
        if(wiredOutlet != null) {
            wiredOutlet.setWiredInlet(null);
            wiredOutlet = null;
        }
    }

    public Outlet getWiredOutlet() {
        return wiredOutlet;
    }

    public void setWiredOutlet(Outlet wiredOutlet) {
        this.wiredOutlet = wiredOutlet;
    }
    
    public String getDatatypeID() {
        return datatype;
    }

    public Box getBox() {
        return box;
    }
    
    public void draw(Graphics g) {
        if(wiredOutlet != null) {
            int x1 = box.getX() + box.getWidth(g);
            int y1 = box.getYPositionForInlet(this);
            int x2 = wiredOutlet.getBox().getX();
            int y2 = wiredOutlet.getBox().getYPositionForOutlet(wiredOutlet);
            drawWire(g, x2, y2, x1, y1);
        }
    }
    
    public void give(Datatype data) {
        if(!data.getDatatypeID().equals(getDatatypeID())) {
            throw new IllegalArgumentException("Falscher Datentyp! " + data.getDatatypeID() + " != " + getDatatypeID());
        } else {
            this.data = data;
        }
    }
    
    public boolean canTake() {
        return this.data != null;
    }
    
    public Datatype take() {
        if(!canTake())
            throw new RuntimeException("Daten sind noch nicht an Inlet angekommen oder wurden bereits genommen!");
        Datatype data = this.data;
        this.data = null; // Wichtig damit Speicher nicht ueberlauft
        return data;
    }
}
