package com.bugjc.zuul;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/test/hello")
    public String testServer(){
        return "hello！";
    }
}
