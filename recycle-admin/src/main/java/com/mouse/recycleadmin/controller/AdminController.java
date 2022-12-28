package com.mouse.recycleadmin.controller;

import com.mouse.recycleadmin.dto.AdminDto;
import com.mouse.recycleadmin.entity.Admin;
import com.mouse.recycleadmin.service.AdminService;
import com.mouse.recycleadmin.utils.RedisUtils;
import com.mouse.recycleadmin.utils.Result;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private RedisUtils redisUtils;

    @PostMapping(value = "/login")
    public Result login(@RequestBody @Valid AdminDto adminDto){
        HashMap<String,String> map = this.adminService.login(adminDto);
        if(!CollectionUtils.isEmpty(map)){
            return Result.success(map);
        }else{
            return Result.error("账号或密码不正确");
        }
    }

    @GetMapping(value = "/info")
    public Result info(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        String uid = redisUtils.get("admin-id-"+token).toString();
        System.out.println("uid"+uid);
        Admin admin = adminService.queryById(Integer.parseInt(uid));
        //TODO 权限角色
        HashMap<String,Object> info = new HashMap<>();
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        info.put("roles",roles);
        info.put("avatar",admin.getHeadPic());
        info.put("name",admin.getAccount());
        return Result.success(info);
    }

    @PostMapping(value = "/logout")
    public Result logout(HttpServletRequest request){
        adminService.logout(request);
        return Result.success();
    }
}
