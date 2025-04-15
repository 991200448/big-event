package com.ithema.controller;

import com.ithema.pojo.Result;
import com.ithema.pojo.User;
import com.ithema.service.UserService;
import com.ithema.utils.JwtUtil;
import com.ithema.utils.Md5Util;
import com.ithema.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{3,16}$") String username, @Pattern(regexp = "^\\S{6,12}$") String password) {
        //判断用户名是否存在
        User u = userService.findByUserName(username);
        if (u == null) {
            //用户名不存在，可以注册
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{3,16}$") String username, @Pattern(regexp = "^\\S{3,16}$") String password) {
        //根据用户名查询用户
        User loginUser = userService.findByUserName(username);
        if (loginUser == null) {
            return Result.error("用户名不存在");
        }

        //判断密码是否正确
        if (Md5Util.checkPassword(password, loginUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", loginUser.getId());
            claims.put("username", loginUser.getUsername());
            //登录成功，生成token，响应给客户端
            String token = JwtUtil.genToken(claims);
            //把token存储到redis中
            ValueOperations ops = redisTemplate.opsForValue();
            ops.set(token, token, 1, TimeUnit.HOURS);
            return Result.success(token);
        }

        return Result.error("密码错误");
    }

    @GetMapping("/info")
    public Result userInfo() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        // user.setUpdateTime(LocalDateTime.now());
        //调用service更新用户信息
        userService.update(user);
        return Result.success();
    }
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam   @URL String avatarUrl) {
        //调用service更新用户信息
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token) {
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少必要参数");
        }
        Map<String, Object> claims = ThreadLocalUtil.get();
        String username = (String) claims.get("username");
        User user = userService.findByUserName(username);
        if(!Md5Util.checkPassword(oldPwd,user.getPassword())){
            return Result.error("旧密码不正确");
        }
        if(!newPwd.equals(rePwd)){
        return Result.error("两次密码不一致");
        }
        userService.updatePwd(newPwd);
        ValueOperations ops = redisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success();
    }
    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        ValueOperations ops = redisTemplate.opsForValue();
        ops.getOperations().delete(token);
        return Result.success("注销成功");
    }


}
