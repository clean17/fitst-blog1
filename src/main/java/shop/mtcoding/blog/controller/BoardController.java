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
import shop.mtcoding.blog.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

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
    public ResponseDto<?> boardUpdate(@PathVariable int id, 
        @RequestBody Map<String, Object> param, Model model){
        String title = param.get("title").toString();
        String content = param.get("content").toString();

        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
           return new ResponseDto<>( -1, "로그인이 필요한 페이지입니다",null);
        }
        int result = boardService.글수정하기(title, content, id, principal.getUsername());
        if( result != 1){
            return new ResponseDto<>( 1, "글 수정을 실패했습니다.",false);
        }
        BoardDto board = boardRepository.findById(id);
        model.addAttribute("board", board);
        return new ResponseDto<>( 1, "수정 완료",true);
    }

    @PostMapping("/board/Write")
    @ResponseBody
    public ResponseDto<?> boardWrite(@RequestBody Map<String, Object> param){
        String title = param.get("title").toString();
        String content = param.get("content").toString();

        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>( -1, "로그인이 필요한 페이지입니다",null);
        }

        int result = boardService.글쓰기(title, content, principal.getId());
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 쓰기 실패",false);
        }
        return new ResponseDto<>(1, "글 쓰기 성공",true);            
    }

    @DeleteMapping("/board/{id}/delete")
    @ResponseBody
    public ResponseDto<?> boardDelete(@PathVariable int id){        
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>( -1, "로그인이 필요한 페이지입니다",null);
        }
        int result = boardService.글삭제하기(id, principal.getUsername());
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 삭제를 실패했습니다.",false);
        }
        return new ResponseDto<>(1, "삭제 완료",true);            
    }
}
