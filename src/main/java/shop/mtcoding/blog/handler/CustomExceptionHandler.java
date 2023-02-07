package shop.mtcoding.blog.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.mtcoding.blog.dto.board.ResponseDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
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
        return new ResponseEntity<>(Script.back(e.getMessage()), e.getStatus());
        // 동일한 유저네임일때는 상태코드 400을 뜨게해야한다
        // ResponseEntity 는 데이터 리턴.. Rest 밑에서 
        // ResponseEntity 는 2개의 인수를 필요로 한다.
        // 400번대가 뜨면 클라이언트의 잘못.. 
    }

    @ExceptionHandler(CustomApiException.class)  // ajax 요청일때는 fail일때도 json 을 리턴해야하기 때문에 이걸 만든다..
    // 그러니까 delete 요청의 모든 익셉션 핸들러는 json 을 리턴하니까 이걸로 바꿔야 한다
    public ResponseEntity<?> CustomApiException(CustomApiException e){                
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage() ,null), e.getStatus());
    }
}

