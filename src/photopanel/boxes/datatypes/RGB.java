package photopanel.boxes.datatypes;

import java.awt.Color;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class RGB {
    
    /*public static int rgb(byte red, byte green, byte blue) {
        int rgb = red;
        rgb = (rgb << 8) + green;
        rgb = (rgb << 8) + blue;
        return rgb;
    }
    
    public static int rgba(byte red, byte green, byte blue, byte alpha) {
        int rgba = alpha;
        rgba = (rgba << 8) + red;
        rgba = (rgba << 8) + green;
        rgba = (rgba << 8) + blue;
        return rgba;
    }
    
    public static int rgba(int red, int green, int blue, int alpha) {
        return rgba((byte) red, (byte) green, (byte) blue, (byte) alpha);
    }
    public static int rgb(int red, int green, int blue) {
        return rgb((byte) red, (byte) green, (byte) blue);
    }
    
    public static byte r(int rgb) {
        return (byte)((rgb >> 16) & 0xFF);
    }
    
    public static byte g(int rgb) {
        return (byte)((rgb >> 8) & 0xFF);
    }
    
    public static byte b(int rgb) {
        return (byte)(rgb & 0xFF);
    }
    
    public static byte a(int rgb) { // ?
        return (byte)((rgb >> 24) & 0xFF);
    }*/
    
    public static int rgb(byte red, byte green, byte blue) {
        Color c = new Color((red + 256) % 256, (green + 256) % 256, (blue + 256) % 256);
        return c.getRGB();
    }
    
    public static int rgba(byte red, byte green, byte blue, byte alpha) {
        Color c = new Color((red + 256) % 256, (green + 256) % 256, (blue + 256) % 256, (alpha + 256) % 256);
        return c.getRGB();
    }
    
    public static int rgba(int red, int green, int blue, int alpha) {
        return rgba((byte) red, (byte) green, (byte) blue, (byte) alpha);
    }
    public static int rgb(int red, int green, int blue) {
        return rgb((byte) red, (byte) green, (byte) blue);
    }
    
    public static byte r(int rgb) {
        Color c = new Color(rgb);
        return (byte) c.getRed();
    }
    
    public static byte g(int rgb) {
        Color c = new Color(rgb);
        return (byte) c.getGreen();
    }
    
    public static byte b(int rgb) {
        Color c = new Color(rgb);
        return (byte) c.getBlue();
    }
    
    public static byte a(int rgb) {
        Color c = new Color(rgb);
        return (byte) c.getAlpha();
    }
    
    // https://stackoverflow.com/questions/4801366/convert-rgb-values-into-integer-pixel
    // Alternative: http://www.dreamincode.net/forums/topic/216615-converting-rgb-to-integer/
}
