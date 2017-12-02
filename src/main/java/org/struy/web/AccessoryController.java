package org.struy.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.struy.config.CustomProperties;
import org.struy.util.Tools;

import java.io.File;
import java.util.*;

@Api(value = "Files Upload")
@RestController
@RequestMapping("/accessory")
public class AccessoryController {
    @Autowired
    private CustomProperties customProperties;


    @ApiOperation(value = "文件上传")
    @ApiImplicitParam(paramType = "path",
            name = "type",
            value = "文件的类型,用于在服务器分目录存储",
            required = true,
            dataType = "String")
    @PostMapping(value = "/upload/{type}")
    public ResponseEntity<?> upload(@PathVariable("type") String type,
                                    @RequestParam("file") MultipartFile multipartFile) {
        String fileName;
        String fileSavePath = null;
        if(!multipartFile.isEmpty()){
            try {

                fileName = multipartFile.getOriginalFilename();

                fileSavePath = Tools.dateFolders(Tools.folderHelper(customProperties.getFileUploadPath() + type))+Tools.uid() + fileName;

                multipartFile.transferTo(new File(fileSavePath));
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Upload Error");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("Upload Successful by:"+fileSavePath);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Upload Error");

    }


    @ApiOperation(value = "已上传的全部列表")
    @GetMapping("/all")
    public ResponseEntity<?> all() {
        String path = customProperties.getFileUploadPath();
        File fas[] = new File(path).listFiles();
        List<Map<String, Object>> files = new ArrayList<>();
        for (int i = 0; i < fas.length; i++) {
            File fs = fas[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]-skip");
            } else {
                String name = fs.getName();
                if (name.length() > 33) {
                    name = name.substring(33);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("path", name);
                files.add(map);
            }
        }
        if (files.size() <= 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(files);
    }

    @ApiOperation(value = "预览文件")
    @GetMapping("/view")
    public ResponseEntity<?> view() {
        //TODO 预览文件
        return ResponseEntity.ok(null);
    }
}
