package com.mouse.recycleadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.recycleadmin.dto.AdminDto;
import com.mouse.recycleadmin.entity.Admin;
import com.mouse.recycleadmin.utils.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public interface AdminService  extends IService<Admin> {

    HashMap<String,String> login(AdminDto adminDto);

    Admin queryById(Integer id);

    void logout(HttpServletRequest request);
}
