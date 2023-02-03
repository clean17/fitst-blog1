package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {
    
    @GetMapping("/board/writeForm")
    public String writeForm(){
        return "/board/writeForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id){
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id){
        return "board/updateForm";
    }
}
