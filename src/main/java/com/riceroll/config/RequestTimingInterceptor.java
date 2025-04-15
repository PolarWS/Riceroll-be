package com.riceroll.config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RequestTimingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime); // 将时间存储在请求对象中
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 获取请求开始时间，计算请求处理时间
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 记录请求信息和消耗时间
        String method = request.getMethod();
        String uri = request.getRequestURI();
        System.out.println("API请求: " + method + " " + uri + " 消耗时间: " + duration + " ms");
    }
}
