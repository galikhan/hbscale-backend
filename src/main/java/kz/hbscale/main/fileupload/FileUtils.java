package kz.hbscale.main.fileupload;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

public class FileUtils {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getExtension(String filename) {

        int indexOfDot = filename.lastIndexOf(".");
        String ext = filename.substring(indexOfDot + 1);
        System.out.println("et" + ext);

        return ext;
    }


    public static boolean createImageWithCustomHeight(File file, int height, String extension, String filename) throws IOException {

        BufferedImage image;
//        try{
            image = ImageIO.read(file);
//        }catch (IIOException e) {
//            image = ImageIO.;
//        }
        int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB :image.getType();

        double ratio = (double) image.getWidth() / image.getHeight();
        int width  = (int) Math.ceil( height * ratio );

        BufferedImage thumbnailImage = new BufferedImage(width, height, type);
        Graphics2D graphics = thumbnailImage.createGraphics();
        graphics.drawImage(image, 0, 0,width, height, null);
        graphics.dispose();

        File thumbnailFile = new File(filename);
        return ImageIO.write(thumbnailImage, extension, thumbnailFile);
    }
}
