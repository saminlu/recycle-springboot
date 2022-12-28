package com.mouse.recycleminiprogram.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.mouse.recycleminiprogram.entity.User;
import com.mouse.recycleminiprogram.service.UserService;
import com.mouse.recycleminiprogram.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String header = request.getHeader("Authorization");

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        System.out.println("token==="+header);
        // 验证payload部分
        if (header == null || header.length()<8) {
            map.put("msg", "token错误，请重新登录");
            map.put("code", 401);
            JSONObject json = new JSONObject(map);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();
            return false;
        }
        Claim uid;
        String token = header.substring(7);
        try {
            uid = JWT.decode(token).getClaim("uid");
            System.out.println("uid"+Integer.parseInt(uid.asString()));
            User admin = userService.queryByOpenId(uid.asString());
            if (admin == null) {
                map.put("msg", "用户不存在，请重新登录");
                map.put("code", 4010);
                JSONObject json = new JSONObject(map);
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.print(json);
                writer.flush();
                writer.close();
            }
        } catch (JWTDecodeException e){
            map.put("msg", "token解析错误，请重新登录");
            map.put("code", 4010);
            JSONObject json = new JSONObject(map);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();
            return false;
        }
        try {
            boolean rs = JwtUtil.verifyToken(token);
            System.out.println("rs:"+rs);
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            map.put("msg", "token错误或已过期，请重新登录");
            System.out.println("token错误或已过期，请重新登录");
            map.put("code", 4010);
            JSONObject json = new JSONObject(map);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg","token无效");
            System.out.println("token错误或已过期，请重新登录");
            map.put("code", 4010);
            JSONObject json = new JSONObject(map);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.print(json);
            writer.flush();
            writer.close();
        }
        return false;
    }
}