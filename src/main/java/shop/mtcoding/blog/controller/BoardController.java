package shop.mtcoding.blog.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog.dto.reply.ReplyDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ReplyRepository;
import shop.mtcoding.blog.model.User;
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
    private BoardService boardService;

    @Autowired
    private ReplyRepository replyRepository;

    // private void mockSession(){
    //     User user = new User();
    //     user.setId(1);
    //     user.setUsername("ssar");
    //     session.setAttribute("principal", user);
    // }

    @GetMapping({"/","board"})
    public String main(Model model){
        // mockSession();
        model.addAttribute("boardList", boardRepository.findAllWithUser());    
        return "user/main" ;
    }
    @GetMapping("/board/writeForm")
    public String writeForm(){
        return "/board/writeForm";
    }
    @GetMapping("/board/{id}")
    public String detail(@PathVariable int id, Model model){
        BoardDetailRespDto dto = boardRepository.findByIdWithUser(id);
        if ( dto == null ){
            return "redirect:/errorpage";
        }
        List<ReplyDto> replyList = replyRepository.findAll();
        model.addAttribute("dto", dto);
        model.addAttribute("replyList", replyList);
        return "board/detail";
    }
    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id, Model model){
        BoardDetailRespDto dto = boardRepository.findByIdWithUser(id);
        if ( dto == null ){
            return "redirect:/errorpage";
        }
        model.addAttribute("dto", dto);
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
    //     BoardDetailRespDto board = boardRepository.findByIdWithUser(id);
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



    @DeleteMapping("/board/{id}")
    @ResponseBody  //  ResponseEntity 내부에 구현되어 있다 안적어도 되지만 공부하려고 적어놓자
    public ResponseEntity<?> boardDelete(@PathVariable int id){        
        // 스크립트를 리턴하기 위해 ResponseEntity<?> 를 사용 //
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            throw new CustomApiException("로그인이 필요한 페이지 입니다", HttpStatus.UNAUTHORIZED); 
            // 리턴 <script>alert('로그인이 필요한 페이지 입니다');history.back();</script>
        }
        boardService.글삭제하기(id, principal.getId());
         // delete 는 json 응답을 받아야 한다.. 자바스크립트가 요청했으니까 ajax 로
        return new ResponseEntity<>(new ResponseDto<>(1, "삭제 성공", null),HttpStatus.OK);
    }

    
}
