package com.ithema.controller;

import com.ithema.pojo.Article;
import com.ithema.pojo.Category;
import com.ithema.pojo.PageBean;
import com.ithema.pojo.Result;
import com.ithema.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }

    @GetMapping
    public Result list(Integer pageNum,
                       Integer pageSize,
                       @RequestParam(required = false) Integer categoryId,
                       @RequestParam(required = false) String state
                       ) {
        PageBean<Article> pb = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(pb);

    }

    @PutMapping
    public Result update(@RequestBody Article article) {
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping
    public Result delete( Integer id) {
        articleService.delete(id);
        return Result.success();
    }


}
