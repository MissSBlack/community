package com.ustc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * learn springBoot project first day
 * by Ly
 */
@Controller
public class HelloController {
    /*
    创建一个/hello的映射方法，从地址栏得到参数name的值赋值给name，然后传递一个model过来，在model中
     创建键值对name-value
     */
    @GetMapping ("/hello")
    public String hello(@RequestParam("name") String name, Model model){
        System.out.println(name);
        model.addAttribute("name",name);
        //model.addAttribute("name",name);
        return "index";
    }
    @GetMapping ("/")
    public String hello( Model model){
        System.out.println(1);
        model.addAttribute("name","user");
        //model.addAttribute("name",name);
        return "index";

    }

}

