package org.struy.main.web;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.struy.main.util.MyProperties;

import javax.annotation.Resource;
import java.io.File;

@RestController
public class UploadController {
    @Resource
    private MyProperties myProperties;

    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile multipartFile) {
        String fileName;
        try {
            fileName = multipartFile.getOriginalFilename();
            String fileSavePath = myProperties.getFileUploadPath();
          multipartFile.transferTo(new File(fileSavePath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }
}
