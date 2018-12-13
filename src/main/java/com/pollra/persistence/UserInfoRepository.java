package com.pollra.persistence;

import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.account.domain.UserOverlap;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Repository
public interface UserInfoRepository {
    @Select("SELECT *" +
            " FROM pollra_userinfo" +
            " WHERE user_nik = #{param1}")
    public UserInfoDto selectUserInfoToUserNik(String userNik);

    @Select("SELECT *" +
            " FROM pollra_userinfo" +
            " WHERE user_id = #{param1}")
    public UserInfoDto selectUserInfoToUserId(String userId);

    @Select("SELECT *" +
            " FROM pollra_userinfo" +
            " WHERE user_id = #{param1}" +
            " AND user_pw = #{param2} ")
    public UserInfoDto selectUserInfoToUserIdAndUserPw(String userId, String userPw);

    @Select("SELECT pollra_userinfo.user_num" +
            " FROM pollra_userinfo" +
            " ORDER BY user_num DESC")
    public int[] selectLastUserNum();

    @Select("SELECT * FROM pollra_userinfo ORDER BY user_num DESC")
    public UserInfoDto[] selectAllUserInfoList();

    @Select("SELECT user_id, user_nik" +
            " FROM pollra_userinfo" +
            " WHERE user_id = #{param1} OR user_nik = #{param2}")
    public UserOverlap[] selectUserIdOrUserNikOverlapCheck(String userId, String userNik);

    @Transactional
    @Insert("INSERT INTO pollra_userinfo" +
            " VALUES (#{userNum},#{userId},#{userPw},#{userNik},#{userEmail},#{userIp})")
    public int insertOneUser(UserInfoDto inputUserInfo);
}
