package com.bugjc.zuul.web;

import com.bugjc.zuul.core.dto.ResultGenerator;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorHandlerController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error/error";
    }

    @RequestMapping("/error")
    public String error() {
        return ResultGenerator.genFailResult("异常").toString();
    }

}
