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
import org.struy.entity.Accessory;
import org.struy.repository.AccessoryRepository;
import org.struy.util.Tools;
import org.struy.util.DownloadUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Api(value = "Files Upload")
@RestController
@RequestMapping("/api/accessory")
public class AccessoryController {
    @Autowired
    private CustomProperties customProperties;
    @Autowired
    private AccessoryRepository accessoryRepository;

    @ApiOperation(value = "已上传的文件列表")
    @GetMapping()
    public ResponseEntity<?> all() {

        Iterable<Accessory> iterable = accessoryRepository.findAll();
        List<Accessory> list1 = new ArrayList();
        iterable.forEach(ac -> list1.add(ac));
        return ResponseEntity.ok(list1);

    }

    @ApiOperation(value = "清空已上传的文件记录及所有文件和文件夹")
    @DeleteMapping()
    public ResponseEntity<?> deleteAll() {
        accessoryRepository.deleteAll();
        if (Tools.foldersFileDelete(customProperties.getUploadPath())) {
            return ResponseEntity.ok().body("cleared");
        }
        return ResponseEntity.ok().body("file cleared failed");
    }

    @ApiOperation(value = "删除单个文件")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Accessory accessory = accessoryRepository.findOne(id);
        if (null != accessory) {
            accessoryRepository.delete(id);
            Tools.foldersFileDelete(customProperties.getUploadPath() + accessory.getPath());
            return ResponseEntity.ok().body("delete single file successful");
        }
        return ResponseEntity.ok().body("single file delete failed");
    }

    @ApiOperation(value = "文件上传")
    @ApiImplicitParam(paramType = "path",
            name = "type",
            value = "文件的类型,用于在服务器分目录存储",
            required = true,
            dataType = "String")
    @PostMapping(value = "/upload/{type}")
    public ResponseEntity<?> upload(@PathVariable("type") String type,
                                    @RequestParam("file") MultipartFile file) {
        String fileName;
        String fileSavePath;
        String viewUrl;
        if (!file.isEmpty()) {
            try {
                Accessory accessory = new Accessory();
                accessory.setId(Tools.uid());
                fileName = file.getOriginalFilename();

                String suffix = fileName.lastIndexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")) : "";
                String filePath = Tools.folderHelper(type, false) + Tools.dateFolders();
                String newName = accessory.getId() + suffix;
                fileSavePath = Tools.folderHelper(customProperties.getUploadPath() + filePath, true) + newName;

                file.transferTo(new File(fileSavePath));
                accessory.setContentType(file.getContentType());
                accessory.setPath(filePath+newName);
                accessory.setSize(file.getSize() + "");
                accessory.setType(type);
                viewUrl = "/api/accessory/view/" + accessory.getId();
                accessory.setUrl(viewUrl);
                accessoryRepository.save(accessory);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Upload Error");
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(viewUrl);
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Upload Error , file isEmpty");

    }


    @ApiOperation(value = "指定类型的所有文件")
    @GetMapping("/{type}")
    public ResponseEntity<?> findByType(@PathVariable String type) {
        List<Accessory> list1 = accessoryRepository.findByType(type);
        return ResponseEntity.ok(list1);

    }

    @ApiOperation(value = "预览文件", notes = "预览包括图片,pdf,及文本")
    @GetMapping("/view/{id}")
    public ResponseEntity<?> view(@PathVariable String id, HttpServletResponse response) {
        Accessory accessory = accessoryRepository.findOne(id);
        if (null != accessory && null != accessory.getPath()) {

            String filePath = customProperties.getUploadPath() + accessory.getPath();
            try {
                DownloadUtil.downLoad(filePath, response, true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("view Error");
    }


    @ApiOperation(value = "下载文件")
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id, HttpServletResponse response) {
        Accessory accessory = accessoryRepository.findOne(id);
        if (null != accessory && null != accessory.getPath()) {

            String filePath = customProperties.getUploadPath() + accessory.getPath();
            try {
                DownloadUtil.downLoad(filePath, response, false);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("download Error");
    }


}
