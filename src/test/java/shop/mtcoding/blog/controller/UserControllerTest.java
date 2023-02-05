package shop.mtcoding.blog.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// 매번 로그인할 필요없이.. 포스트맨 날릴필요없이 여기서 테스트하자 !!!!!!!!!!!
// 테스트할때 세팅이 필요해서 시간이 필요하면 이 방법을 사용해

// 스프링부트테스트 - 메모리에 다 띄우고 테스트
@WebMvcTest(UserController.class)  // 컨트롤러까지 가능 필요한것만 가져감
public class UserControllerTest {
    
    @Autowired
    private MockMvc mvc; // 테스트에 사용

    @Test
    public void join_test(){
        // requestBody - 쿼리스트링으로 만들어서 content 넣고
        // 미디어타입을 content타입에 넣고
        // 폼으로 받았으니까 

        // mvc.perform(post(urlTemplate:"/join").content("").contentType(""))
        // 목의 메소드....

        // given /when /then

    }


}
