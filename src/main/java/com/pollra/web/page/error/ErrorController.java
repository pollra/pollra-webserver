package com.pollra.web.page.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {
    @GetMapping("/error/signuperrorid")
    public ModelAndView signuperrorid(HttpServletRequest request){
        return new ModelAndView("/error/signuperrorid");
    }

    @GetMapping("/error/signuperrornik")
    public ModelAndView signuperrornik(HttpServletRequest request){
        return new ModelAndView("/error/signuperrornik");
    }

    @GetMapping("/error/signuperroridnik")
    public ModelAndView signuperroridnik(HttpServletRequest request){
        return new ModelAndView("/error/signuperroridnik");
    }
}
