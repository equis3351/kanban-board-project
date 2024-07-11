package com.sparta.kanbanboardproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//        (exclude =  SecurityAutoConfiguration.class )//시큐리티 제외
public class KanbanBoardProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(KanbanBoardProjectApplication.class, args);
    }

}
