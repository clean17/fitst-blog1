package shop.mtcoding.blog.dto.board;

import lombok.Getter;
import lombok.Setter;

public class BoardReq {
    
    @Getter
    @Setter
    public static class BoardSaveReqDto{
        private int id;
        private String title;
        private String content;  // 문자라고 가정
    }

    @Getter
    @Setter
    public static class BoardUpdateRqeDto{
        private int id;
        private String title;
        private String content;
        private int userId;
    }
}
