package org.struy.web;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.DefaultPutRet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.http.Response;
import org.springframework.web.multipart.MultipartFile;
import org.struy.entity.Accessory;
import org.struy.repository.AccessoryRepository;
import org.struy.util.Tools;


@Api(value = "cloud Upload")
@RestController
@RequestMapping("/api/qiniu")
public class QiuniuController {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Value("${custom.access-key}")
    String accessKey;
    @Value("${custom.secret-key}")
    String secretKey;
    @Value("${custom.bucket}")
    String bucket;
    @Value("${custom.back-url}")
    String callbackUrl;

    @ApiOperation("七牛云上传")
    @PostMapping("/upload/{type}")
    public ResponseEntity<?> NiuUpload(@PathVariable("type") String type,
            @RequestParam("file") MultipartFile file){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        String fileName;
        if (!file.isEmpty()) {
            try {
                // 获取流
                byte[] uploadBytes = file.getBytes();
                // 生成上传凭证，然后准备上传
                Auth auth = Auth.create(accessKey, secretKey);
                String upToken = auth.uploadToken(bucket);
                try {

                    Accessory accessory = new Accessory();
                    accessory.setId(Tools.uid());
                    fileName = file.getOriginalFilename();
                    String suffix = Tools.suffix(fileName);
                    String filePath = Tools.dateFolders(true);
                    String key = accessory.getId() + suffix;

                    // 执行上传
                    Response response = uploadManager.put(uploadBytes, filePath+key, upToken);
                    //  解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                    // 业务服务器的存储操作
                    accessory.setContentType(file.getContentType());
                    accessory.setPath(filePath+key);
                    accessory.setSize(file.getSize() + "");
                    accessory.setType(type);
                    accessory.setUrl(callbackUrl+putRet.key);
                    accessoryRepository.save(accessory);
                    return ResponseEntity.ok(accessory.getUrl());
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        return ResponseEntity.ok("Upload Error ");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.ok("Upload Error ");
            }
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Upload Error , file isEmpty");
    }
}
