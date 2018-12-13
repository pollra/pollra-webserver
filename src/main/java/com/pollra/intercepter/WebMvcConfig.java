package com.pollra.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier(value = "httpInterceptor")
    private HandlerInterceptor interceptor;

    @Autowired
    @Qualifier(value = "signInInterceptor")
    private HandlerInterceptor signInInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        PathMatcher pathMatcher = new AntPathMatcher("/");
        registry.addInterceptor(interceptor).pathMatcher(pathMatcher)
                .addPathPatterns("/", "/**","/user/signin");
//                .excludePathPatterns("");
        registry.addInterceptor(signInInterceptor).pathMatcher(pathMatcher)
                .addPathPatterns("/user/signup");
    }



}