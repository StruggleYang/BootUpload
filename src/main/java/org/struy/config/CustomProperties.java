package org.struy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.struy.util.Tools;

@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {
    private String fileUploadPath;

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    /**
     * If there is no configuration,
     * it will be uploaded to the user's temporary directory
     *
     * @return FileUploadPath
     */
    public String getFileUploadPath() {

        if (null == fileUploadPath) {
            return getTempPath();
        }
        return Tools.folderHelper(fileUploadPath,true);
    }

    /**
     * get user's tmpdir
     * @return TempPath
     */
    private String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }
}
