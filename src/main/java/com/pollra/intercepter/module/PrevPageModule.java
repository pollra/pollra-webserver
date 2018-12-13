package com.pollra.intercepter.module;

import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.account.domain.UserOverlap;
import com.pollra.web.function.account.service.UserInfoService;
import com.pollra.web.function.category.domain.CategoryDto;
import com.pollra.web.function.category.domain.CategoryLatelyPostDAO;
import com.pollra.web.function.category.service.CategoryService;
import com.pollra.web.function.comment.service.CommentService;
import com.pollra.web.function.data.service.DataResolver;
import com.pollra.web.function.page.PageVO;
import com.pollra.web.function.posts.domain.PostsDto;
import com.pollra.web.function.posts.domain.PostsInfoDto;
import com.pollra.web.function.posts.service.PostListService;
import com.pollra.web.function.posts.service.PostViewService;
import com.pollra.web.function.posts.service.PostsService;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.mybatis.spring.transaction.SpringManagedTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

@Component
public class PrevPageModule {
    private static final Logger log = LoggerFactory.getLogger(PrevPageModule.class);
    private UserInfoService userInfoService;
    private CategoryService categoryService;
    private CommentService commentService;
    private PostsService postsService;
    private PostViewService postViewService;
    private PostListService postListService;
    private DataResolver dataResolver;

    public PrevPageModule(UserInfoService userInfoService, CategoryService categoryService, CommentService commentService, PostsService postsService, PostViewService postViewService, PostListService postListService, DataResolver dataResolver) {
        this.userInfoService = userInfoService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.postsService = postsService;
        this.postViewService = postViewService;
        this.postListService = postListService;
        this.dataResolver = dataResolver;
    }

    /**
     * 페이지별로 사용해야하는 기능 정리
     * pageVO : 접속 시도한 페이지
     * session : 로그인여부 확인을 위함
     *
     */

    public ModelAndView pageBasicFunction(String pageUri, HttpServletRequest request) {
        ModelAndView view = new ModelAndView(pageUri);
        BasicFunctionBody(request, view);
        return view;
    }

    public ModelAndView BasicFunctionBody(HttpServletRequest request, ModelAndView view) {
        /**
         * 현재 접속중인 유저의 닉네임.
         * 인터셉터에서 유저 이름이 null 일 경우 guest값을 대입
         * 값은 반드시 존재한다.
         */
        // 접속한 유저의 닉네임을 가져옴
        String userNik = request.getSession().getAttribute("userNik").toString();
        int userNum_f = userNik.equals("guest") ?
                0 : userInfoService.getUserInfoToUserNik(userNik).getUserNum();
        UserInfoDto userInfoDto = userInfoService.getUserInfoToUserNik(userNik);
        if (userInfoDto != null) {
            setCategoriesAndPageInfo(userInfoDto.getUserNum(), view);
            // 카테고리 데이터 넘기기 끝
//            setSignupFunction(request, view);
//            setPostingList(view, pageVO);
        }else{
            log.info("입력하신 페이지의 정보가 확실하지 않습니다.");
        }
        return view;
    }


    /**
     * PageVO 객체를 받아서 NULL인지 확인한 후 객체를 만들고 리턴한다.
     * @param pageVO
     * @return
     */
    public PageVO getPageVo(PageVO pageVO) {
        if(pageVO.isNull()){
            log.info("[!]pageBasicFunction : "+"pageVO is NULL set default");
            new PageVO("guest","IoT","4");
        }
        return pageVO;
    }

    /**
     * 페이지에 표시할 데이터를 셋팅하고 view 로 보내는 메소드
     * 유저 번호를 기준으로 categories 와 subCategories를 만들고
     * 데이터를 ModelAndView 객체로 넘긴다.
     *
     * [!] 이 메소드는 동적 페이지에서는 사용할 수 없음
     *
     * @param view
     * @param userNum
     */
    public ModelAndView setCategoriesAndPageInfo(int userNum, ModelAndView view) {
        // 기본적으로 모든 페이지에 존재하는 기능은 카테고리
        // 카테고리는 일반 카테고리와 서브카테고리가 존재
        // 유저 번호가 필요함

        // 기본적으로는 게스트로 페이지가 로딩되지만 로그인 했을때는 아님
        // 로그인 된 유저의 정보를 기준으로 페이지를 로딩함

        CategoryDto[] categoryParent=categoryService.selectCategoriesToLevel(userNum,0);
        CategoryLatelyPostDAO[] latelyPost = categoryService.selectJoinLatelyPostsToUserNum(userNum);
        CategoryDto[] categoryList = categoryService.selectCategoryToUserNum(userNum);
        // 카테고리 데이터를 넘김
        view.addObject("categories", categoryParent);
        view.addObject("subCategories", dataResolver.goPageCategoryList(
                latelyPost
                , categoryList
        ));
        dataResolver.goPageCategoryList(latelyPost, categoryParent);
        view.addObject("pageInfo", new PageVO("guest","",""));
        return view;
    }

