package com.tianscar.imageio.plugins.png;

public final class PNG {

    private PNG() {
        throw new UnsupportedOperationException();
    }

    public static final int PNG_COLOR_GRAY = 0;
    public static final int PNG_COLOR_RGB = 2;
    public static final int PNG_COLOR_PALETTE = 3;
    public static final int PNG_COLOR_GRAY_ALPHA = 4;
    public static final int PNG_COLOR_RGB_ALPHA = 6;

    public static final int PNG_FILTER_NONE = 0;
    public static final int PNG_FILTER_SUB = 1;
    public static final int PNG_FILTER_UP = 2;
    public static final int PNG_FILTER_AVERAGE = 3;
    public static final int PNG_FILTER_PAETH = 4;

    public static final int APNG_DISPOSE_OP_NONE = 0;
    public static final int APNG_DISPOSE_OP_BACKGROUND = 1;
    public static final int APNG_DISPOSE_OP_PREVIOUS = 2;

    public static final int APNG_BLEND_OP_SOURCE = 0;
    public static final int APNG_BLEND_OP_OVER = 1;

}
