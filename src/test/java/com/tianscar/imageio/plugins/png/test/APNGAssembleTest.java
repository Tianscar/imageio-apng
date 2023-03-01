package com.tianscar.imageio.plugins.png.test;

import com.tianscar.imageio.plugins.png.PNG;
import com.tianscar.imageio.plugins.png.PNGImageWriteParam;
import com.tianscar.imageio.plugins.png.PNGMetadata;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class APNGAssembleTest {

    private static final int NUM_FRAMES = 4;
    private static final File outputFile = new File("shield.apng");

    public static void main(String[] args) {
        try {
            BufferedImage[] frames = new BufferedImage[NUM_FRAMES];
            for (int i = 0; i < frames.length; i ++) {
                frames[i] = ImageIO.read(getResourceStream("shield_" + (90 * i) + ".png"));
            }

            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);

            ImageWriter writer = ImageIO.getImageWritersByFormatName("apng").next();
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionType("Deflate");
            param.setCompressionQuality(1.0f);
            if (param instanceof PNGImageWriteParam) {
                ((PNGImageWriteParam) param).setAnimContainsIDAT(false); // true if the animation contains `IDAT` chunk, false otherwise
            }

            writer.prepareWriteSequence(null);

            PNGMetadata metadata = new PNGMetadata();
            metadata.acTL_num_plays = 0; // infinite play
            metadata.acTL_num_frames = frames.length;
            metadata.fcTL_delay_num = 2;
            metadata.fcTL_delay_den = 1;
            metadata.fcTL_dispose_op = PNG.APNG_DISPOSE_OP_BACKGROUND;
            metadata.fcTL_blend_op = PNG.APNG_BLEND_OP_OVER;
            writer.writeToSequence(new IIOImage(frames[0], null, metadata), param); // write `IDAT`
            for (BufferedImage frame : frames) {
                writer.writeToSequence(new IIOImage(frame, null, metadata), param);
            }
            writer.endWriteSequence();
            writer.dispose();
            ios.flush();
            ios.close();
        }
        catch (Throwable t) {
            throw new RuntimeException("APNGAssembleTest failed", t);
        }
    }

    private static InputStream getResourceStream(String name) {
        return APNGAssembleTest.class.getClassLoader().getResourceAsStream(name);
    }

}
