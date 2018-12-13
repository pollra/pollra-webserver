package com.pollra.test;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TestRespository {
    @Select("select * from testdata where idx = #{idx}")
    TestDto getOneTestData(int idx);
}
