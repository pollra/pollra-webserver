package com.pollra.web.function.account.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserOverlap {
    private String userId;
    private String userNik;
}
