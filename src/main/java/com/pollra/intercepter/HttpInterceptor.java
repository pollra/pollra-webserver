package com.pollra.intercepter;

import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.account.service.UserInfoService;
import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.service.CategoryService;
import com.pollra.web.function.page.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class HttpInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);
    private UserInfoService userInfoService;
    private CategoryService categoryService;

    public HttpInterceptor(UserInfoService userInfoService, CategoryService categoryService) {
        this.userInfoService = userInfoService;
        this.categoryService = categoryService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        ModelAndView view = new ModelAndView("index");
        logger.info("================ Before Method");
        HttpSession session = request.getSession();
        if(session.getAttribute("userNik")==null){
            session.setAttribute("userNik", "guest");
        }
        // uri 로 받은 페이지 정보를 Map 형식으로 저장한다.
        Map<?,?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);


        return true;
    }

    @Override
    public void postHandle( HttpServletRequest request,
                            HttpServletResponse response,
                            Object handler,
                            ModelAndView modelAndView) {
        logger.info("================ Method Executed");
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) {
        logger.info("================ Method Completed");
    }
}