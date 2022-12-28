package com.mouse.recycleadmin.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.List;


public class PageParameter<P> {
    private int current = 1;

    private int size = 10;
    private P selectParameter;

    public PageParameter() {
    }

    public int getCurrent() {
        return this.current;
    }

    public int getSize() {
        return this.size;
    }

    public P getSelectParameter() {
        return this.selectParameter;
    }

    public void setCurrent(final int current) {
        this.current = current;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public void setSelectParameter(final P selectParameter) {
        this.selectParameter = selectParameter;
    }

    public String toString() {
        return "PageParameter(current=" + this.getCurrent() + ", size=" + this.getSize() + ", selectParameter=" + this.getSelectParameter() + ")";
    }
}
