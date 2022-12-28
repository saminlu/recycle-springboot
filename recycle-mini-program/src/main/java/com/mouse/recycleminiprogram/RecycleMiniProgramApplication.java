package com.mouse.recycleminiprogram;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages ="com.mouse.recycleminiprogram.mapper")
@MapperScan("com.mouse.recycleminiprogram.mapper")
public class RecycleMiniProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecycleMiniProgramApplication.class, args);
    }

}
