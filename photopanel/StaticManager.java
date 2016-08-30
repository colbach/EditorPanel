package photopanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import photopanel.boxes.Box;
import photopanel.boxes.Let;
import photopanel.boxes.listener.DragListening;
import photopanel.boxes.listener.KeyListening;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class StaticManager {

    public static String errorMessage = "";

    public static boolean canRun() {
        errorMessage = "";
        for (Box box : boxes) {
            if (!box.allInletsAreWired()) {
                errorMessage = "Inlets von " + box.getName() + " müssen verbunden sein";
                return false;
            }
        }

        return true;
    }

    private static boolean allFinished() {
        for (Box box : boxes) {
            if (!box.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public static void run() {
        System.out.println("STARTED...");

        try {
            if (!canRun()) {
                throw new RuntimeException("Kann nicht ausgeführt werden!");
            }

            for (Box box : boxes) {
                box.setFinished(false);
            }

            int i = 0;
            while (!allFinished()) {

                System.out.println("(" + ++i + ")");

                boolean nextBoxFound = false;

                forEveryBox:
                for (Box box : boxes) {
                    if (!box.isFinished() && box.canTakeAll()) {
                        nextBoxFound = true;
                        System.out.println("run: [" + box.getName() + "] ...");
                        box.run();
                        System.out.println("...finished");
                        box.setFinished(true);
                        break forEveryBox;
                    }
                }

                if (!nextBoxFound) {
                    throw new RuntimeException("Deadlock!");
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Fehler: " + exception.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        System.out.println("...FINISHED");
    }

    private static File actualFile = null;

    public static void loadFromFile(File file) {
        try (ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file))) {
            boxes = (ArrayList<Box>) oin.readObject();

            // Aufraumen
            activeBox = null;
            keyListening = null;
            dragListening = null;
            wireingLet = null;
            wireingLetX = 0;
            wireingLetY = 0;

            actualFile = file;

            System.out.println("Datei geladen: " + file.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Datei kann nicht geladen werden! (" + e.getMessage() + ")",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static synchronized void saveToFile(File file) {
        try (ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file))) {
            oout.writeObject(boxes);

            actualFile = file;

            System.out.println("gespeichert unter: " + file.getAbsolutePath());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Datei kann nicht gespeichert werden! (" + e.getMessage() + ")",
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static synchronized boolean save() {
        if (actualFile != null) {
            saveToFile(actualFile);
            return true;
        } else {
            return false;
        }
    }

    private static ArrayList<Box> boxes = new ArrayList<>();

    private static Box activeBox = null;

    private static KeyListening keyListening = null;
    private static DragListening dragListening = null;
    private static Let wireingLet = null;
    private static int wireingLetX;
    private static int wireingLetY;

    public static boolean PRINT_EVENTS = false;

    public static void registerKeyListeningParameter(KeyListening klp) {
        keyListening = klp;
    }

    public static void registerDragListeningParameter(DragListening dlp) {
        dragListening = dlp;
    }

    public static void registerWireingLet(Let wl, int x, int y) {
        wireingLet = wl;
        wireingLetX = x;
        wireingLetY = y;
        dragListening = null; // Box soll nicht verschiebbar sein wenn 
    }

    public static void registerActiveBox(Box activeBox) {
        StaticManager.activeBox = activeBox;
    }

    public static Box getActiveBox() {
        return activeBox;
    }

    public static void setActiveBox(Box activeBox) {
        StaticManager.activeBox = activeBox;
    }

    public static KeyListening getKeyListening() {
        return keyListening;
    }

    public static void setKeyListening(KeyListening keyListening) {
        StaticManager.keyListening = keyListening;
    }

    public static DragListening getDragListening() {
        return dragListening;
    }

    public static void setDragListening(DragListening dragListening) {
        StaticManager.dragListening = dragListening;
    }

    public static Let getWireingLet() {
        return wireingLet;
    }

    public static int getWireingLetX() {
        return wireingLetX;
    }

    public static int getWireingLetY() {
        return wireingLetY;
    }

    public static void setWireingLet(Let wireingLet) {
        StaticManager.wireingLet = wireingLet;
    }

    public synchronized static ArrayList<Box> getBoxes() {
        return boxes;
    }

    public synchronized static void addBox(Box box) {
        boxes.add(box);
    }

    public synchronized static boolean removeBox(Box box) {
        if (box != null) {
            box.destroy();
        }
        if (box == activeBox) {
            activeBox = null;
        }
        return boxes.remove(box);
    }

    public synchronized static void reset() {
        boxes = new ArrayList<>();
        activeBox = null;
        keyListening = null;
        dragListening = null;
        wireingLet = null;
        wireingLetX = 0;
        wireingLetY = 0;
    }

}
