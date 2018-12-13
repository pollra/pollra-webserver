package com.pollra.web.page.sign;

import com.pollra.intercepter.module.PrevPageModule;
import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.account.service.UserInfoService;
import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.domain.CategoryLatelyPostDAO;
import com.pollra.web.function.category.service.CategoryService;
import com.pollra.web.function.comment.service.CommentService;
import com.pollra.web.function.data.service.DataResolver;
import com.pollra.web.function.page.PageVO;
import com.pollra.web.function.posts.domain.PostListDAO;
import com.pollra.web.function.posts.service.PostListService;
import com.pollra.web.function.posts.service.PostViewService;
import com.pollra.web.function.posts.service.PostsService;
import com.pollra.web.page.index.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class SignController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private PostListService postListService;
    private CategoryService categoryService;
    private UserInfoService userInfoService;
    private PostsService postsService;
    private PostViewService postViewService;
    private CommentService commentService;
    private DataResolver dataResolver;
    private PrevPageModule prevPageModule;

    public SignController(PostListService postListService, CategoryService categoryService, UserInfoService userInfoService, PostsService postsService, PostViewService postViewService, CommentService commentService, DataResolver dataResolver, PrevPageModule prevPageModule) {
        this.postListService = postListService;
        this.categoryService = categoryService;
        this.userInfoService = userInfoService;
        this.postsService = postsService;
        this.postViewService = postViewService;
        this.commentService = commentService;
        this.dataResolver = dataResolver;
        this.prevPageModule = prevPageModule;
    }

    @RequestMapping("signup")
    public ModelAndView signupPage(HttpServletRequest request){
        ModelAndView view = new ModelAndView("user/signup");
        view = prevPageModule.BasicFunctionBody(request, view);
        return view;
    }

    @RequestMapping("signout")
    public ModelAndView signout(HttpServletRequest request){
        ModelAndView view = new ModelAndView("user/signout");
        view = prevPageModule.BasicFunctionBody(request, view);
        return view;
    }

    @RequestMapping(value = "signupok", method = RequestMethod.POST)
    public ModelAndView signupok(HttpServletRequest request){
        ModelAndView view = new ModelAndView("user/signupok");
        view = prevPageModule.BasicFunctionBody(request, view);
        view = prevPageModule.setSignupFunction(request, view);
        return view;
    }

    @RequestMapping(value = "signin", method = RequestMethod.GET)
    public ModelAndView signin(HttpServletRequest request){
        return prevPageModule.pageBasicFunction("user/signin",request);
    }

    @RequestMapping(value = "signinok", method = RequestMethod.POST)
    public ModelAndView signinok(HttpServletRequest request){
        HttpSession session = request.getSession();
        System.out.println(request.getParameter("user_id"));
        String requestUserId = request.getParameter("user_id").trim();
        String requestUserPassword = request.getParameter("user_password").trim();
        ModelAndView view = null;
        UserInfoDto userInfoCheck = userInfoService.getUserInfoToUserId(requestUserId);

        if(userInfoCheck != null){

            if(userInfoCheck.getUserPw().equals(requestUserPassword)){
                CategoryDto[] categoryArray = categoryService.selectCategoryToUserNum(userInfoCheck.getUserNum());
                PageVO pageVO = null;
                try {
                    pageVO = postListService.getPageVOToUserInfoDto(userInfoCheck);
                } catch (Exception e) {
                    pageVO = new PageVO("guest","IoT","4");
                    e.printStackTrace();
                }
                session.setAttribute("userNik", userInfoCheck.getUserId());


                view = new ModelAndView("/"+pageVO.getPrevPageNik()
                                                +"/"+pageVO.getPageCategory()
                                                +"/"+pageVO.getPagePostNum());
                // 카테고리 처리
                CategoryLatelyPostDAO[] latelyPost = categoryService
                        .selectJoinLatelyPostsToUserNum(userInfoCheck.getUserNum());
                view.addObject("subCategories", dataResolver.goPageCategoryList(latelyPost, categoryArray));
            }else{
                view = new ModelAndView("user/signinokd");
            }
        }else{
            view = new ModelAndView("user/signinoks");
        }

        return view;
    }

    private ModelAndView getModelAndView(HttpServletRequest request, String s) {
        HttpSession session = request.getSession();
        String userNik = session.getAttribute("userNik").toString();
        int userNum_f = userNik.equals("guest") ?
                0 : userInfoService.getUserInfoToUserNik(userNik).getUserNum();
        ModelAndView view;
        String prevPage = request.getHeader("Referer");
        System.out.println(prevPage);
        PageVO pageInfo = new PageVO("guest", "", "");
        if (userNum_f == 0) {
            view = new ModelAndView(s);
            view.addObject("pageInfo", pageInfo);

            CategoryDto[] categories = categoryService.selectCategoriesToLevel(0, 0);
            view.addObject("categories", categories);

            CategoryLatelyPostDAO[] latelyPost = categoryService.selectJoinLatelyPosts();
            CategoryDto[] categoryList = categoryService.selectCategoryList();
            // 데이터 가공
            view.addObject("subCategories", dataResolver.goPageCategoryList(latelyPost, categoryList));

        } else {
            view = new ModelAndView("index");
        }
        return view;
    }
}
