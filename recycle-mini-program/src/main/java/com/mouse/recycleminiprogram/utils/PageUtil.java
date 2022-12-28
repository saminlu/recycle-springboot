package com.mouse.recycleminiprogram.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class PageUtil {
    public PageUtil() {
    }

    public static <T> PageResult<T> page(Page<T> page) {
        PageResult<T> pageResult = new PageResult();
        if (null == page) {
            return pageResult;
        } else {
            pageResult.setCurrent(page.getCurrent());
            pageResult.setSize(page.getSize());
            pageResult.setPages(page.getPages());
            pageResult.setTotal(page.getTotal());
            pageResult.setRecords(page.getRecords());
            return pageResult;
        }
    }

    public static Page getPage(PageParameter pageParameter) {
        return getPage(pageParameter, false);
    }

    public static Page getPage(PageParameter pageParameter, boolean isOrderBy) {
        Page pageResult = new Page();
        if (null == pageParameter) {
            return pageResult;
        } else {
            pageResult.setCurrent((long)pageParameter.getCurrent());
            pageResult.setSize((long)pageParameter.getSize());
            return pageResult;
        }
    }

    public static <T> List<T> startPage(List<T> list, Integer pageNum, Integer pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        } else {
            Integer count = list.size();
            Integer pageCount;
            if (count % pageSize == 0) {
                pageCount = count / pageSize;
            } else {
                pageCount = count / pageSize + 1;
            }

            if (pageCount < pageNum) {
                return null;
            } else {
                int fromIndex;
                int toIndex;
                if (!pageNum.equals(pageCount)) {
                    fromIndex = (pageNum - 1) * pageSize;
                    toIndex = fromIndex + pageSize;
                } else {
                    fromIndex = (pageNum - 1) * pageSize;
                    toIndex = count;
                }

                List<T> pageList = list.subList(fromIndex, toIndex);
                return pageList;
            }
        }
    }

}
