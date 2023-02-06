package shop.mtcoding.blog.dto.reply;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDto {
    private int id;
    private String body;
    private int boardId;
    private String username;
    private Timestamp createdAt;
}
