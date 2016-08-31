package photopanel.views;

import photopanel.boxes.implementations.imageediting.SharpenBox;
import photopanel.boxes.implementations.imagesize.CropExpandBox;
import photopanel.boxes.implementations.imageanalyse.HistogramBox;
import photopanel.boxes.implementations.structure.logic.RedruceValueArrayBox;
import photopanel.boxes.implementations.structure.logic.CompareValueArrayBox;
import photopanel.boxes.implementations.structure.copy.CopyImageBox;
import photopanel.boxes.implementations.structure.copy.CopyValueArrayBox;
import photopanel.boxes.implementations.output.show.ShowSingleValueBox;
import photopanel.boxes.implementations.output.show.ShowValueArrayBox;
import photopanel.boxes.implementations.input.ImageFromPathBox;
import java.awt.Graphics;
import javax.swing.JPanel;
import photopanel.boxes.*;

import static photopanel.drawing.DrawingTools.*;
import static photopanel.drawing.DrawingConstants.*;
import static photopanel.StaticManager.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class OverviewPanel extends JPanel {
    
    public void setExampleTestSomething() {
        reset();
        addBox(new ImageFromPathBox(10, 50));
        addBox(new CropExpandBox(320, 80));
        addBox(new CopyImageBox(50, 200));
        addBox(new HistogramBox(200, 350));
        addBox(new HistogramBox(250, 400));
        addBox(new SharpenBox(200, 450));
        addBox(new CopyValueArrayBox(600, 350));
        addBox(new ShowValueArrayBox(900, 350));
        addBox(new RedruceValueArrayBox(700, 200));
        addBox(new CompareValueArrayBox(600, 300));
        addBox(new ShowSingleValueBox(900, 100));
    }
    
    private int letToX = -1;
    private int letToY = -1;

    public OverviewPanel() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(PRINT_EVENTS)
            System.out.println("paintComponent(Graphics g)");
        
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        for(Box box : getBoxes()) {
            box.drawUnfilled(g);
            box.drawFill(g);
            for(Outlet o : box.getOutlets()) {
                o.draw(g);
            }
        }
        
        if(getWireingLet() != null) {
            drawPreviewWire(g, getWireingLetX(), getWireingLetY(), letToX, letToY);
        }
    }

    public void mousePressed(int x, int y) {
        if(PRINT_EVENTS)
            System.out.println("mousePressed(" + x + ", " + y + ")");
        setKeyListening(null);
        boolean matchedABox = false;
        for(int i=getBoxes().size()-1; i>=0; i--) {
            boolean matched = getBoxes().get(i).reactTo(x, y);
            if(matched) {
                matchedABox = true;
                break;
            }
        }
        if(!matchedABox) {
            setActiveBox(null);
        }
        if(getWireingLet() != null) {
            letToX = x;
            letToY = y;
        }
        repaint();
    }
    
    public void keyTyped(char keyChar) {
        if(PRINT_EVENTS)
            System.out.println("keyTyped(" + keyChar + " = " + (int)keyChar + ")");
        if(keyChar == 127 || keyChar == 4) { // Box löschen
            removeBox(getActiveBox());
            repaint();
        } else if(getKeyListening() != null) {
            getKeyListening().react(keyChar);
            repaint();
        }
    }
    
    void mouseIsDragging(int fromX, int fromY, int x, int y) {
        if(PRINT_EVENTS)
            System.out.println("... " + x + " " + y);
        if(getDragListening() != null) {
            getDragListening().reactToDrag(x, y);
            repaint();
        }
        if(getWireingLet() != null) {
            letToX = x;
            letToY = y;
            repaint();
        }
    }

    public void mouseDragged(int fromX, int fromY, int toX, int toY) {
        if(PRINT_EVENTS)
            System.out.println("mouseDragged(" + fromX + ", " + fromY + ", " + toX + ", " + toY + ")");
    }

    void mouseReleased(int x, int y) {
        if(PRINT_EVENTS)
            System.out.println("mouseReleased(" + x + ", " + y + ")");
        if(getWireingLet() != null) {
            for(int i=getBoxes().size()-1; i>=0; i--) {
                boolean matched = getBoxes().get(i).wire(getWireingLet(), x, y);
                if(matched)
                    break;
            }
        }
        setDragListening(null);
        setWireingLet(null);
        repaint();
    }
    
}
