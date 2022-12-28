package com.mouse.recycleadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.recycleadmin.dto.AdminDto;
import com.mouse.recycleadmin.entity.Admin;
import com.mouse.recycleadmin.mapper.AdminMapper;
import com.mouse.recycleadmin.service.AdminService;
import com.mouse.recycleadmin.utils.JwtUtil;
import com.mouse.recycleadmin.utils.PasswordEncoder;
import com.mouse.recycleadmin.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Resource
    private RedisUtils redisUtils;

    public HashMap<String,String> login(AdminDto adminDto){
        HashMap<String,String> map= new HashMap<>();
        QueryWrapper<Admin> q = new QueryWrapper<>();
        String account = adminDto.getAccount();
        String pwd = adminDto.getPwd();
        PasswordEncoder pe = new PasswordEncoder();
        String encodePwd = pe.encode(pwd);
        System.out.println("encodePwd=="+encodePwd);
        q.eq("account",account);
        Admin admin = adminMapper.selectOne(q);
        if(admin != null){
            boolean rs = pe.matches(pwd,admin.getPwd());
            if(rs){
                String token = JwtUtil.makeToken(admin.getId().toString());
                System.out.println("token:"+token);
                map.put("token",token);
            }
        }

        return map;
    }

    public Admin queryById(Integer id){
        return adminMapper.selectById(id);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || header.length()>7) {
            String token = header.substring(7);
            redisUtils.del("admin-id-"+token);
        }
    }

}
