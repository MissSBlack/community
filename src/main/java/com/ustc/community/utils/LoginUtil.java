package com.ustc.community.utils;

import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
@Component
public class LoginUtil {
    @Autowired
    private UserMapper userMapper;
    public  String loginUtil(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token="-1";
        if (cookies==null){
            return token;
        }
        for (Cookie cookie:cookies){
            if (cookie.getName().equals("token")){
                token = cookie.getValue();
               // System.out.println(token);
                User user=userMapper.findByToken(token);
                if (user!=null){
                    request.getSession().setAttribute("user",user);
                    return token;
                }
                break;
            }
        }

        return token;
    }
}
