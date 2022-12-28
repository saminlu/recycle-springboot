package com.mouse.recycleminiprogram.utils;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;


public class PageResult<R> {
    private List<R> records;
    private long current;
    private long size;
    private long total;
    private long pages;

    public PageResult() {
    }

    public PageResult(List<R> records, long current, long size, long total, long pages) {
        this.records = records;
        this.current = current;
        this.size = size;
        this.total = total;
        this.pages = pages;
    }

    public PageResult(Page<R> page) {
        this.setCurrent(page.getCurrent());
        this.setPages(page.getPages());
        this.setRecords(page.getRecords());
        this.setSize(page.getSize());
        this.setTotal(page.getTotal());
    }

    public List<R> getRecords() {
        return this.records;
    }

    public long getCurrent() {
        return this.current;
    }

    public long getSize() {
        return this.size;
    }

    public long getTotal() {
        return this.total;
    }

    public long getPages() {
        return this.pages;
    }

    public void setRecords(final List<R> records) {
        this.records = records;
    }

    public void setCurrent(final long current) {
        this.current = current;
    }

    public void setSize(final long size) {
        this.size = size;
    }

    public void setTotal(final long total) {
        this.total = total;
    }

    public void setPages(final long pages) {
        this.pages = pages;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResult)) {
            return false;
        } else {
            PageResult<?> other = (PageResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$records = this.getRecords();
                Object other$records = other.getRecords();
                if (this$records == null) {
                    if (other$records != null) {
                        return false;
                    }
                } else if (!this$records.equals(other$records)) {
                    return false;
                }

                if (this.getCurrent() != other.getCurrent()) {
                    return false;
                } else if (this.getSize() != other.getSize()) {
                    return false;
                } else if (this.getTotal() != other.getTotal()) {
                    return false;
                } else if (this.getPages() != other.getPages()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PageResult;
    }

    public String toString() {
        return "PageResult(records=" + this.getRecords();

    }
}