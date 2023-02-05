package shop.mtcoding.blog.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class TestController {
    
    @Test
    public void parse_test() {
            // given
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            System.out.println(timestamp);
            LocalDateTime nowTime = timestamp.toLocalDateTime();
            System.out.println(nowTime);
            String nowStr = nowTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            System.out.println(nowStr);  // 2023-01-20 
    }
}
