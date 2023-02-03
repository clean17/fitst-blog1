package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    
    @GetMapping("/board/writeForm")
    public String writeForm(){
        return "/board/writeForm";
    }
}
