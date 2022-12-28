package com.mouse.recycleadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.recycleadmin.dto.NewsDto;
import com.mouse.recycleadmin.entity.Faq;
import com.mouse.recycleadmin.entity.News;
import com.mouse.recycleadmin.mapper.NewsMapper;
import com.mouse.recycleadmin.service.NewsService;
import com.mouse.recycleadmin.utils.PageParameter;
import com.mouse.recycleadmin.utils.PageResult;
import com.mouse.recycleadmin.utils.PageUtil;
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

    public Integer create(NewsDto newsDto){
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        return this.newsMapper.insert(news);
    }
}
