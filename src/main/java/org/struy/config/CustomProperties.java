package org.struy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.struy.util.Tools;

@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    @Setter
    private String uploadPath;
    @Setter
    @Getter
    private String swaggerBase;

    @Getter
    @Setter
    private String accessKey;
    @Getter
    @Setter
    private String secretKey;

    /**
     * If there is no configuration,
     * it will be uploaded to the user's temporary directory
     *
     * @return FileUploadPath
     */
    public String getUploadPath() {

        if (null == uploadPath) {
            return getTempPath();
        }
        return Tools.folderHelper(uploadPath, true);
    }

    /**
     * get user's tmpdir
     *
     * @return TempPath
     */
    private String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }
}
