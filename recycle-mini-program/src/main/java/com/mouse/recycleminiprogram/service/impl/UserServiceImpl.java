package com.mouse.recycleminiprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.recycleminiprogram.entity.User;
import com.mouse.recycleminiprogram.mapper.UserMapper;
import com.mouse.recycleminiprogram.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User queryByOpenId(String openId) {
        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("openid",openId);
        return this.userMapper.selectOne(q);
    }

}
