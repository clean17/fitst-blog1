package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ResponseDto;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.util.Script;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;
    
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

    @PostMapping("/boardWrite")
    @ResponseBody
    public String boardWrite(String title, String content){
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            // return new ResponseDto<>(1, "로그인 풀림",false);
            return Script.href("errorpage");
        }
        int result = boardRepository.insertBoard(title, content, principal.getId());
        if ( result != 1 ){
            // return new ResponseDto<>(1, "글 쓰기 실패",false);
            return Script.href("errorpage");
        }else{
            // return new ResponseDto<>(1, "글 쓰기 성공",true);            
            return Script.hrefAlert("글 쓰기 성공","/");
        }
    }
}
