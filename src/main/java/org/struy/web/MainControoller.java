package org.struy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/")
public class MainControoller {

    @ApiIgnore
    @RequestMapping()
    public String root() {
        return "index";
    }
}
