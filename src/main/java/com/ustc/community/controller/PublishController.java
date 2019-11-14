package com.ustc.community.controller;

import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.Question;
import com.ustc.community.model.User;
import com.ustc.community.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Integer id,
                       Model model){
        Question question=questionMapper.getQuesionById(id);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id",id);
        return "publish";
    }
    @GetMapping("/publish")
    public String publish(HttpServletRequest request) {

        return "publish";
    }

    @PostMapping("/publish")
    public String addQuestion(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") String id,
            HttpServletRequest request,
            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "描述不能为空！");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空！");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "User is not login!");
            return "publish";
        }
        if (id!=null&&!"".equals(id)){
            Long gmtModified=System.currentTimeMillis();
            questionMapper.updateQuestion(id,title,description,tag,gmtModified);
        }else {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(System.currentTimeMillis());
            question.setCreator(user.getId().longValue());
            questionMapper.create(question);
        }

        return "redirect:/";
    }
}

