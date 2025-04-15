package com.ithema.service;

import com.ithema.pojo.Category;

import java.util.List;

public interface CategorySevice {
    void add(Category category);

    List<Category> list();

    Category findById(Integer id);

    void update(Category category);

    void delete(Integer id);
}
