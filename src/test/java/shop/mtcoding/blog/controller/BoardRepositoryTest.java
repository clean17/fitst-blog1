package shop.mtcoding.blog.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.blog.dto.board.BoardResp.BoardMainRespDto;
import shop.mtcoding.blog.model.BoardRepository;

@MybatisTest // 라이브러리 추가 MyBatis만 테스트할 경우
public class BoardRepositoryTest {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void findAllWithUser_test() throws Exception {
        // 앞단의 필요한 빈들을 만들지 않고 레파지토리 이하의 빈만 생성해서 테스트함
        //given
        ObjectMapper om = new ObjectMapper();

        //when
        List<BoardMainRespDto> BoardMainRespDto = boardRepository.findAllWithUser();
        // 필터도 없어서 세션이 필요가 없다 
        System.out.println("테스트 : "+ BoardMainRespDto.size());  // 이것보다 더 좋은방법은 아래
        // BoardMainRespDto.stream().map(e->{b})
        String responseBody = om.writeValueAsString(BoardMainRespDto); // 이녀석이 json 으로 변환해줌
        System.out.println("테스트 : "+ responseBody);
        //then
        assertThat(BoardMainRespDto.get(5).getUsername()).isEqualTo("love"); //  통과
    }
}
