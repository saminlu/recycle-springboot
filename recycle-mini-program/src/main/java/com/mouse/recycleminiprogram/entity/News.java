package com.mouse.recycleminiprogram.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class News {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String picture;
    private Integer visitCount;
    private String content;
    private Integer isDeleted;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    private Date updateTime;
}
