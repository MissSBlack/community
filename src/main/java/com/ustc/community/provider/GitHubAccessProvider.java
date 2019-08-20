package com.ustc.community.provider;

import com.ustc.community.DTO.AccessTokenDTO;
import com.ustc.community.DTO.GitHubUsers;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

import com.alibaba.fastjson.JSON;

//把普通的pojo实例化 实例化Spring容器中
@Component
public class GitHubAccessProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            System.out.println(string);
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    public GitHubUsers getUser(String access_Token) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //涉及到URL的地方，一定一定要确保没有空格，该错误导致一小时时间浪费，吸取教训。
                .url("https://api.github.com/user?access_token=" + access_Token)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //System.out.println(string);
            GitHubUsers gitHubUsers = JSON.parseObject(string, GitHubUsers.class);
            //System.out.println(gitHubUsers.getId());
            return gitHubUsers;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
