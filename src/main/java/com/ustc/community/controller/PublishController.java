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
    @GetMapping("/publish" )
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String addQuestion(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {
        System.out.println(title);
            User user=null;
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie:cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
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
            // question.setCreator( user.getId());
            questionMapper.create(question);
           return "redirect:/";
        }
    }

