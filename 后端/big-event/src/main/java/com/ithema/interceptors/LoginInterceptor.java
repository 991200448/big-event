package com.ithema.interceptors;

import com.ithema.utils.JwtUtil;
import com.ithema.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 验证token
        String token = request.getHeader("Authorization");
        try {
            ValueOperations<String,String> ops = redisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if(!StringUtils.hasLength(redisToken)) {
                throw new RuntimeException("token无效");
            }
            Map<String, Object> claims = JwtUtil.parseToken(redisToken);
            //2. 将claims存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            return true;
        }catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //3. 清除ThreadLocal中的claims
        ThreadLocalUtil.remove();
    }
}
