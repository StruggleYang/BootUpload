package org.struy.main.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@ConfigurationProperties(prefix="myProperties") //接收application.yml中的myProperties下面的属性
public class MyProperties {
    private  String fileUploadPath ;

    public String getFileUploadPath() {
        if (null==fileUploadPath) {
            return getTempPath();
        }
        //判断有没有结尾符,没有得加上
        if (!fileUploadPath.endsWith(File.separator)) {
            fileUploadPath = fileUploadPath + File.separator;
        }
        //判断目录存不存在,不存在得加上
        File file = new File(fileUploadPath);
        if(!file.exists()){
            file.mkdirs();
        }
        return fileUploadPath;
    }

    public void setFileUploadPath(String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
    }

    public static String getTempPath(){
        return System.getProperty("java.io.tmpdir");
    }
}
