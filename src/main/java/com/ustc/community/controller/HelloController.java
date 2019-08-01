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
    @GetMapping ("/hello")
    public String hello(@RequestParam("name") String name, Model model){
        System.out.println(name);
        model.addAttribute("name",name);
        //model.addAttribute("name",name);
        return "hello";
    }
    @GetMapping ("/hello1")
    public String hello( Model model){
        System.out.println(1);
        model.addAttribute("name","user");
        //model.addAttribute("name",name);
        return "hello";
    }

}

