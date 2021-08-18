package com.learnspringboot.learnbackenddemo1;

import com.learnspringboot.learnbackenddemo1.dao.PostDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class LearnBackEndDemo1Application {

    public static void main(String[] args) {
        SpringApplication.run(LearnBackEndDemo1Application.class, args);
    }

}
