package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import shop.mtcoding.blog.model.User;

// // 매번 로그인할 필요없이.. 포스트맨 날릴필요없이 여기서 테스트하자 !!!!!!!!!!!
// // 테스트할때 세팅이 필요해서 시간이 필요하면 이 방법을 사용해

// // 스프링부트테스트 - 메모리에 다 띄우고 테스트
// @WebMvcTest(UserController.class)  // 컨트롤러까지 가능 필요한것만 가져감
// public class UserControllerTest {

//     @Autowired
//     private MockMvc mvc; // 테스트에 사용

//     @Test
//     public void join_test(){
//         // requestBody - 쿼리스트링으로 만들어서 content 넣고
//         // 미디어타입을 content타입에 넣고
//         // 폼으로 받았으니까 

//         // mvc.perform(post(urlTemplate:"/join").content("").contentType(""))
//         // 목의 메소드....

//         // given /when /then

//     }

/* 
 * SpringBootTest는 통합테스트 ( 실제 환경과 동일하게 Bean이 생성됨 )// 독립적으로 테스트 -> Mock ( 가짜 환경에 IoC 컨테이너 테스트 )
 * AutoConfigureMockMvc 는 mock 환경의 IoC 컨테이너에 MockMvc Bean 이 생성됨
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc; // @AutoConfigureMockMvc 가 DI 를 해줌

    @Test
    public void join_test() throws Exception {
        // given
        String requestBody = "username=cos&password=1234&email=cos@nate.com"; // 이렇게 넘기면 스프링의 기본 파싱전락에 의해서 key=value 형태로 메소드가 받아 먹음

        // when
        ResultActions resultActions = mvc.perform(post("/join").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        // then
        resultActions.andExpect(status().is3xxRedirection());
    }

    @Test
    public void login_test() throws Exception {
        // given
        String requestBody = "username=ssar&password=1234";

        // when
        ResultActions resultActions = mvc.perform(post("/login").content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)); // content 를 넣으면 타입이 필요해

        HttpSession session = resultActions.andReturn().getRequest().getSession();
        // resultActions 이 응답의 결과를 다 들고 있다.
        User principal = (User) session.getAttribute("principal");
        // System.out.println(" 왜 안나오는데 " + principal.getUsername());

        // then
        assertThat(principal.getUsername()).isEqualTo("ssar");
        resultActions.andExpect(status().is3xxRedirection());
    }
}