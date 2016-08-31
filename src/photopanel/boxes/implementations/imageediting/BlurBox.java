package photopanel.boxes.implementations.imageediting;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.Serializable;
import photopanel.boxes.datatypes.*;
import photopanel.boxes.parameters.*;
import photopanel.boxes.*;

/**
 * EDITORPANEL - Tool- und Pluginprogrammierung 2016 - Hochschule Trier
 * @author Christian Colbach
 */
public class BlurBox extends Box implements Serializable {

    private static final long serialVersionUID = 1L;

    SliderParameter sp;
    ToogleParameter disableParameter;

    public BlurBox(int x, int y) {
        if (x == Integer.MIN_VALUE || y == Integer.MAX_VALUE) { // Spezialfall falls Objekt nicht komplett initialisiert werden soll
            return;
        }

        inlets.add(new Inlet(this, Datatype.BITMAP));

        outlets.add(new Outlet(this, Datatype.BITMAP));

        setX(x);
        setY(y);

        sp = addSliderParameter(3, 1, 100, "Stärke");
        sp.onlyAcceptWholeValues();
        disableParameter = addToogleParameter(false, "Deaktivieren");

    }

    public static String NAME = "Blur";
    public static String TITLE = "Blur";
    public static String CATEGORY = Boxes.CATEGORY_BILDBEARBEITUNG_EFFEKTE;

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
    public Datatype[] run(Datatype[] ds) {
        if (disableParameter.isSelected()) {
            return ds;
        }

        Bitmap b = (Bitmap) ds[0];
        BufferedImage sourceImage = b.getImage();
        BufferedImage dstImage = null;
        float bf = sp.getFloat();
        float af = (1f - bf) / 4f;
        int s = sp.getInt();
        float[] blurKernel = generateGaussianKernel(s);
        Kernel kernel = new Kernel(s, s, blurKernel);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        dstImage = op.filter(sourceImage, null);
        b.setImage(dstImage);
        return new Datatype[]{b};
    }

    /**
     * Builds the Gaussian kernel that will be used to convolve the image.
     * Source: http://www.math.colostate.edu/~benoit/software.html Directlink:
     * http://www.math.colostate.edu/~benoit/Java/microscopy/SmootherFilter.java
     *
     * DISCLAIMER OF WARRANTY This source code is provided "as is" and without
     * any express or implied warranties whatsoever. The user must assume the
     * entire risk of using the source code.
     *
     * @return the convolution kernel
     */
    private float[] generateGaussianKernel(int size) {
        float[] data;
        double gVal;
        float total;

        // Gaussian kernel generieren
        data = new float[size * size];
        total = 0.0f;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                gVal = (1 / (2 * Math.PI)) + Math.exp(-Math.pow(x - 3, 2) - Math.pow(y - 3, 2));
                data[(y * size) + x] = (float) gVal;
                total += data[(y * size) + x];
            }
        }

        // Normalisieren
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                data[(y * size) + x] /= total;
            }
        }

        return data;

    }

}
