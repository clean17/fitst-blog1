package shop.mtcoding.blog.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardReq {
    
    @Getter
    @Setter
    public static class BoardReqDto{
        private int id;
        private String title;
        private String content;  // 문자라고 가정
    }
}
