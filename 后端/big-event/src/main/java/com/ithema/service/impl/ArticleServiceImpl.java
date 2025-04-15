package com.ithema.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ithema.mapper.ArticleMapper;
import com.ithema.pojo.Article;
import com.ithema.pojo.PageBean;
import com.ithema.service.ArticleService;
import com.ithema.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        PageBean<Article> pageBean = new PageBean<>();
        PageHelper.startPage(pageNum, pageSize);

        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> articles = articleMapper.list(userId, categoryId, state);
        Page<Article> page = (Page<Article>) articles;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    @Override
    public void delete(Integer id) {
        articleMapper.deleteById(id);
    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }
}
