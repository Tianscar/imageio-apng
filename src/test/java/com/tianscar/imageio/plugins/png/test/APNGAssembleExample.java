package com.tianscar.imageio.plugins.png.test;

import com.tianscar.imageio.plugins.png.PNGImageWriteParam;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class APNGAssembleExample {

    private static final String nativeMetadataFormatName = "javax_imageio_png_1.0"; // see PNGMetadata.java

    private static final int NUM_FRAMES = 4;
    private static final File outputFile = new File("shield.apng");

    public static void main(String[] args) {
        try {
            BufferedImage[] frames = new BufferedImage[NUM_FRAMES];
            for (int i = 0; i < frames.length; i ++) {
                frames[i] = ImageIO.read(getResourceStream("shield_" + (90 * i) + ".png"));
            }

            ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile);

            ImageWriter writer = ImageIO.getImageWritersByFormatName("apng").next(); // get library's apng writer
            // writer = ImageIO.getImageWritersByMIMEType("image/apng").next();      // by mime type
            // writer = ImageIO.getImageWritersBySuffix("apng").next();              // by suffix
            writer.setOutput(ios); // set the output stream

            ImageWriteParam param = writer.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);    // enable compression control
            param.setCompressionType("Deflate");                        // only `Deflate` supported
            param.setCompressionQuality(1.0f);                          // 1.0 - Best quality
            if (param instanceof PNGImageWriteParam) {
                ((PNGImageWriteParam) param).setAnimContainsIDAT(false);// true if the animation contains `IDAT` chunk, false otherwise, default true
            }

            writer.prepareWriteSequence(null);

            // more metadata information see https://wiki.mozilla.org/APNG_Specification and http://www.w3.org/TR/PNG/
            // You can also create PNGMetadata directly and use the constants in PNG.java!
            IIOMetadata metadata = writer.getDefaultImageMetadata(
                    ImageTypeSpecifier.createFromBufferedImageType(frames[0].getType()), // cannot be null
                    param // can be null
            ); // since PNG don't have a stream metadata, the first image metadata send to the writer will be applied to the whole image
            IIOMetadataNode treeToMerge = new IIOMetadataNode(nativeMetadataFormatName);
            // create the metadata tree to merge, the native tree, as standard tree not fully supported
            IIOMetadataNode acTL = new IIOMetadataNode("acTL");
            acTL.setAttribute("num_plays", "0");                            // infinite play
            acTL.setAttribute("num_frames", Integer.toString(frames.length));     // must be set before write frames
            treeToMerge.appendChild(acTL);
            metadata.mergeTree(nativeMetadataFormatName, treeToMerge);                  // merge tree

            writer.writeToSequence(new IIOImage(frames[0], null, metadata), param);
            // since the animation do not contain `IDAT` chunk, write `IDAT` separately

            for (BufferedImage frame : frames) {
                metadata = writer.getDefaultImageMetadata(
                        ImageTypeSpecifier.createFromBufferedImageType(frame.getType()), // cannot be null
                        param // can be null
                );
                treeToMerge = (IIOMetadataNode) metadata.getAsTree(nativeMetadataFormatName);
                IIOMetadataNode fcTL = new IIOMetadataNode("fcTL");
                fcTL.setAttribute("sequence_number", "0"); // the value doesn't matter, will automatically set by the writer later
                fcTL.setAttribute("width", Integer.toString(frame.getWidth()));
                fcTL.setAttribute("height", Integer.toString(frame.getHeight()));
                fcTL.setAttribute("x_offset", "0");        // the x offset to the origin
                fcTL.setAttribute("y_offset", "0");        // the y offset to the origin
                // the delay time is in seconds
                fcTL.setAttribute("delay_num", "2");       // the numerator of the delay time
                fcTL.setAttribute("delay_den", "1");       // the denominator of the delay time
                // 2s / 1 = 2s
                fcTL.setAttribute("dispose_op", "background"); // see PNGMetadata#fcTL_disposalOperatorNames
                fcTL.setAttribute("blend_op", "source");       // see PNGMetadata#fcTL_blendOperatorNames
                treeToMerge.appendChild(fcTL);
                metadata.mergeTree(nativeMetadataFormatName, treeToMerge);
                writer.writeToSequence(new IIOImage(frame, null, metadata), param);
            }
            writer.endWriteSequence(); // end write sequence
            writer.dispose();          // release the resources
            ios.flush();
            ios.close();
        }
        catch (Throwable t) {
            throw new RuntimeException("Assemble failed", t);
        }
    }

    private static InputStream getResourceStream(String name) {
        return APNGAssembleExample.class.getClassLoader().getResourceAsStream(name);
    }

}
