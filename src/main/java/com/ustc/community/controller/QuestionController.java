package com.ustc.community.controller;

import com.ustc.community.DTO.QuestionDTO;
import com.ustc.community.service.QuestionService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @GetMapping("/question/{id}")
    public String showQuestionDetails(@PathVariable Integer id, HttpServletRequest request,
                                      Model model){
        QuestionDTO questionById = questionService.getQuestionById(id);
        model.addAttribute("question",questionById);
        return "question";

    }

 }
