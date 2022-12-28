package com.mouse.recycleminiprogram.config;

import com.mouse.recycleminiprogram.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器
 * @author LiZeyuan
 * @date 2022/8/16 16:33
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")             // 拦截所有请求
                .excludePathPatterns("/wechat/login")    //排除登录接口
                .excludePathPatterns("/news/**");
//                .excludePathPatterns("/faq/**");    //排除FAQ接口
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
