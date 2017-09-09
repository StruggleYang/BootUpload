package org.struy.main.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainControoller {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }

}
