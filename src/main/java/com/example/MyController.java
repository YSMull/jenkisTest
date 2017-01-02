package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by maoyusu on 2017/1/2.
 */
@Controller
@RequestMapping("/")
public class MyController {

    @RequestMapping("hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }
}
