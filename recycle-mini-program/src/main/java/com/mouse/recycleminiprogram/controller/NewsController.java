package com.mouse.recycleminiprogram.controller;

import com.mouse.recycleminiprogram.dto.NewsDto;
import com.mouse.recycleminiprogram.entity.News;
import com.mouse.recycleminiprogram.service.NewsService;
import com.mouse.recycleminiprogram.utils.PageParameter;
import com.mouse.recycleminiprogram.utils.PageResult;
import com.mouse.recycleminiprogram.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
    @Resource
    private NewsService newsService;

    @PostMapping(value = "/list")
    public Result<PageResult<News>> list(@RequestBody PageParameter<NewsDto> pageParameter){
        return Result.success(this.newsService.queryList(pageParameter));
    }

    @GetMapping(value = "/detail/{id}")
    public Result detail(@PathVariable("id") Integer id){
        return Result.success(this.newsService.queryById(id));
    }

}
