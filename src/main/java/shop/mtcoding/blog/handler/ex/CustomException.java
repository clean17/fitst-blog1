package shop.mtcoding.blog.handler.ex;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    // 만든의도는 ?

    // e.getMessage() 가 되는 이유 Throwable 의 디폴트 메세지를 가져온다.

    private HttpStatus status;

    public CustomException(String msg, HttpStatus status){
        super(msg);  // 메세지는 부모가 처리하고 
        this.status = status; // 경우에 따른 응답코드를 다르게 주고 싶어서 생성자의 파라미터로 추가했다.
    }

    public CustomException(String msg){
        this(msg, HttpStatus.BAD_REQUEST);
        // 파라미터를 모두 받는 생성자를 만들어 놨으므로 this를 사용해서 가장 긴 파라미터를 받는 생성자를 호출
    }
}
