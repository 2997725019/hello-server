package com.stu.helloserver.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stu.helloserver.common.ResultCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        boolean isCreateUser = "POST".equalsIgnoreCase(method) && "/api/users".equals(uri);
        boolean isGetUser = "GET".equalsIgnoreCase(method) && uri.startsWith("/api/users/");

        if (isCreateUser || isGetUser) {
            return true;
        }

        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            Map<String, Object> result = new HashMap<>();
            result.put("code", ResultCode.TOKEN_INVALID.getCode());
            result.put("msg", "非法操作: 敏感动作 [" + method + "] 需要登录凭证");
            result.put("data", null);

            String json = new ObjectMapper().writeValueAsString(result);
            response.getWriter().write(json);
            return false;
        }

        return true;
    }
}
