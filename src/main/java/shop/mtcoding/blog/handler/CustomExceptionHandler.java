package shop.mtcoding.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.util.Script;

@RestControllerAdvice
public class CustomExceptionHandler {
    // 익셉션 전용 컨트롤러를 만들자

    @ExceptionHandler(CustomException.class)  // 런타임 익셉션은 다 낚아채니까 자식 익셉션으로 만든다.. 내가 정의한 런타임 익셉션만 데려올거야
    public ResponseEntity<?> customException(CustomException e){
        
        // return e.getMessage();  // rest클래스 내부에 있으니까 메세지 리턴
        // return Script.back(e.getMessage());

        // ResponseEntity 는 상태코드를 같이 전달할 수 있다. 제네릭에 바디데이터를 넣는다
        return new ResponseEntity<>(Script.back(e.getMessage()), HttpStatus.BAD_REQUEST);
        // 동일한 유저네임일때는 상태코드 400을 뜨게해야한다
        // ResponseEntity 는 데이터 리턴.. Rest 밑에서 
        // ResponseEntity 는 2개의 인수를 필요로 한다.
        // 400번대가 뜨면 클라이언트의 잘못.. 
    }

}

