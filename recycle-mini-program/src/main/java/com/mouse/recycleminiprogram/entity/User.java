package com.mouse.recycleminiprogram.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String nickname;
    private String avatarUrl;
    private Integer gender;
    private String openid;
    private String sessionKey;
    private Date createTime;
    private Date updateTime;
}
