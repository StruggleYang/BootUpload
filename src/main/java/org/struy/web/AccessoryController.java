package org.struy.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.struy.config.MyProperties;
import org.struy.util.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accessory")
public class AccessoryController {
    @Autowired
    private MyProperties myProperties;


    @ApiOperation(value = "文件上传")
    @PostMapping(value = "/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) {
        String fileName;
        try {
            fileName = multipartFile.getOriginalFilename();
            String fileSavePath = myProperties.getFileUploadPath();
          multipartFile.transferTo(new File(fileSavePath + Tools.uid()+ fileName));
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return ResponseEntity.ok(fileName);
    }

    @ApiOperation(value = "已上传的全部列表")
    @GetMapping("all")
    public ResponseEntity<?> all(){
        String path = myProperties.getFileUploadPath();
        File fas[] = new File(path).listFiles();
        List<Map<String,Object>> files = new ArrayList<>();
        for (int i = 0; i < fas.length; i++) {
            File fs = fas[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]-skip");
            } else {
                String name = fs.getName();
                if (name.length()>33){
                    name = name.substring(33);
                }
                Map<String,Object> map = new HashMap<>();
                map.put("path",name);
                files.add(map);
            }
        }
        if (files.size()<=0){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(files);
    }

    @ApiOperation(value = "预览文件")
    @GetMapping("view")
    public ResponseEntity<?> view(){
        //TODO 预览文件
        return ResponseEntity.ok(null);
    }
}
