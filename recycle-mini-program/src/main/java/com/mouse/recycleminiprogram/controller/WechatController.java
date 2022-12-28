package com.mouse.recycleminiprogram.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mouse.recycleminiprogram.dto.UserDto;
import com.mouse.recycleminiprogram.entity.User;
import com.mouse.recycleminiprogram.mapper.UserMapper;
import com.mouse.recycleminiprogram.service.UserService;
import com.mouse.recycleminiprogram.utils.HttpClientUtils;
import com.mouse.recycleminiprogram.utils.JwtUtil;
import com.mouse.recycleminiprogram.utils.RedisUtils;
import com.mouse.recycleminiprogram.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/wechat")
public class WechatController {

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @PostMapping(value = "/login")
    public Result wxLogin(@RequestBody UserDto userDto) throws Exception {
        Map<String, Object> result = new HashMap<>();
        result.put("status", 200);
        String AppId = this.appid;  //公众平台自己的appId
        String AppSecret = this.appsecret;  //AppSecret
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + AppId +
                "&secret=" + AppSecret +
                "&js_code=" + userDto.getCode() +
                "&grant_type=authorization_code";
        System.out.println("===" + url);
        String jsonString = JSON.toJSONString(HttpClientUtils.sendGet(url));
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONObject content = JSON.parseObject(jsonObject.getString("content"));
        if (StringUtils.contains(jsonString, "errcode")) {
            result.put("status", 500);
            result.put("msg", "登录失败");
            return Result.error("登录失败");
        }
        String openid = content.getString("openid");
        String sessionKey = content.getString("session_key");
        if (openid == null || openid == "") {
            result.put("status", 400);
            result.put("msg", "登录失败");
            return Result.error("登录失败");
        }
        User u = this.userService.queryByOpenId(openid);
        User userInfo = new User();
        if (u == null) {
            userInfo.setNickname(userDto.getNickName());
            userInfo.setAvatarUrl(userDto.getAvatarUrl());
            userInfo.setGender(userDto.getGender());
            userInfo.setOpenid(openid);
            userInfo.setSessionKey(sessionKey);
            userInfo.setCreateTime(new Date(System.currentTimeMillis()));
            userInfo.setUpdateTime(new Date(System.currentTimeMillis()));
            this.userMapper.insert(userInfo);
        } else {
            userInfo.setSessionKey(sessionKey);
            userInfo.setUpdateTime(new Date(System.currentTimeMillis()));
            QueryWrapper q = new QueryWrapper();
            q.eq("openid", openid);
            this.userMapper.update(userInfo, q);
        }
        String token = JwtUtil.makeToken(openid);
        result.put("token", token);
        Boolean rs = redisUtils.set("wechat_token_"+openid, token, 86400);
        System.out.println("===" + rs);
//        redisTemplate.opsForValue().set("wxLogin",jsonData, 5, TimeUnit.HOURS);  //存到redis五个小时过期
        return Result.success(result);
    }

}
