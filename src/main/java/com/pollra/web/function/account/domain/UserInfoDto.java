package com.pollra.web.function.account.domain;

import lombok.*;
import org.hibernate.validator.internal.util.stereotypes.Immutable;
import org.springframework.data.annotation.Id;

import javax.annotation.processing.Generated;

@Data
@Setter
@Getter
@AllArgsConstructor
@ToString
public class UserInfoDto {
    private int userNum;
    private String userId;
    private String userPw;
    private String userNik;
    private String userEmail;
    private String userIp;
}
