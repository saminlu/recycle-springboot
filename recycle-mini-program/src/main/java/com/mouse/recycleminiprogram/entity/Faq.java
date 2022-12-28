package com.mouse.recycleminiprogram.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class Faq {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
}
