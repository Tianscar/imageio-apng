package com.tianscar.imageio.plugins.png.test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.InputStream;

public class APNGDisassembleExample {

    private static final String nativeMetadataFormatName = "javax_imageio_png_1.0"; // see PNGMetadata.java

    public static void main(String[] args) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(getResourceStream("shield.apng"));

            ImageReader reader = ImageIO.getImageReadersByFormatName("apng").next(); // get library's apng reader
            //reader = ImageIO.getImageReadersByMIMEType("image/apng").next();       // by mime type
            //reader = ImageIO.getImageReadersBySuffix("apng").next();               // by suffix
            reader.setInput(iis); // set the input stream

            // more metadata information see https://wiki.mozilla.org/APNG_Specification and http://www.w3.org/TR/PNG/
            // You can also cast PNGMetadata directly!
            IIOMetadata metadata;
            for (int i = 0; i < reader.getNumImages(true); i ++) {
                metadata = reader.getImageMetadata(i);
                IIOMetadataNode child = (IIOMetadataNode) metadata.getAsTree(nativeMetadataFormatName).getFirstChild();
                while (child != null) {
                    if ("fcTL".equals(child.getNodeName())) {
                        System.out.println("frame index: " + i);
                        System.out.println("width: " + child.getAttribute("width"));
                        System.out.println("height: " + child.getAttribute("height"));
                        System.out.println("x offset: " + child.getAttribute("x_offset"));
                        System.out.println("y offset: " + child.getAttribute("y_offset"));
                        System.out.println("disposal operator: " + child.getAttribute("dispose_op"));
                        System.out.println("blend operator: " + child.getAttribute("blend_op"));
                        System.out.println("delay time (seconds): " +
                                (Integer.parseInt(child.getAttribute("delay_num")) /
                                Integer.parseInt(child.getAttribute("delay_den"))));
                        break;
                    }
                    child = (IIOMetadataNode) child.getNextSibling();
                }
                ImageIO.write(reader.read(i), "png", new File("shield_" + (i * 90) + ".png"));
            }
            reader.dispose();
        }
        catch (Throwable t) {
            throw new RuntimeException("Disassemble failed", t);
        }
    }

    private static InputStream getResourceStream(String name) {
        return APNGAssembleExample.class.getClassLoader().getResourceAsStream(name);
    }

}