    public ModelAndView setSignupFunction(HttpServletRequest request, ModelAndView view){
        // 회원 정보를 받음
        // 아래에서 쓸 유저 넘버를 정한다. 모든 유저의 넘버를 조회. 마지막넘버에서 +1 한다.
        int lastUserNum = userInfoService.getLastUserNum()[0];

        // 유저 리스트를 가져온다
        UserInfoDto[] userInfoList = userInfoService.getUserinfoList();

        // 받은 회원 정보를 검사.. 는 나중에
        UserInfoDto inputUserInfo = new UserInfoDto(
                lastUserNum+1,
                        request.getParameter("id"),
                        request.getParameter("password"),
                        request.getParameter("nik"),
                        request.getParameter("email"),
                        request.getRequestURI()
        );
        // 중복체크
        UserOverlap[] overLapCheck = userInfoService.getOverlapCheck(inputUserInfo.getUserId(), inputUserInfo.getUserNik());
        log.info("회원가입 판별데이터: "+ overLapCheck.length + "/ 입력된 값: "+inputUserInfo.getUserId()+"/"+inputUserInfo.getUserNik());
        if(overLapCheck.length==0) {
            log.info("회원가입 처리 시작");
            // 인설트 처리
            int insertResult = userInfoService.insertOneUser(inputUserInfo);

            // 다른 베이스셋팅(기본포스트, 기본유저정보 등)
            // Default Category Setting

            CategoryDto defaultCategory = new CategoryDto(
                    inputUserInfo.getUserNum(),
                    "DefaultCategory",
                    0
            );
            CategoryDto subDefaultCategory = new CategoryDto(
                    inputUserInfo.getUserNum(),
                    "subCategory",
                    1
            );

            categoryService.insertDefaultCategory(defaultCategory);
            categoryService.insertDefaultCategory(subDefaultCategory);
            CategoryDto[] categoryDtos = categoryService.selectCategoryToUserNum(inputUserInfo.getUserNum());

            // Default Posting Setting(postsinfo 자동 생성)
            PostsDto defaultPosting = new PostsDto(
                    inputUserInfo.getUserNum(),
                    categoryDtos[0].getCategoryNum(),
                    "안녕하세요!",
                    " 가입을 축하합니다! <br/>",
                    new Timestamp(System.currentTimeMillis())
            );

            postsService.insertDefaultPosting(defaultPosting);
            log.info("넘어가는 데이터: "+inputUserInfo.getUserNum() +"/"+ categoryDtos[0].getCategoryNum());
            PostsDto postsDto = postsService.selectOnePostToUsernumCategorynum(inputUserInfo.getUserNum(), categoryDtos[0].getCategoryNum());
            PostsInfoDto defaultPostInfo = new PostsInfoDto(postsDto.getPostsNum(), postsDto.getPostsDate());
            postsService.insertDefaultPostsInfo(defaultPostInfo);

        }else{
            // 어떤게 중복인지 처리
            log.info("...ㅇㅂㅇ!! 냄새나는 중복이다");
            boolean userIdOverLap = false;
            boolean userNikOverLap = false;

            for(UserOverlap overlap : overLapCheck ){
                if(overlap.getUserId().equals(inputUserInfo.getUserId())) userIdOverLap = true;
                if(overlap.getUserNik().equals(inputUserInfo.getUserNik())) userNikOverLap = true;
            }
            log.info("가입실패 페이지로 보내는넘: "+userIdOverLap+"/"+userNikOverLap);
            if(userIdOverLap == true && userNikOverLap == false){
                log.info("id 중복 가입실패. 가입실패 페이지로");
                view = new ModelAndView("/error/signuperrorid");
                view.addObject("errorMsg","");
            }
            if(userIdOverLap == false && userNikOverLap == true) {
                log.info("Nik 중복 가입실패. 가입실패 페이지로");
                view = new ModelAndView("/error/signuperrornik");
                view.addObject("errorMsg","");
            }
            if(userIdOverLap == true && userNikOverLap == true){
                log.info("둘다중복 가입실패. 가입실패 페이지로");
                view = new ModelAndView("/error/signuperroridnik");
                view.addObject("errorMsg","");
            }
        }
        return view;
    }

    public ModelAndView setPostingFunction(HttpServletRequest request, ModelAndView view){

        return view;
    }

}
