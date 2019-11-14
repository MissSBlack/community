package com.ustc.community.controller;

import com.ustc.community.DTO.AccessTokenDTO;
import com.ustc.community.DTO.GitHubUsers;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.User;
import com.ustc.community.provider.GitHubAccessProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * 用来接收GitHub认证之后的code信息；
 * 用来跳转到callback页面
 * by Ly
 */
@Controller
public class AuthorizeController {
    //该注解用来完成bean的自动装配
    @Autowired
    private GitHubAccessProvider gitHubAccessProvider;
    //这个注解用来读取application.properties的值
    @Value("${github.Client_id}")
    private String client_id;
    @Value("${github.Client_secret}")
    private String client_secret;
    @Value("${github.Redirect_uri}")
    private String redirect_uri;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);

        String accessToken = gitHubAccessProvider.getAccessToken(accessTokenDTO);
        //System.out.println(accessToken);
        GitHubUsers gitHubUsers = gitHubAccessProvider.getUser(accessToken);
        //从gitHub成功获取到用户信息
        if (gitHubUsers != null) {
            //判断用户是否已经存在于数据库，如果有，就更新用户，否则，创建一个新用户
            String accountId=String.valueOf(gitHubUsers.getId());
            User dbUser=userMapper.getUserByAccountId(accountId);
            if (dbUser==null){
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setAccountId(String.valueOf(gitHubUsers.getId()));
                user.setName(gitHubUsers.getName());
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatorUrl(gitHubUsers.getAvatar_url());
                userMapper.insterUser(user);
                response.addCookie(new Cookie("token", token));
            }else{
                String token=dbUser.getToken();
                String name=gitHubUsers.getName();
                String avatorUrl=gitHubUsers.getAvatar_url();
                Long gmtModified=System.currentTimeMillis();
                userMapper.updateUser(name,avatorUrl,gmtModified,accountId);
                response.addCookie(new Cookie("token", token));
            }
            //request.getSession().setAttribute("user", gitHubUsers);
            return "redirect:/";
            //return "index";
        } else {
            //用户为空，登录失败
//            return "index";
            return "redirect:/";
        }

    }

}

