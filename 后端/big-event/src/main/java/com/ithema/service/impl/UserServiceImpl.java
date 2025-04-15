package com.ithema.service.impl;

import com.ithema.mapper.UserMapper;
import com.ithema.pojo.User;
import com.ithema.service.UserService;
import com.ithema.utils.Md5Util;
import com.ithema.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        String encodePassword = Md5Util.getMD5String(password);
        userMapper.add(username, encodePassword);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        userMapper.updateAvatar(avatarUrl, (Integer) claims.get("id"));
    }

    @Override
    public void updatePwd(String newPwd) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Integer id = (Integer) claims.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }
}
