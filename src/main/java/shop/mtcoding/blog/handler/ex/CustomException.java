package shop.mtcoding.blog.handler.ex;

public class CustomException extends RuntimeException{
    // 만든의도는 ?
    public CustomException(String msg){
        super(msg);
    }
}
