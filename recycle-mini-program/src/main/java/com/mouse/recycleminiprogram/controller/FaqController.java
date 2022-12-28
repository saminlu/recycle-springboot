package com.mouse.recycleminiprogram.controller;

import com.mouse.recycleminiprogram.dto.FaqDto;
import com.mouse.recycleminiprogram.entity.Faq;
import com.mouse.recycleminiprogram.service.FaqService;
import com.mouse.recycleminiprogram.utils.PageParameter;
import com.mouse.recycleminiprogram.utils.PageResult;
import com.mouse.recycleminiprogram.utils.Result;
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
    public Result detail(@PathVariable("id") Integer id){
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
