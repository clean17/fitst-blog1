package shop.mtcoding.blog.dto.user;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import lombok.Getter;
import lombok.Setter;

public class UserReq {
    
    @Getter
    @Setter
    public static class JoinReqDto{
        private String username;
        private String password;
        private String email;
    }

    @Getter
    @Setter
    public static class UpdateReqDto{
        private int id;
        private String password;
        private String email;
    }
    // @Getter
    // @Setter
    // public static class UsernameCheckDto{
    //     private Map<String, Object> param;
    //     private String username = param.get("username").toString();
    // }
}
