package com.mouse.recycleadmin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("recycle_admin")
public class Admin {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String account;
    private String pwd;
    private String headPic;
}
