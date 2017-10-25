package org.struy.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.struy.main.util.MyProperties;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainControoller {
    @Resource
    private MyProperties myProperties;

    @RequestMapping()
    public String root(Model model) {
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
                map.put("name",name);
                files.add(map);
            }
        }
        if (files.size()<=0){
            Map<String,Object> map2 = new HashMap<>();
            map2.put("name","你还有上传任何东西！");
            files.add(map2);
        }
        model.addAttribute("title","springBoot文件上传");
        model.addAttribute("files",files);
        return "index";
    }
}
