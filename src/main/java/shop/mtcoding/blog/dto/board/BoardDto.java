package shop.mtcoding.blog.dto.board;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private int id;
    private String title;
    private String body;
    private String username;
    private Timestamp createdAt;
}
