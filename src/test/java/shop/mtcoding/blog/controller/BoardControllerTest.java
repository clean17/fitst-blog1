package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardResp;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateRqeDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
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


    /// 여기부터 오늘

    @Autowired
    private ObjectMapper om;

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
    public void main_test() throws Exception{
        //given        
        //when
            ResultActions resultActions = mvc.perform(get("/"));

            Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
            List<BoardResp.BoardMainRespDto> dtos = (List<BoardResp.BoardMainRespDto>) map.get("boardList");

            String responsebody = om.writeValueAsString(dtos);
            System.out.println("테스트 " + responsebody);
        //then
        // resultActions.andExpect(status().isOk());
        assertThat(dtos.size()).isEqualTo(6);
        assertThat(dtos.get(0).getId()).isEqualTo(1);
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

    @Test
    public void detail_test() throws Exception{        
        int responseBody = 1;

        ResultActions resultActions = mvc.perform(get("/board/"+responseBody));
        // resultActions.andExpect(status().isOk());
        Map<String, Object> map = resultActions.andReturn().getModelAndView().getModel();
        BoardDetailRespDto ddd = (BoardDetailRespDto) map.get("dto");
        System.out.println("테스트 :" + om.writeValueAsString(ddd));
        assertThat(ddd.getId()).isEqualTo(1);
        assertThat(ddd.getUsername()).isEqualTo("ssar");
    }

    @Test
    public void boardDelete_test() throws Exception{
        // 지금 할수 있는 검증  인증안됐을대 익셉션 뜨는지 302가 뜨는지
        int id = 1;

        ResultActions resultActions = mvc.perform(delete("/board/"+id).session(mockSession));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : "+ responseBody); // 테스트 : {"code":1,"msg":"삭제 성공","data":true}
        resultActions.andExpect(status().isOk());

        // resultActions.andExpect(jsonPath("$.code").value(1)); // json 이 제대로 전송됐는지 테스트
    }
    @Test
    public void updateForm_test() throws Exception{
    
        int id = 442;
        ResultActions resultActions = mvc.perform(get("/board/442/updateForm").session(mockSession));
        BoardDetailRespDto b = (BoardDetailRespDto)resultActions.andReturn().getModelAndView().getModel().get("dto");
        assertThat(b.getId()).isEqualTo(1);

        // resultActions.andExpect(status().isOk());
    }

    @Test
    public void boardUpdate_test() throws Exception{
        String requestBody = "title=22&content=23&userId=1245";
        BoardUpdateRqeDto b = new BoardUpdateRqeDto();
        b.setId(1);
        b.setTitle("안녕");
        b.setContent("2345");
        String a = om.writeValueAsString(b);

        System.out.println(a);


        int id = 2;
        ResultActions resultActions = mvc.perform(post("/borad/"+id+"/update")
                                         .content(requestBody)
                                         .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                                         .session(mockSession));
                                    
        resultActions.andExpect(status().isOk());

    }

}

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