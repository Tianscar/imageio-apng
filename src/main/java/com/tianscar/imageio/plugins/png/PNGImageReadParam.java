package com.tianscar.imageio.plugins.png;

import javax.imageio.ImageReadParam;

public final class PNGImageReadParam extends ImageReadParam {

    public boolean isForceReadIDAT() {
        return forceReadIDAT;
    }

    public void setForceReadIDAT(boolean forceReadIDAT) {
        this.forceReadIDAT = forceReadIDAT;
    }

    private boolean forceReadIDAT = false;

}
