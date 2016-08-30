package photopanel.boxes;

import photopanel.boxes.implementations.imagesize.*;
import photopanel.boxes.implementations.merge.*;
import photopanel.boxes.implementations.structure.copy.*;
import photopanel.boxes.implementations.output.save.*;
import photopanel.boxes.implementations.output.show.*;
import photopanel.boxes.implementations.imageanalyse.*;
import photopanel.boxes.implementations.structure.logic.*;
import photopanel.boxes.implementations.structure.*;
import photopanel.boxes.implementations.input.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.*;
import photopanel.boxes.implementations.imageediting.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class Boxes {
    
    public static String CATEGORY_IO_AUSGABE = "I/O > Ausgabe";
    public static String CATEGORY_IO_AUSGABE_SPEICHERN = "I/O > Ausgabe > Speichern";
    public static String CATEGORY_IO_AUSGABE_ZEIGEN = "I/O > Ausgabe > Zeigen";
    public static String CATEGORY_IO_EINGABE = "I/O > Eingabe";
    public static String CATEGORY_STRUKTUR = "Struktur";
    public static String CATEGORY_STRUKTUR_UMSCHALTEN = "Struktur > Umschalten";
    public static String CATEGORY_STRUKTUR_KOPIEREN = "Struktur > Kopieren";
    public static String CATEGORY_STRUKTUR_LOGIK = "Struktur > Logik";
    public static String CATEGORY_BILDANALYSE = "Bildanalyse";
    public static String CATEGORY_BILDBEARBEITUNG = "Bildbearbeitung";
    public static String CATEGORY_BILDBEARBEITUNG_BILDGROESSE = "Bildbearbeitung > Bildgrösse";
    public static String CATEGORY_BILDBEARBEITUNG_EFFEKTE = "Bildbearbeitung > Effekte";
    public static String CATEGORY_BILDBEARBEITUNG_ZUSAMMENFÜHREN = "Bildbearbeitung > Zusammenführen";
    public static String CATEGORY_BILDBEARBEITUNG_EFFEKTE_ERWEITERT = "Bildbearbeitung > Effekte > Erweitert";
    
    private final static Class[] IMPLEMENTATION_CLASSES = new Class[] {
        SharpenBox.class,
        CompareValueArrayBox.class,
        CopyValueArrayBox.class,
        CopyImageBox.class,
        CropExpandBox.class,
        HistogramBox.class,
        ImageFromPathBox.class,
        RedruceValueArrayBox.class,
        ShowSingleValueBox.class,
        ShowValueArrayBox.class,
        ShowTrueFalseBox.class,
        SingleValueBox.class,
        ValueArrayBox.class,
        ImageFromDialog.class,
        ShowBitmapBox.class,
        ImageSwitchBox.class,
        ValueArraySwitchBox.class,
        TrueFalseSwitchBox.class,
        SingleValueSwitchBox.class,
        LogicNotBox.class,
        LogicOperatorBox.class,
        TrueFalseBox.class,
        CopySingleValueBox.class,
        CopyTrueFalseBox.class,
        SaveBitmapToPathBox.class,
        SaveBitmapViaDialogBox.class,
        SaveTrueFalseToPathBox.class,
        SaveTrueFalseViaDialogBox.class,
        SaveSingleValueToPathBox.class,
        SaveSingleValueViaDialogBox.class,
        SaveValueArrayToPathBox.class,
        SaveValueArrayViaDialogBox.class,
        ImageByColor.class,
        Convolve3x3Box.class,
        NegativeBox.class,
        TuneColorBox.class,
        ImageToArrayBox.class,
        AttributeBox.class,
        BlurBox.class,
        EdgeDetectionBox.class,
        RemoveTransparencyBox.class,
        MixBox.class,
        OverlayBox.class,
        ResizeBox.class,
    };
    
    public static Box newInstance(Class c, int x, int y) {
        try {
            return (Box) c.getDeclaredConstructor(new Class[] {Integer.TYPE, Integer.TYPE}).newInstance(x, y);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Boxes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Boxes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Boxes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Boxes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Boxes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Boxes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getNameForBox(Class c) {
            Box b = newInstance(c, Integer.MIN_VALUE, Integer.MIN_VALUE);
            return b.getName();
    }
    
    public static Box newInstance(int i, int x, int y) {
        return newInstance(IMPLEMENTATION_CLASSES[i], x, y);
    }
    
    public static int getCount() {
        return IMPLEMENTATION_CLASSES.length;
    }
    
    public static List<String> getCategorys() {
        return asSortedList(IMPLEMENTATION_CLASSES_FOR_CATEGORY.keySet());
    }
    
    public static List<Class> getBoxesForCategory(String category) {
        return IMPLEMENTATION_CLASSES_FOR_CATEGORY.get(category);
    }
    
    private static final HashMap<String, ArrayList<Class>> IMPLEMENTATION_CLASSES_FOR_CATEGORY = new HashMap<>();
    static {
        for(Class implementationClass : IMPLEMENTATION_CLASSES) {
            Box b = newInstance(implementationClass, Integer.MIN_VALUE, Integer.MIN_VALUE);
            String category = b.getCategory();
            ArrayList other = IMPLEMENTATION_CLASSES_FOR_CATEGORY.getOrDefault(category, new ArrayList<Class>());
            other.add(implementationClass);
            IMPLEMENTATION_CLASSES_FOR_CATEGORY.put(category, other); // Dies muss gemacht werden weil der Default-Fall sonst nicht in Hashmap ist
        }
    }
    
    public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
        List<T> list = new ArrayList<T>(c);
        java.util.Collections.sort(list);
        return list;
    } // http://stackoverflow.com/questions/740299/how-do-i-sort-a-set-to-a-list-in-java
} 
