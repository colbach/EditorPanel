package photopanel.boxes.datatypes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class Bitmap extends Datatype {

    @Override
    public String getDatatypeID() {
        return Datatype.BITMAP;
    }
    
    private final String filename; // ohne Endung
    
    private BufferedImage bufferedImage = null;

    public Bitmap(BufferedImage bufferedImage, String filename) {
        this.bufferedImage = bufferedImage;
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public BufferedImage getImage() {
        return bufferedImage;
    }

    public void setImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    public int getRGB(int x, int y) {
        return bufferedImage.getRGB(x, y);
    }

    public synchronized void setRGB(int x, int y, int rgb) {
        bufferedImage.setRGB(x, y, rgb);
    }

    public int getWidth() {
        return bufferedImage.getWidth();
    }

    public int getHeight() {
        return bufferedImage.getHeight();
    }

    public Graphics getGraphics() {
        return bufferedImage.getGraphics();
    }

    public int getTileWidth() {
        return bufferedImage.getTileWidth();
    }

    @Override
    public String getDatatypeName() {
        return getDatatypeID();
    }

    @Override
    public Bitmap copy() {
        return new Bitmap(deepCopy(bufferedImage), filename);
    }
    
    public static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    } // http://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage

    @Override
    public String getDescription() {
        return getFilename();
    }

    @Override
    public void saveToFile(File file) throws Exception {
        if(file.getAbsolutePath().toLowerCase().endsWith(".png")) {
            if(file.exists()) {
                Object[] options = {"Ja", "Nein"};
                int n = JOptionPane.showOptionDialog(
                            null,
                            "Die Datei " + file.getName() + " existiert bereits. Wollen sie diese Datei überschreiben?",
                            "Datei überschreiben",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                if(n == 0)
                    ImageIO.write(bufferedImage, "png", file);
            }
        } else if(file.getAbsolutePath().toLowerCase().endsWith(".jpg") || file.getAbsolutePath().toLowerCase().endsWith(".jpeg")) {
            if(file.exists()) {
                Object[] options = {"Ja", "Nein"};
                int n = JOptionPane.showOptionDialog(
                            null,
                            "Die Datei " + file.getName() + " existiert bereits. Wollen sie diese Datei überschreiben?",
                            "Datei überschreiben",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                if(n == 0)
                    ImageIO.write(bufferedImage, "jpg", file);
            }
        } else {
            file = new File(file.getAbsolutePath() + ".png");
            if(file.exists()) {
                Object[] options = {"Ja", "Nein"};
                int n = JOptionPane.showOptionDialog(
                            null,
                            "Die Datei " + file.getName() + " existiert bereits. Wollen sie diese Datei überschreiben?",
                            "Datei überschreiben",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]);
                if(n != 0) {
                    System.out.println("Abgebrochen");
                    return;
                }
            }
        }
        
        System.out.println("Speicher: " + file.getAbsoluteFile());
        ImageIO.write(bufferedImage, "png", file);
    }
    
}
