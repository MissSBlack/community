package com.ustc.community.service;

import com.ustc.community.DTO.PageDTO;
import com.ustc.community.DTO.QuestionDTO;
import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.Question;
import com.ustc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO selAll(Integer page, Integer size) {
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        if (totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size+1;
        }
        if (page<1) page=1;
        if (page>totalPage) page=totalPage;
        //page指的是多少页，size指的是每页的大小
        //计算出分页所需要的偏移量
        Integer offset=size*(page-1);
        List<Question> questionList=questionMapper.selAll(offset,size);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setTotalPage(totalPage);
        for(Question question:questionList){
            //System.out.println(question.getCreator());
           User user= userMapper.findById(question.getCreator().intValue());
           QuestionDTO questionDTO=new QuestionDTO();
           //这个方法是复制属性
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);

        pageDTO.setPageInfor(totalCount,page,size);
        return pageDTO;
    }
}
