package com.ustc.community.controller;

import com.ustc.community.DTO.PageDTO;
import com.ustc.community.DTO.QuestionDTO;
import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.Question;
import com.ustc.community.model.User;
import com.ustc.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size
    ) {
        PageDTO pageDTO = questionService.selAll(page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "index";
    }
    @RequestMapping("/layout")
    public String layout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }


}
