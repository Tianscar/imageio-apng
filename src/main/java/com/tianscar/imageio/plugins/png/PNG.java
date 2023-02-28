package com.tianscar.imageio.plugins.png;

public final class PNG {

    private PNG() {
        throw new UnsupportedOperationException();
    }

    public static final int APNG_DISPOSE_OP_NONE = 0;
    public static final int APNG_DISPOSE_OP_BACKGROUND = 1;
    public static final int APNG_DISPOSE_OP_PREVIOUS = 2;

    public static final int APNG_BLEND_OP_SOURCE = 0;
    public static final int APNG_BLEND_OP_OVER = 1;

}
