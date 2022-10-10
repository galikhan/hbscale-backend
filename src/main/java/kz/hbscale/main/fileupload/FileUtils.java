package kz.hbscale.main.fileupload;

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
}
