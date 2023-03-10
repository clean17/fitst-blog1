package shop.mtcoding.blog.dto.board;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

public class BoardResp {

    @Getter
    @Setter
    public static class BoardMainRespDto {
        private int id;
        private String title;
        private String username;
        private String thumbnail;
    }

    @Getter
    @Setter
    public static class BoardDto {
        private int id;
        private String title;
        private String content;
        private String username;
        private Timestamp createdAt;
    }

    @Getter
    @Setter
    public static class BoardDetailRespDto {
        private int id;
        private String title;
        private String content;
        private String username;
        private int userId;
        private Timestamp createdAt;
    }
}
