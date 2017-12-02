package org.struy.util;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

public class Tools {
    /**
     * get a UUID
     *
     * @return UUID
     */
    public static String uid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Add the end to the folder path
     * and if folder path Non-existent then mkdirs
     *
     * @param path folder path
     * @return
     */
    public static String folderHelper(String path) {

        path = !path.endsWith(File.separator) ?
                path + File.separator :
                path;

        File file = new File(path);
        if (!new File(path).exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * Create date level folders
     * @param path folder path
     * @return
     */
    public static String dateFolders(String path) {

        Calendar date = Calendar.getInstance();

        path += date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH) + 1) + File.separator
                /*+ date.get(Calendar.DAY_OF_MONTH)*/;

        return folderHelper(path);
    }

}
