package com.pollra.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sound.sampled.AudioFormat;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class DbConnectionTest {
    private TestRespository testRespository;

    public DbConnectionTest(TestRespository testRespository) {
        this.testRespository = testRespository;
    }

    @GetMapping("/{idx}")
    public String testMethod1(@PathVariable int idx){
        String value = testRespository.getOneTestData(idx).getValue();
        System.out.println("value: "+value);
        return value;
    }
}
