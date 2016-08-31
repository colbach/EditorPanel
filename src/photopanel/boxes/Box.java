package photopanel.boxes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import photopanel.boxes.listener.DragListening;
import photopanel.boxes.parameters.Parameter;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;
import static photopanel.StaticManager.*;
import photopanel.boxes.datatypes.Datatype;
import photopanel.boxes.parameters.MultipleOptionsParameter;
import photopanel.boxes.parameters.NotePseudoParameter;
import photopanel.boxes.parameters.SliderParameter;
import photopanel.boxes.parameters.ToogleParameter;
import photopanel.boxes.parameters.ValueParameter;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public abstract class Box implements DragListening, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    protected ArrayList<Inlet> inlets = new ArrayList<>();
    protected ArrayList<Outlet> outlets = new ArrayList<>();
    
    protected ArrayList<Parameter> parameters = new ArrayList<>();
    
    private int x, y;
    
    public int off = BOX_HEADER_HEIGHT;
    
    private boolean finished = false;
    
    protected void addInlet(String datatyp) {
        inlets.add(new Inlet(this, datatyp));
    }
    
    protected void addOutlet(String datatyp) {
        outlets.add(new Outlet(this, datatyp));
    }
    
    protected MultipleOptionsParameter addMultipleOptionsParameter(String[] options, int index, String description) {
        if(!description.endsWith(":")) {
            description += ":";
        }
        MultipleOptionsParameter p = new MultipleOptionsParameter(this, off, options, index, description);
        parameters.add(p);
        off += p.getHeight();
        return p;
    }
    
    protected void addNote(String description, BufferedImage bi) {
        NotePseudoParameter p = new NotePseudoParameter(this, off, description, bi);
        parameters.add(p);
        off += p.getHeight();
    }
    
    protected void addNote(String description) {
        NotePseudoParameter p = new NotePseudoParameter(this, off, description);
        parameters.add(p);
        off += p.getHeight();
    }
    
    protected SliderParameter addSliderParameter(double defaultValue, double minimum, double maximum, String description) {
        if(!description.endsWith(":")) {
            description += ":";
        }
        SliderParameter p = new SliderParameter(this, off, defaultValue, minimum, maximum, description);
        parameters.add(p);
        off += p.getHeight();
        return p;
    }
    
    protected ToogleParameter addToogleParameter(boolean defaultValue, String description) {
        ToogleParameter p = new ToogleParameter(this, off, defaultValue, description);
        parameters.add(p);
        off += p.getHeight();
        return p;
    }
    
    protected ValueParameter addValueParameter(String defaultValue, String description) {
        if(!description.endsWith(":")) {
            description += ":";
        }
        ValueParameter p = new ValueParameter(this, off, defaultValue, description);
        parameters.add(p);
        off += p.getHeight();
        return p;
    }
    
    protected ValueParameter addValueParameter(String description) {
        if(!description.endsWith(":")) {
            description += ":";
        }
        ValueParameter p = new ValueParameter(this, off, "", description);
        parameters.add(p);
        off += p.getHeight();
        return p;
    }
    
    protected ValueParameter addValueParameter(String defaultValue, String description, int forcedFieldWidth) {
        if(!description.endsWith(":")) {
            description += ":";
        }
        ValueParameter p = new ValueParameter(this, off, defaultValue, description, forcedFieldWidth);
        parameters.add(p);
        off += p.getHeight();
        return p;
    }
    
    public ArrayList<Inlet> getInlets() {
        return inlets;
    }

    public ArrayList<Outlet> getOutlets() {
        return outlets;
    }

    public ArrayList<Parameter> getParameters() {
        return parameters;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public abstract String getName();
    
    public abstract String getTitel();
    
    public abstract String getCategory();
    
    // === Zeichnen ===
    
    public void drawUnfilled(Graphics g) {
        
        int width = getWidth(g);
        int height = getHeight();
        
        // --- Zeichne Box ---
        if(getActiveBox() == this) {
            drawBoxBackgroundActive(g, x, y, width, height);
        } else {
            drawBoxBackground(g, x, y, width, height);
        }
        //drawDottedArea(g, x + 3, y + 3, width - 5, 30);
        g.setColor(BOX_TITLE_BACKGROUND_COLOR);
        g.fillRect(x + 3, y + 3, width - 5, BOX_HEADER_HEIGHT);
        drawTitle(g, x, y+3, width, getTitel());
        
        // --- Zeichne Parmameter ---
        for(Parameter p : parameters) {
            p.drawUnfilled(g);
        }
        
        // --- Zeichne Lets ---
        g.setColor(BOX_BORDER_COLOR);
        
        // In
        g.setFont(LET_FONT);
        int yIn = y + height - 5;
        for(int i=inlets.size()-1; i>=0; i--) {
            g.drawOval(x + 6, yIn-7, 5, 5);
            g.drawString(inlets.get(i).getDatatypeID(), x + 20, yIn);
            yIn -= BOX_FOOTER_PER_LET_HEIGHT;
        }
        if(inlets.size() != 0) {
            g.setFont(LET_TITLE_FONT);
            g.drawString(STRING_INPUT, x+9, yIn+2);
        }
        
        // Out
        g.setFont(LET_FONT);
        int yOut = y + height - 5;
        for(int i=outlets.size()-1; i>=0; i--) {
            g.drawOval(x + width - 13, yOut-7, 5, 5);
            g.drawString(outlets.get(i).getDatatypeID(), x + width - getTextWidth(g, outlets.get(i).getDatatypeID()) - 20, yOut);
            yOut -= BOX_FOOTER_PER_LET_HEIGHT;
        }
        if(outlets.size() != 0) {
            g.setFont(LET_TITLE_FONT);
            g.drawString(STRING_OUTPUT, x + width - getTextWidth(g, STRING_OUTPUT) - 10, yOut+2);
        }
    }
    
    public int getYPositionForOutlet(Outlet out) {
        int yOut = y + getHeight() - 5;
        for(int i=outlets.size()-1; i>=0; i--) {
            if(outlets.get(i) == out) {
                return yOut-5;
            }
            yOut -= BOX_FOOTER_PER_LET_HEIGHT;
        }
        throw new RuntimeException("Outlet scheint nicht zu Box zu gehören");
    }
    
    public int getYPositionForInlet(Inlet in) {
        int yIn = y + getHeight() - 5;
        for(int i=inlets.size()-1; i>=0; i--) {
            if(inlets.get(i) == in) {
                return yIn-5;
            }
            yIn -= BOX_FOOTER_PER_LET_HEIGHT;
        }
        throw new RuntimeException("Outlet scheint nicht zu Box zu gehören");
    }
    
    public void drawFill(Graphics g) {
        
        int width = 200;
        int height = 300;
        
        // Zeichne Parmameter
        for(Parameter p : parameters) {
            p.drawFill(g);
        }
    }
    
    public void whiteOut(Graphics g) {
        // Zeichne Parmameter
        for(Parameter p : parameters) {
            p.whiteOut(g);
        }
    }
    
    int cachedHeight = -1;
    public int getHeight() {
        if(cachedHeight < 0) {
            int height = 0;
            for(Parameter p : parameters) {
                height += p.getHeight();
            }
            height += (Math.max(inlets.size(), outlets.size()) + 1) * BOX_FOOTER_PER_LET_HEIGHT;
            cachedHeight = height + BOX_HEADER_HEIGHT;
        }
        return cachedHeight;
    }
    
    private int cachedWidth = -1;
    public int getWidth(Graphics g) {
        if(cachedWidth < 0) {
            int maxParMin = MINIMUM_BOX_WIDTH;
            for(Parameter p : parameters) {
                int parMin = p.getMinimumWidth(g);
                if(parMin > maxParMin) {
                    maxParMin = parMin;
                }
            }
            cachedWidth = maxParMin;
        }
        return cachedWidth;
    }

    public int getCachedHeight() {
        return cachedHeight;
    }

    public int getCachedWidth() {
        return cachedWidth;
    }
    
    public boolean reactTo(int x, int y) {
        if(insects(x, y)) {
            // DragListener anmelden
            dragOfX = x - this.x;
            dragOfY = y - this.y;
            registerDragListeningParameter(this);
            registerActiveBox(this);
            // Parameter
            for(Parameter p : parameters) {
                boolean reacted = p.reactTo(x, y);
                if(reacted)
                    return true;
            }
            // Lets kontrolieren
            if(x > this.x + cachedWidth/2) { // outlet 
                int yOut = this.y+cachedHeight-BOX_FOOTER_PER_LET_HEIGHT;
                for(int i=outlets.size()-1; i>=0; i--) {
                    if(y > yOut) {
                        //System.out.println("Outlet " + i);
                        registerWireingLet(outlets.get(i), this.x + cachedWidth, yOut + BOX_FOOTER_PER_LET_HEIGHT/2);
                        return true;
                    }
                    yOut -= BOX_FOOTER_PER_LET_HEIGHT;
                }
            } else { // inlet
                int yIn = this.y+cachedHeight-BOX_FOOTER_PER_LET_HEIGHT;
                for(int i=inlets.size()-1; i>=0; i--) {
                    if(y > yIn) {
                        //System.out.println("Intlet " + i);
                        registerWireingLet(inlets.get(i), this.x, yIn + BOX_FOOTER_PER_LET_HEIGHT/2);
                        return true;
                    }
                    yIn -= BOX_FOOTER_PER_LET_HEIGHT;
                }
            }
            return true;
        }
        return false;
    }
    
    private boolean insects(int x, int y) {
        return x > this.x && x < this.x + cachedWidth && y > this.y && y < this.y + getHeight();
    }
    
    public int dragOfX = -1;
    public int dragOfY = -1;
    
    @Override
    public void reactToDrag(int x, int y) {
        this.x = x - dragOfX;
        this.y = y - dragOfY;
    }
    
    /**
     * Gibt zuruck ob Punkt auf Box war.
     * ACHTUNG Gibt nicht an ob er tatsechlich verbinden kann!
     */
    public boolean wire(Let let, int x, int y) {
        if(insects(x, y)) {
            if(let instanceof Inlet && x > this.x + cachedWidth/2) { // outlet 
                int yOut = this.y+cachedHeight-BOX_FOOTER_PER_LET_HEIGHT;
                for(int i=outlets.size()-1; i>=0; i--) {
                    if(y > yOut) {
                        Inlet inlet = (Inlet) let;
                        if(outlets.get(i).isSameDatatype(inlet)) {
                            outlets.get(i).unwire();
                            inlet.unwire();
                            boolean worked = outlets.get(i).wireWith(inlet);
                            if(!worked) {
                                throw new RuntimeException("Kann Wire nicht setzen!");
                            }
                        } else {
                            System.out.println("Falscher Typ! " + outlets.get(i).getDatatypeID() + " und "+ inlet.getDatatypeID());
                        }
                        return true;
                    }
                    yOut -= BOX_FOOTER_PER_LET_HEIGHT;
                }
            } else if(let instanceof Outlet && x < this.x + cachedWidth/2) { // inlet
                int yIn = this.y+cachedHeight-BOX_FOOTER_PER_LET_HEIGHT;
                for(int i=inlets.size()-1; i>=0; i--) {
                    if(y > yIn) {
                        Outlet outlet = (Outlet) let;
                        if(outlet.isSameDatatype(inlets.get(i))) {
                            inlets.get(i).unwire() ;
                            outlet.unwire();
                            boolean worked = outlet.wireWith(inlets.get(i));
                            if(!worked) {
                                throw new RuntimeException("Kann Wire nicht setzen!");
                            }
                        } else {
                            System.out.println("Falscher Typ! " + inlets.get(i).getDatatypeID() + " und "+ outlet.getDatatypeID());
                        }
                        return true;
                    }
                    yIn -= BOX_FOOTER_PER_LET_HEIGHT;
                }
            }
            
            return true;
        }
        return false;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    public boolean canTakeAll() {
        for(Inlet in : inlets) {
            if(!in.canTake()) {
                return false;
            }
        }
        return true;
    }
    
    public Datatype[] takeAll() {
        if(!canTakeAll())
            throw new RuntimeException("Es sind noch nicht alle Daten an den Inlets angekommen!");
        Datatype[] ds = new Datatype[inlets.size()];
        for(int i=0; i<ds.length; i++) {
            ds[i] = inlets.get(i).take();
        }
        return ds;
    }
    
    public boolean allInletsAreWired() {
        for(Inlet in : inlets) {
            if(!in.isWired())
                return false;
        }
        return true;
    }
    
    public void run() throws Exception {
        if(!canTakeAll())
            throw new RuntimeException("canTakeAll()==false. Nicht alle Inlets sind angekommen!");
        
        Datatype[] allIn = takeAll();
        Datatype[] allOut = run(allIn);
        
        if(allOut.length != outlets.size())
            throw new RuntimeException("Laengev on allOut (" + allOut.length + ") stimmt nicht nicht Anzahl Outlets ( " + outlets.size() + ") ueberein!");
        
        for(int i=0; i<allOut.length; i++) {
            Outlet o = outlets.get(i);
            o.give(allOut[i]);
        }
    }
    
    public abstract Datatype[] run(Datatype[] ds) throws Exception;
    
    public void destroy() {
        for(Inlet inlet : inlets) {
            inlet.unwire();
        }
        for(Outlet outlet : outlets) {
            outlet.unwire();
        }
    }

    public Parameter p(int index) {
        return parameters.get(index);
    }
    
}
