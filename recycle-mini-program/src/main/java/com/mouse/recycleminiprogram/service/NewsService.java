package com.mouse.recycleminiprogram.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.recycleminiprogram.dto.NewsDto;
import com.mouse.recycleminiprogram.entity.News;
import com.mouse.recycleminiprogram.utils.PageParameter;
import com.mouse.recycleminiprogram.utils.PageResult;

public interface NewsService extends IService<News> {
    /**
     * 查询列表
     *
     * @return 实例对象
     */
    PageResult<News> queryList(PageParameter<NewsDto> pageParameter);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    News queryById(Integer id);
}
