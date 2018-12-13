package com.pollra.web.page.index;

import com.pollra.persistence.PostsRepository;
import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.account.service.UserInfoService;
import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.domain.CategoryLatelyPostDAO;
import com.pollra.web.function.category.service.CategoryService;
import com.pollra.web.function.comment.domain.CommentDAO;
import com.pollra.web.function.comment.domain.CommentDto;
import com.pollra.web.function.comment.service.CommentService;
import com.pollra.web.function.data.service.DataResolver;
import com.pollra.web.function.page.PageVO;
import com.pollra.web.function.posts.domain.PostListDAO;
import com.pollra.web.function.posts.domain.PostViewDAO;
import com.pollra.web.function.posts.domain.PostsDto;
import com.pollra.web.function.posts.service.PostListService;
import com.pollra.web.function.posts.service.PostViewService;
import com.pollra.web.function.posts.service.PostsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private PostListService postListService;
    private CategoryService categoryService;
    private UserInfoService userInfoService;
    private PostsService postsService;
    private PostViewService postViewService;
    private CommentService commentService;
    private DataResolver dataResolver;

    public MainController(PostListService postListService, CategoryService categoryService, UserInfoService userInfoService, PostsService postsService, PostViewService postViewService, CommentService commentService, DataResolver dataResolver) {
        this.postListService = postListService;
        this.categoryService = categoryService;
        this.userInfoService = userInfoService;
        this.postsService = postsService;
        this.postViewService = postViewService;
        this.commentService = commentService;
        this.dataResolver = dataResolver;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(HttpServletRequest request, ModelAndView View) throws IOException {
        HttpSession userNik = request.getSession();
        log.info("[in] user: "+ userNik.getAttribute("userNik")+"/ page: "+"index");
        return "redirect:/guest/IoT/4";
    }

    @GetMapping("error/pageNotFound")
    public String pageNotFound(){
        return "error/pageNotFound";
    }

    @GetMapping("/{pageNik}/{categoryName}/{postNum}")
    public ModelAndView userPage(@PathVariable String pageNik,
                                 @PathVariable String categoryName,
                                 @PathVariable String postNum,
                                 HttpServletRequest request){
        ModelAndView view = new ModelAndView("index");
        HttpSession session = request.getSession();



        UserInfoDto userInfo = null;
        int categoryNum = 0;

        log.info("접속한 유저: "+session.getAttribute("userNik"));
        log.info("접속한 페이지: /"+pageNik+"/"+categoryName+"/"+postNum);
        try{
            userInfo = userInfoService.getUserInfoToUserNik(pageNik.toLowerCase());
//            log.info("1" + userInfo); // NULL 뜸.. ㅇㅅㅇ시발
            categoryNum = categoryService.selectNumToName(categoryName)[0].getCategoryNum();
            log.info("2" + categoryNum);
        }catch (Exception e){
            log.info("에러. 정보가 확실하지 않음.");
            view = new ModelAndView("error/pageNotFound");
            e.printStackTrace();
        }

        if(userInfo != null && categoryNum != 0) {
            view.addObject("userNik",session.getAttribute("userNik").toString());
            view.addObject("pageNik",pageNik);
            PageVO pageInfo = new PageVO(pageNik,categoryName, postNum);
            view.addObject("pageInfo",pageInfo);

            CategoryDto[] categories = categoryService.selectCategoriesToLevel(userInfo.getUserNum(), 0);
            view.addObject("categories", categories);
            log.info("Categories 불러오기 성공 \n postList 불러오기 시도..." + categoryName);

            PostListDAO[] postLists = postListService.getPostListToCategoryName(categoryName);
            view.addObject("postLists", postLists);
            log.info("postList 불러오기 성공 \n postView 불러오기 시도..."+ userInfo.getUserNum()+" "+ categoryNum);

            PostViewDAO[] postView = postViewService.selectAllPostView(userInfo.getUserNum(), categoryNum, Integer.parseInt(postNum));
            view.addObject("postView", postView[0]);
            System.out.println("postView 불러오기 성공 \n Comments 불러오기 시도..." + postView[0].getPostNum());

            CommentDAO[] comments = commentService.selectArrayToPostNum(postView[0].getPostNum());
            view.addObject("comments", comments);
            System.out.println("Comments 불러오기 성공");

            int postCommentCount = commentService.selectOneToPostNum(postView[0].getPostNum());
            view.addObject("postCommentCount", postCommentCount);

            CategoryLatelyPostDAO[] latelyPost = categoryService.selectJoinLatelyPosts();
            // 데이터 가공
            CategoryDto[] categoryList = categoryService.selectCategoryList();
            view.addObject("subCategories", dataResolver.goPageCategoryList(latelyPost, categoryList));

            // 페이지이름별로 데이터 이름 바꿔서 전송

        }else{
            System.out.println("[!] 치명적 오류. 페이지가 존재하지 않음");
        }
        return view;
    }
}