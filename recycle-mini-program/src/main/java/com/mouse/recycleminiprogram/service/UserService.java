package com.mouse.recycleminiprogram.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.recycleminiprogram.entity.User;

public interface UserService extends IService<User> {

    /**
     * 通过openid查询单条数据
     *
     * @param openId 主键
     * @return 实例对象
     */
    User queryByOpenId(String openId);


}
