package com.mouse.recycleminiprogram.dto;

import lombok.Data;

@Data
public class UserDto {
    private String code;  //只是为了能接收参数，不需要存入数据库
    private String nickName;  //微信名
    private String avatarUrl;  //头像
    private Integer gender;
}
