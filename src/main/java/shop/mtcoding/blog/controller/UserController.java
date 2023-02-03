package shop.mtcoding.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.model.ResponseDto;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;
import shop.mtcoding.blog.util.Script;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService service;

    // 폼을 한번에 받을거야 .. 파라미터에 계속해서 추가할 수 없으니까 Dto에 받음
    // 메소드를 한번 만지면 완벽하게 만들어.. 유효성도 넣어야지.. 서비스로 넘어가지 말래
    // 이렇게 예외를 다 잡고 서비스로 넘어가면 익셉션은 컨트롤러에서 발생하지 않은게 보장이 된다
    // 이렇게 익셉션 핸들러를 만들어 놓으면 디버깅할때 좋다
    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto){
        System.out.println(joinReqDto.getUsername());
        System.out.println(joinReqDto.getPassword());
        System.out.println(joinReqDto.getEmail());
        int result = service.회원가입(joinReqDto);
        if(result !=1 ) throw new CustomException("회원가입실패");

        return "redirect:/loginForm";
    }

    @GetMapping("/")
    public String main(Model model){
        List<Board> boardList = boardRepository.findAll();
        model.addAttribute("boardList", boardList);    
        return "user/main" ;
    }

    @GetMapping("/joinForm")
    public String joinForm(){
    
        return "user/joinForm";
    }


    @GetMapping("/loginForm")
    public String loginForm(){
        throw new CustomException("로그인폼 못들어감");
        // return "user/loginForm";
    }
    
    // @GetMapping("/loginForm")
    // public String loginForm(){
    
    //     return "user/loginForm";
    // }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(){
        session.invalidate();
        return Script.href("/");
    }

    @GetMapping("/user/updateForm")
    public String updateForm(){
    
        return "user/updateForm";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseDto<?> login(@RequestBody Map<String, Object> param){     
        String username = param.get("username").toString();
        String password = param.get("password").toString();

        if ( username == null || username.isEmpty() || password == null || password.isEmpty()){
            return new ResponseDto<>(1, "아이디 또는 비밀번호가 비었습니다",null);
        }
        User principal = userRepository.findByUsernameAndPassword(username, password);
        if ( principal == null ) {
            return new ResponseDto<>(1, "아이디 또는 비밀번호가 다릅니다", false);
        }else{
            // Cookie cookie = new Cookie("remember",username);
            session.setAttribute("principal", principal);
            return new ResponseDto<>(1, "로그인 성공", true);
        }
    }

    @PostMapping("/usernameCheck")
    public @ResponseBody ResponseDto<?> check(@RequestBody Map<String, Object> param){     	
        String username = param.get("username").toString();

        if( username == null || username.isEmpty()){  
            return new ResponseDto<>(-1,"username을 입력해주세요",null);
        }
        if ( username.equals("ssar")){
            return new ResponseDto<>(1,"동일한 username이 존재", false);
        }else{
            return new ResponseDto<>(1,"해당 username으로 회원가입 가능", true);
        }
    }  
 
    // @PostMapping("/join")
    // @ResponseBody
    // public ResponseDto<?> join(@RequestBody Map<String, Object> param){
    //     String username = param.get("username").toString();
    //     String password = param.get("password").toString();
    //     String email = param.get("email").toString();
    
    //     if( username == null || username.isEmpty() || password == null || password.isEmpty() || email == null || email.isEmpty() ){
    //         return new ResponseDto<>(1, "필수 입력창을 확인하세요",null);
    //     }
    //     int result = userRepository.insertUser( username, password, email);
    //     if( result != 1 ) {
    //         return new ResponseDto<>(1, "DB 에러",false);
    //     }else{
    //         return new ResponseDto<>(1, "회원 가입 성공",true);
    //     }
    // }

    @PutMapping("/update")
    @ResponseBody
    public ResponseDto<?> update(@RequestBody Map<String, Object> param){
        String password = param.get("password").toString();
        String email = param.get("email").toString();

        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            return new ResponseDto<>(1, "로그인 풀림",false);
        }

        if( password == null || password.isEmpty() || email == null || email.isEmpty() ){
            return new ResponseDto<>(1, "필수 입력창을 확인하세요",null);
        }
        int result = userRepository.updateUser( password, email, principal.getId());
        if( result != 1 ) {
            return new ResponseDto<>(1, "DB 에러",false);
        }else{
            return new ResponseDto<>(1, "수정 완료",true);
        }
    }
}
