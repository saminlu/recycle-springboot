package com.mouse.recycleadmin.controller;

import com.mouse.recycleadmin.dto.FaqDto;
import com.mouse.recycleadmin.entity.Faq;
import com.mouse.recycleadmin.service.FaqService;
import com.mouse.recycleadmin.utils.PageParameter;
import com.mouse.recycleadmin.utils.PageResult;
import com.mouse.recycleadmin.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/faq")
public class FaqController {

    @Resource
    private FaqService faqService;

    @PostMapping(value = "/list")
    public Result<PageResult<Faq>> list(@RequestBody PageParameter<FaqDto> pageParameter){
        return Result.success(this.faqService.queryList(pageParameter));
    }

    @GetMapping(value = "/detail/{id}")
    public Result<Faq> detail(@PathVariable("id") Integer id){
        return Result.success(this.faqService.queryById(id));
    }

    @PostMapping("/save")
    public Result save(@RequestBody Faq faq){
        return Result.success(this.faqService.save(faq));
    }

    @PostMapping("/update")
    public Result update(@RequestBody Faq faq) {
        return Result.success(this.faqService.updateById(faq));
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return  Result.success(this.faqService.removeById(id));
    }
}
