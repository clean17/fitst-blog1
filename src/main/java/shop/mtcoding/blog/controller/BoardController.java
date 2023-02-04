package shop.mtcoding.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.board.BoardDto;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ResponseDto;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String main(Model model){
        User principal = userRepository.findByUsernameAndPassword("ssar", "1234");
        session.setAttribute("principal", principal);
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);    
        return "user/main" ;
    }
    
    @GetMapping("/board/writeForm")
    public String writeForm(){
        return "/board/writeForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model){
        BoardDto board = boardRepository.findById(id);
        if ( board == null ){
            return "redirect:/errorpage";
        }
        model.addAttribute("board", board);
        return "board/detail";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        BoardDto board = boardRepository.findById(id);
        if ( board == null ){
            return "redirect:/errorpage";
        }
        model.addAttribute("board", board);
        return "board/updateForm";
    }
   
    @PutMapping("/borad/{id}/update")
    @ResponseBody
    public ResponseDto<?> boardUpdate(@PathVariable int id, @RequestBody Map<String, Object> param){
        // System.out.println("파람 : "+ param);
        String title = param.get("title").toString();
        String content = param.get("content").toString();
        // System.out.println("파람 : "+ title);
        // System.out.println("파람 : "+ content);
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>( -1, "로그인 풀림",false);
            // return Script.href("errorpage");
        }
        int result = boardRepository.updateBoard(title, content, id);
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 수정 실패",false);
            // return Script.href("errorpage");
        }else{
            return new ResponseDto<>(1, "글 수정 성공",true);            
            // return Script.href("글 수정 성공","/board/"+id);
        }
    }

    @PostMapping("/boardWrite")
    @ResponseBody
    public ResponseDto<?> boardWrite(@RequestBody Map<String, Object> param){
        String title = param.get("title").toString();
        String content = param.get("content").toString();
        // System.out.println(title);
        // System.out.println(content);
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>(-1, "로그인 풀림",null);
            // return Script.href("errorpage");
        }
        int result = boardRepository.insertBoard(title, content, principal.getId());
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 쓰기 실패",false);
            // return Script.href("errorpage");
        }else{
            return new ResponseDto<>(1, "글 쓰기 성공",true);            
            // return Script.href("글 쓰기 성공","/");
        }
    }

    @DeleteMapping("/board/{id}/delete")
    @ResponseBody
    public ResponseDto<?> boardDelete(@PathVariable int id){        
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>(-1, "로그인 풀림",null);
            // return Script.href("errorpage");
        }
        int result = boardRepository.deleteBoard(id);
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 삭제 실패",false);
            // return Script.href("errorpage");
        }else{
            return new ResponseDto<>(1, "글 삭제 성공",true);            
            // return Script.href("글 쓰기 성공","/");
        }
    }
}
