package com.pollra.web.function.account.service;

import com.pollra.persistence.UserInfoRepository;
import com.pollra.web.function.account.domain.UserInfoDto;
import com.pollra.web.function.account.domain.UserOverlap;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserInfoService {
    private UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoDto getUserInfoToUserNik(String userNik){
        return userInfoRepository.selectUserInfoToUserNik(userNik);
    }

    public UserInfoDto getUserInfoToUserId(String userId){
        return userInfoRepository.selectUserInfoToUserId(userId);
    }

    public UserInfoDto getUserInfoToUserIdANDUserPw(String userId, String userPw){return userInfoRepository.selectUserInfoToUserIdAndUserPw(userId,userPw); }

    public int[] getLastUserNum(){ return userInfoRepository.selectLastUserNum();}

    public UserInfoDto[] getUserinfoList(){return userInfoRepository.selectAllUserInfoList();}

    public UserOverlap[] getOverlapCheck(String userId, String userNik){return userInfoRepository.selectUserIdOrUserNikOverlapCheck(userId,userNik);}

    public int insertOneUser(UserInfoDto inputUserInfo) {return userInfoRepository.insertOneUser(inputUserInfo);

    }
}
