package com.mouse.recycleminiprogram.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mouse.recycleminiprogram.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
