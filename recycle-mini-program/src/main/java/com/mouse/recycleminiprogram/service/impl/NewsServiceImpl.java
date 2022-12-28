package com.mouse.recycleminiprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.recycleminiprogram.dto.NewsDto;
import com.mouse.recycleminiprogram.entity.News;
import com.mouse.recycleminiprogram.mapper.NewsMapper;
import com.mouse.recycleminiprogram.service.NewsService;
import com.mouse.recycleminiprogram.utils.PageParameter;
import com.mouse.recycleminiprogram.utils.PageResult;
import com.mouse.recycleminiprogram.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Resource
    private NewsMapper newsMapper;

    /**
     * 查询列表
     *
     * @return 实例对象
     */
    public PageResult<News> queryList(PageParameter<NewsDto> pageParameter) {
        Page<News> page = new Page<>(pageParameter.getCurrent(), pageParameter.getSize());
        QueryWrapper<News> q = new QueryWrapper<>();
        q.eq("is_deleted",0);
        return PageUtil.page(newsMapper.selectPage(page, q));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public News queryById(Integer id) {
        return this.newsMapper.selectById(id);
    }
}
