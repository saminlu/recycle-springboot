package com.mouse.recycleadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mouse.recycleadmin.dto.FaqDto;
import com.mouse.recycleadmin.entity.Faq;
import com.mouse.recycleadmin.utils.PageParameter;
import com.mouse.recycleadmin.utils.PageResult;

public interface FaqService extends IService<Faq> {

    /**
     * 查询列表
     *
     * @return 实例对象
     */
    PageResult<Faq> queryList(PageParameter<FaqDto> pageParameter);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Faq queryById(Integer id);

}

