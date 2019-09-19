package com.ustc.community.controller;

import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.Question;
import com.ustc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
   // private LoginUtil loginUtil=new LoginUtil();
    @GetMapping("/publish" )
    public String publish(HttpServletRequest request){

        return  "publish";
    }
    @PostMapping("/publish")
    public String addQuestion(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {
            model.addAttribute("title",title);
            model.addAttribute("description",description);
            model.addAttribute("tag",tag);
            if (title==null||title==""){
                model.addAttribute("error","标题不能为空！");
                return "publish";
            }
            if (description==null||description==""){
            model.addAttribute("error","描述不能为空！");
            return "publish";
           }
           if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空！");
            return "publish";
          }

            User user=null;
            Cookie[] cookies = request.getCookies();
            if (cookies==null){
                model.addAttribute("error","User is not login!");
                return "publish";
            }
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    //System.out.println(user.getGmtCreate());
                    //System.out.println(user.getAccountId());
                    //System.out.println(user.getAvatorUrl());
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
            if (user==null){
                model.addAttribute("error","User is not login!");
                return "publish";
            }
           Question question=new Question();
           // question.setTitle("hello");
           question.setTitle(title);
           question.setDescription(description);
           question.setTag(tag);
           question.setGmtCreate(System.currentTimeMillis());
           question.setGmtModified(System.currentTimeMillis());
           question.setCreator( user.getId().longValue());
           questionMapper.create(question);
           return "redirect:/";
        }
    }

