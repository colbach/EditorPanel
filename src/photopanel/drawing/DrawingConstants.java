package photopanel.drawing;

import java.awt.Color;
import java.awt.Font;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class DrawingConstants {
    
    // === Strings ===
    public static String STRING_INPUT = "Input";
    public static String STRING_OUTPUT = "Output";
    
    // === Groessen ===
    public static int MINIMUM_BOX_WIDTH = 120;
    public static int BOX_HEADER_HEIGHT = 25;
    public static int BOX_FOOTER_HEIGHT = 50;
    public static int BOX_FOOTER_PER_LET_HEIGHT = 20;
    public static int INPUT_TEXT_BOX_HEIGHT = 20;
    public static int DEFAULT_INPUT_TEXT_BOX_WIDTH = 70;
    public static int SELECT_BOX_EDGE = 14;
    public static int OPTION_SELECT_EDGE = 14;
    
    public static int MULTIPLE_OPTIONES_PARAMETER_HEIGHT_PER_PARAMETER = 25;
    public static int SLIDER_PARAMETER_HEIGHT = 60;
    public static int TOOGLE_PARAMETER_HEIGHT_PER_PARAMETER = 30;
    public static int VALUE_PARAMETER_HEIGHT_PER_PARAMETER = 30;
    public static int NOTE_PARAMETER_HEIGHT_PER_LINE = 18;
    
    
    public static int ARROW_SPOT = 20;
    
    // === Schriften ===
    public static Font INPUT_TEXT_FONT = new Font("Arial", Font.PLAIN, 15);
    public static Font SHOW_VALUE_ARRAY_FONT = new Font("Arial", Font.PLAIN, 15);
    public static Font TITLE_FONT = new Font("Arial", Font.ITALIC + Font.BOLD, 16);
    public static Font PARAMETER_TITLE_FONT = new Font("Arial", Font.ITALIC, 13);
    public static Font PARAMETER_FONT = new Font("Arial", Font.PLAIN, 12);
    public static Font LET_TITLE_FONT = new Font("Arial", Font.ITALIC, 12);
    public static Font LET_FONT = new Font("Arial", Font.PLAIN, 12);
    public static Font SHOW_SINGLEVALUE_VALUE_FONT = new Font("Arial", Font.PLAIN, 24);
    public static Font SHOW_SINGLEVALUE_DESCRIPTION_FONT = new Font("Arial", Font.ITALIC, 24);
    
    // === Farben ===
    public static Color BACKGROUND_COLOR = new Color(230, 230, 230);
    public static Color WIRE_COLOR = Color.BLACK;
    public static Color TITLE_COLOR = Color.DARK_GRAY;
    public static Color PARAMETER_COLOR = Color.BLACK;
    public static Color OPTION_SELECT_COLOR = Color.BLACK;
    public static Color BOX_COLOR = new Color(255, 255, 255, 240);
    public static Color BOX_ACTIVE_COLOR = new Color(255, 255, 255, 255);
    public static Color BOX_BORDER_COLOR = Color.BLACK;
    public static Color BOX_TITLE_DOTT_COLOR = Color.lightGray;
    public static Color BOX_TITLE_LINE_COLOR = BOX_BORDER_COLOR;
    public static Color BOX_SECTION_LINE_COLOR = new Color(0, 0, 0, 40);
    public static Color BOX_TITLE_BACKGROUND_COLOR = new Color(0, 0, 0, 20);
    public static Color BOX_SHADOW_COLOR = new Color(0, 0, 0, 100);
    public static Color WIRE_PREVIEW_COLOR = new Color(0, 0, 0, 60);
    
}
