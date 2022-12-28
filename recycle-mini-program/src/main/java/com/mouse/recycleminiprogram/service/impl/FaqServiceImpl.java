package com.mouse.recycleminiprogram.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mouse.recycleminiprogram.dto.FaqDto;
import com.mouse.recycleminiprogram.mapper.FaqMapper;
import com.mouse.recycleminiprogram.entity.Faq;
import com.mouse.recycleminiprogram.service.FaqService;
import com.mouse.recycleminiprogram.utils.PageParameter;
import com.mouse.recycleminiprogram.utils.PageResult;
import com.mouse.recycleminiprogram.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FaqServiceImpl extends ServiceImpl<FaqMapper, Faq> implements FaqService {

    @Resource
    private FaqMapper faqMapper;

    /**
     * 查询列表
     *
     * @return 实例对象
     */
    public PageResult<Faq> queryList(PageParameter<FaqDto> pageParameter) {
        Page<Faq> page = new Page<>(pageParameter.getCurrent(), pageParameter.getSize());
        QueryWrapper<Faq> q = new QueryWrapper<>();
        return PageUtil.page(faqMapper.selectPage(page, q));
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Faq queryById(Integer id) {
        return this.faqMapper.selectById(id);
    }
}
