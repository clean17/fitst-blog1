package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardResp;
import shop.mtcoding.blog.dto.reply.ReplyDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.BoardService;
import shop.mtcoding.blog.util.Script;

@Controller
public class BoardController {
    // System.out.println("디버그 " +boardSaveReqDto.getId());

    @Autowired
    private BoardRepository boardRepository;
    
    @Autowired
    private HttpSession session;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private ReplyRepository replyRepository;

    @GetMapping("/")
    public String main(Model model){
        // User principal = userRepository.findByUsernameAndPassword("ssar", "1234");
        // session.setAttribute("principal", principal);
        List<BoardResp.BoardMainRespDto> boardList = boardRepository.findAllWithUser();
        model.addAttribute("boardList", boardList);    
        return "user/main" ;
    }
    @GetMapping("/board/writeForm")
    public String writeForm(){
        return "/board/writeForm";
    }
    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model){
        BoardResp.BoardDto board = boardRepository.findById(id);
        if ( board == null ){
            return "redirect:/errorpage";
        }
        List<ReplyDto> replyList = replyRepository.findAll();
        model.addAttribute("board", board);
        model.addAttribute("replyList", replyList);
        return "board/detail";
    }
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        BoardResp.BoardDto board = boardRepository.findById(id);
        if ( board == null ){
            return "redirect:/errorpage";
        }
        model.addAttribute("board", board);
        return "board/updateForm";
    }

    @PostMapping("/borad/{id}/update")
    @ResponseBody
    public String boardUpdate(@PathVariable int id ,BoardSaveReqDto BoardSaveReqDto, int userId){
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            throw new CustomException("로그인이 필요한 페이지 입니다");
        }
        int result = boardService.글수정하기(BoardSaveReqDto, userId, principal.getId());
        if( result != 1){
            throw new CustomException("글 수정에 실패 했습니다.");
        }
        return Script.href("/board/"+id);
    }

    // @PutMapping("/borad/{id}/update")
    // @ResponseBody
    // public ResponseDto<?> boardUpdate(@PathVariable int id, 
    //     @RequestBody Map<String, Object> param, Model model){
    //     String title = param.get("title").toString();
    //     String content = param.get("content").toString();

    //     User principal = (User)session.getAttribute("principal");
    //     if( principal == null ){
    //        return new ResponseDto<>( -1, "로그인이 필요한 페이지입니다",null);
    //     }
    //     int result = boardService.글수정하기(title, content, id, principal.getUsername());
    //     if( result != 1){
    //         return new ResponseDto<>( 1, "글 수정을 실패했습니다.",false);
    //     }
    //     BoardDto board = boardRepository.findById(id);
    //     model.addAttribute("board", board);
    //     return new ResponseDto<>( 1, "수정 완료",true);
    // }

    ////////////////////////  글쓰기
    @PostMapping("/board/Write")
    @ResponseBody
    public String boardWrite(BoardSaveReqDto boardSaveReqDto){
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            throw new CustomException("로그인이 필요한 페이지 입니다", HttpStatus.UNAUTHORIZED); 
                 // 인증이 안돼었으면 401을 리턴해야한다 // 403 은 권한이 없을때 리턴해야한다.
        }
        if ( boardSaveReqDto.getTitle() == null || boardSaveReqDto.getTitle().isEmpty() ){
            throw new CustomException("글 제목이 없습니다.");
        }
        if ( boardSaveReqDto.getTitle().length() > 100 ){
            throw new CustomException("제목의 허용길이 100자를 초과했습니다.");
        }
        if ( boardSaveReqDto.getContent() == null || boardSaveReqDto.getContent().isEmpty() ){
            throw new CustomException("글 내용이 없습니다.");
        }
        // 이렇게 로직으로 모든걸 막아야하는데.. 자바스크립트도 막고 뭐도 막고,,,
        // 이런 작업을 AOP 란것을 이용해서 나중에 편하게 할수가 있다
        
        // 컨트롤러의 유효성 검사 받아온 파라미터가 존재하는지는 컨트롤러에서 걸러본다...
        boardService.글쓰기(boardSaveReqDto, principal.getId());         
        return Script.href("/");
    }



    @DeleteMapping("/board/{id}/delete")
    @ResponseBody
    public ResponseDto<?> boardDelete(@PathVariable int id){        
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>( 0, "로그인이 필요한 페이지입니다",null);
        }
        BoardResp.BoardDto board = boardRepository.findById(id);
        if (board == null) {
            return new ResponseDto<>( -1, "글이 존재하지 않습니다.",null);
        }
        if (!board.getUsername().equalsIgnoreCase(principal.getUsername())) {
            return new ResponseDto<>( -1, "글 삭제 권한이 없습니다.",null);
        }
        int result = boardService.글삭제하기(id, principal.getUsername());
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 삭제를 실패했습니다.",false);
        }
        return new ResponseDto<>(1, "삭제 완료",true);            
    }

    
}
