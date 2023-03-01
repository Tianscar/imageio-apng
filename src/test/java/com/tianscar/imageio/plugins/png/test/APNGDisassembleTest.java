package com.tianscar.imageio.plugins.png.test;

import com.tianscar.imageio.plugins.png.PNGMetadata;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.InputStream;

public class APNGDisassembleTest {

    public static void main(String[] args) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(getResourceStream("shield.apng"));

            ImageReader reader = ImageIO.getImageReadersByFormatName("apng").next();
            reader.setInput(iis);

            IIOMetadata md;
            for (int i = 0; i < reader.getNumImages(true); i ++) {
                md = reader.getImageMetadata(i);
                if (md instanceof PNGMetadata) {
                    PNGMetadata metadata = (PNGMetadata) md;
                    System.out.println("frame index: " + i);
                    System.out.println("width: " + metadata.fcTL_width);
                    System.out.println("height: " + metadata.fcTL_height);
                    System.out.println("x offset: " + metadata.fcTL_x_offset);
                    System.out.println("y offset: " + metadata.fcTL_y_offset);
                    System.out.println("disposal operator: " + metadata.fcTL_dispose_op);
                    System.out.println("blend operator: " + metadata.fcTL_blend_op);
                    System.out.println("delay time (seconds): " + metadata.fcTL_delay_num / metadata.fcTL_delay_den);
                }
                ImageIO.write(reader.read(i), "png", new File("shield_" + (i * 90) + ".png"));
            }
            reader.dispose();
        }
        catch (Throwable t) {
            throw new RuntimeException("APNGDisassembleTest failed", t);
        }
    }

    private static InputStream getResourceStream(String name) {
        return APNGAssembleTest.class.getClassLoader().getResourceAsStream(name);
    }

}
