package com.ustc.community.utils;

import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class LoginUtil {
    @Autowired
    private UserMapper userMapper;
    public  String loginUtil(HttpServletRequest request,String url){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                String token = cookie.getValue();
                System.out.println(token);
                User user=userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }

        return url;
    }
}
