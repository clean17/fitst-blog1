package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.model.ResponseDto;

@Controller
public class ReplyController {
    

    @PutMapping("/reply/insert")
    public @ResponseBody ResponseDto<?> replyInsert(){
        
        return new ResponseDto<>(0, null, null);
    }
}
