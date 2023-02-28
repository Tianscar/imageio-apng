package com.tianscar.imageio.plugins.png;

import javax.imageio.ImageWriteParam;
import java.util.Locale;

public final class PNGImageWriteParam extends ImageWriteParam {

    /**
     * Default quality level = 0.5 ie medium compression
     */
    private static final float DEFAULT_QUALITY = 0.5f;

    private static final String[] compressionNames = {"Deflate"};
    private static final float[] qualityVals = {0.00F, 0.30F, 0.75F, 1.00F};
    private static final String[] qualityDescs = {
            "High compression",   // 0.00 -> 0.30
            "Medium compression", // 0.30 -> 0.75
            "Low compression"     // 0.75 -> 1.00
    };

    public boolean isAnimContainsIDAT() {
        return animContainsIDAT;
    }

    public void setAnimContainsIDAT(boolean animContainsIDAT) {
        this.animContainsIDAT = animContainsIDAT;
    }

    private boolean animContainsIDAT;

    PNGImageWriteParam(Locale locale) {
        super();
        this.canWriteProgressive = true;
        this.locale = locale;
        this.canWriteCompressed = true;
        this.compressionTypes = compressionNames;
        this.compressionType = compressionTypes[0];
        this.compressionMode = MODE_DEFAULT;
        this.compressionQuality = DEFAULT_QUALITY;
        animContainsIDAT = true;
    }

    /**
     * Removes any previous compression quality setting.
     *
     * <p> The default implementation resets the compression quality
     * to <code>0.5F</code>.
     *
     * @throws IllegalStateException if the compression mode is not
     *                               <code>MODE_EXPLICIT</code>.
     */
    @Override
    public void unsetCompression() {
        super.unsetCompression();
        this.compressionType = compressionTypes[0];
        this.compressionQuality = DEFAULT_QUALITY;
    }

    /**
     * Returns <code>true</code> since the PNG plug-in only supports
     * lossless compression.
     *
     * @return <code>true</code>.
     */
    @Override
    public boolean isCompressionLossless() {
        return true;
    }

    @Override
    public String[] getCompressionQualityDescriptions() {
        super.getCompressionQualityDescriptions();
        return qualityDescs.clone();
    }

    @Override
    public float[] getCompressionQualityValues() {
        super.getCompressionQualityValues();
        return qualityVals.clone();
    }
}
