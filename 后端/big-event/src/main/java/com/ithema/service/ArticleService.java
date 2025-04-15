package com.ithema.service;

import com.ithema.pojo.Article;
import com.ithema.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void delete(Integer id);

    void update(Article article);
}
