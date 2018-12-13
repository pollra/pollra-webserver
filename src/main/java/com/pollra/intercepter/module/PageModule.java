package com.pollra.intercepter.module;

import com.pollra.web.function.page.PageVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public interface PageModule {
    public ModelAndView getModelAndView(PageVO pageVO);
    public void setPostingList(ModelAndView view, PageVO pageVO);
    public void setCategories(ModelAndView view, int userNum);
}
