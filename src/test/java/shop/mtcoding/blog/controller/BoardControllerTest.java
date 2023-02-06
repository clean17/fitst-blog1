package shop.mtcoding.blog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
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
public class BoardControllerTest {

    @Autowired
    private MockMvc mvc; // @AutoConfigureMockMvc 가 DI 를 해줌

    private MockHttpSession mockSession;

    @BeforeEach  //  모든 test 메소드 실행 직전 마다 호출된다 ( 추가햇음 ) 이걸로 모든 테스트에 세션 주입
                // MOCK 세션 생성 - 이용
    public void setUp(){
        User user = new User();
        user.setId(1);
        user.setUsername("ssar");
        user.setPassword("1234");
        user.setEmail("ssar@nate.com");
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        mockSession = new MockHttpSession();
        mockSession.setAttribute("principal", user);
    }

    @Test
    public void save_test() throws Exception {
        // given
        // setUp();
        String requestBody = "title=제목1&content=내용1";

        // String title = "";
        // for (int i = 0; i < 12412 ; i++) {
        //     title += "가";
        // }

        // String requestBody = "title=" + title + "&content=내용1";
        // when
        ResultActions resultActions = mvc.perform(
                post("/board/Write")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(mockSession));            
        // then
        resultActions.andExpect(status().is3xxRedirection());
    }
}

// @AutoConfigureMockMvc
// @SpringBootTest(webEnvironment = WebEnvironment.MOCK)
// public class BoardControllerTest {

//     @Autowired
//     private MockMvc mvc; // @AutoConfigureMockMvc 가 DI 를 해줌

//     private MockHttpSession session;

//     @Test
//     public void save_test() throws Exception {
//         // given
//         User user = new User();
//         user.setId(1);
//         user.setUsername("ssar");
//         user.setPassword("1234");
//         user.setEmail("ssar@nate.com");
//         user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

//         session = new MockHttpSession();
//         session.setAttribute("principal", user);

//         // 그냥 실행하면 세션이 없어서 302를 리턴하지 못한다
//         String requestBody = "title=제목1&content=내용1"; // 이렇게 넘기면 스프링의 기본 파싱전락에 의해서 key=value 형태로 메소드가 받아 먹음

//         // when
//         ResultActions resultActions = mvc.perform(post("/board/Write")
//                 .content(requestBody)
//                 .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                 .session(session));
//         // then
//         resultActions.andExpect(status().is3xxRedirection());
//     }
// }


// @AutoConfigureMockMvc
// @SpringBootTest(webEnvironment = WebEnvironment.MOCK)
// public class BoardControllerTest {

//     @Autowired
//     private MockMvc mvc;

//     @Test
//     public void save_test() throws Exception {
//         // given
//         User user = new User();
//         user.setId(1);
//         user.setUsername("ssar");
//         user.setPassword("1234");
//         user.setEmail("ssar@nate.com");
//         user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

//         MockHttpSession session = new MockHttpSession();
//         session.setAttribute("principal", user);

//         String requestBody = "title=제목1&content=내용1";

//         // 제목이나 내용을 안넣으면 400 에러

//         // when
//         ResultActions resultActions = mvc.perform(
//                 post("/board/Write")
//                         .content(requestBody)
//                         .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
//                         .session(session));
//                         // .session(session)); 안 넣으면 세션이 없어서 401에러

//         // then
//         resultActions.andExpect(status().is3xxRedirection());
//     }
// }