package org.struy.main.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/")
public class MainControoller {

    @RequestMapping()
    public String root() {
        return "index";
    }

    @RequestMapping("/showFiles")
    @ResponseBody
    public String showFiles() {

        return "Hello";
    }

}
