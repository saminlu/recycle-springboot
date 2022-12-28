package com.mouse.recycleadmin.controller;

import com.mouse.recycleadmin.dto.NewsDto;
import com.mouse.recycleadmin.entity.News;
import com.mouse.recycleadmin.service.NewsService;
import com.mouse.recycleadmin.utils.PageParameter;
import com.mouse.recycleadmin.utils.PageResult;
import com.mouse.recycleadmin.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;

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

    @PostMapping(value = "/create")
    public Result create(@RequestBody @Valid NewsDto newsDto){
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setVisitCount(0);
        news.setCreateTime(new Date(System.currentTimeMillis()));
        news.setUpdateTime(new Date(System.currentTimeMillis()));
        return Result.success(this.newsService.save(news));
    }

}
