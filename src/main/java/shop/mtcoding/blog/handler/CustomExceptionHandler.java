package shop.mtcoding.blog.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.util.Script;

@RestControllerAdvice
public class CustomExceptionHandler {
    // 익셉션 전용 컨트롤러를 만들자

    @ExceptionHandler(CustomException.class)  // 런타임 익셉션은 다 낚아채니까 자식 익셉션으로 만든다.. 내가 정의한 런타임 익셉션만 데려올거야
    public String customException(CustomException e){
        // return e.getMessage();  // rest클래스 내부에 있으니까 메세지 리턴
        return Script.back(e.getMessage());
    }

}
