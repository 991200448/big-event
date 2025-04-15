package com.ithema.controller;

import com.ithema.pojo.Category;
import com.ithema.pojo.Result;
import com.ithema.service.CategorySevice;
import com.ithema.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategorySevice categorySevice;


    @PostMapping
    public Result category(@RequestBody Category category) {
        categorySevice.add(category);
        return Result.success();
    }
    @GetMapping("/list")
    public Result<List<Category>> list() {
        List<Category> cs = categorySevice.list();
        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result detail(Integer id) {
        Category category =  categorySevice.findById(id);
        return Result.success(category);
    }

    @PutMapping
    public Result update(@RequestBody Category category) {
        categorySevice.update(category);
        return Result.success();
    }


    @DeleteMapping
    public Result delete( Integer id) {
        categorySevice.delete(id);
        return Result.success();
    }

}
