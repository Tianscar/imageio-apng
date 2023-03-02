package com.tianscar.imageio.plugins.png;

public interface PNG {

    int PNG_COLOR_GRAY = 0;
    int PNG_COLOR_RGB = 2;
    int PNG_COLOR_PALETTE = 3;
    int PNG_COLOR_GRAY_ALPHA = 4;
    int PNG_COLOR_RGB_ALPHA = 6;

    int PNG_FILTER_NONE = 0;
    int PNG_FILTER_SUB = 1;
    int PNG_FILTER_UP = 2;
    int PNG_FILTER_AVERAGE = 3;
    int PNG_FILTER_PAETH = 4;

    int APNG_DISPOSE_OP_NONE = 0;
    int APNG_DISPOSE_OP_BACKGROUND = 1;
    int APNG_DISPOSE_OP_PREVIOUS = 2;

    int APNG_BLEND_OP_SOURCE = 0;
    int APNG_BLEND_OP_OVER = 1;

}
