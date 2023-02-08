package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateRqeDto;
import shop.mtcoding.blog.dto.board.BoardResp;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.User;

// @WebMvcTest(UserController.class)  // 컨트롤러까지 가능 필요한것만 가져감

/* 
 * SpringBootTest는 통합테스트 ( 실제 환경과 동일하게 Bean이 생성됨 )// 독립적으로 테스트 -> Mock ( 가짜 환경에 IoC 컨테이너 테스트 )
 * AutoConfigureMockMvc 는 mock 환경의 IoC 컨테이너에 MockMvc Bean 이 생성됨
 */

 @Transactional // 메소드 실행 직후 롤백 !!!! / 서비스의 트랜잭션과 다르다
// 단점 auto_increment 초기화가 안된다.
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
        // user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

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
        resultActions.andExpect(status().isOk());
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
    
        int id = 1;
        ResultActions resultActions = mvc.perform(get("/board/"+id+"/updateForm")
        .session(mockSession));
        Board b = (Board)resultActions.andReturn().getModelAndView().getModel().get("board");
        assertThat(b.getId()).isEqualTo(1);

        // resultActions.andExpect(status().isOk());
    }

    @Test
    public void boardUpdate_test() throws Exception{
        // String requestBody = "title=&content=&userId=1245";
        //given
        int id=1;
        BoardUpdateRqeDto bu = new BoardUpdateRqeDto();
        bu.setTitle("제목33333333333333");
        bu.setContent("ㄹㄷㄹㄷ미ㅏ디");
        System.out.println(om.writeValueAsString(bu));;
        String a = om.writeValueAsString(bu);
        
        ResultActions resultActions = mvc.perform(put("/borad/"+id)
                                         .content(a)
                                         .contentType(MediaType.APPLICATION_JSON_VALUE)
                                         .session(mockSession)
                                         );
                                    
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.code").value(1));
    }
}