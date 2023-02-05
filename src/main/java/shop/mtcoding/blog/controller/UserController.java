package shop.mtcoding.blog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.board.BoardReq.BoardReqDto;
import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.UpdateReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
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
    private HttpSession session;

    @Autowired
    private UserService userService;

    @GetMapping("/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }
    @GetMapping("/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }
    @GetMapping("/logout")
    public @ResponseBody String logout(){
        session.invalidate();
        return Script.href("/");
    }
    @GetMapping("/user/updateForm")
    public String updateForm(){
        return "user/updateForm";
    }

    @PostMapping("/usernameCheck")
    public @ResponseBody ResponseDto<?> usernameCheck(@RequestBody Map<String, Object> param){
        String username = param.get("username").toString();
        return userService.중복체크(username);
    }
    
    @PostMapping("/join")
    public String join(JoinReqDto joinReqDto){    
        if (joinReqDto.getUsername() == null || joinReqDto.getUsername().isEmpty()) {
            throw new CustomException("username을 작성해주세요");
        }
        if (joinReqDto.getPassword() == null || joinReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        if (joinReqDto.getEmail() == null || joinReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }

        int result = userService.회원가입(joinReqDto);
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }
        return "redirect:/loginForm";      
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseDto<?> login(@RequestBody Map<String, Object> param){     
        String username = param.get("username").toString();
        String password = param.get("password").toString();

        if ( username == null || username.isEmpty() ){
            return new ResponseDto<>(1, "아이디가 비었습니다",null);
        }
        if ( password == null || password.isEmpty()){
            return new ResponseDto<>(1, "비밀번호가 비었습니다",null);
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
 
    // @PostMapping("/join")
    // @ResponseBody
    // public ResponseDto<?> join(@RequestBody Map<String, Object> param){
    //     String username = param.get("username").toString();
    //     String password = param.get("password").toString();
    //     String email = param.get("email").toString();
    
    //     if( username == null || username.isEmpty() ){
    //         return new ResponseDto<>(1, "아이디를 입력하세요",null);
    //     }
    //     if( password == null || password.isEmpty() ){
    //         return new ResponseDto<>(1, "패스워드를 입력하세요",null);
    //     }
    //     if( email == null || email.isEmpty() ){
    //         return new ResponseDto<>(1, "이메일을 입력하세요",null);
    //     }
    //     int result = userRepository.insertUser( username, password, email);
    //     if( result != 1 ) {
    //         return new ResponseDto<>(1, "DB 에러",false);
    //     }else{
    //         return new ResponseDto<>(1, "회원 가입 성공",true);
    //     }
    // }

    // @PostMapping("/update")
    // @ResponseBody
    // public ResponseDto<?> update(@RequestBody Map<String, Object> param){
    //     String password = (String)param.get("password");
    //     String email = (String)param.get("email");
    //     int id = (int)param.get("email");

    //     User principal = (User)session.getAttribute("principal");
    //     if( principal == null ){
    //         return new ResponseDto<>(1, "로그인 풀림",false);
    //     }
        
    //     int result = userService.회원수정(password, email, id, principal.getId());
    //     if( result != 1 ) {
    //         return new ResponseDto<>(1, "DB 에러",false);
    //     }
    //     return new ResponseDto<>(1, "회원 수정 완료",true);
    // }

    @PostMapping("/user/update")
    @ResponseBody
    public String update(UpdateReqDto updateReqDto){
        User principal = (User)session.getAttribute("principal");
        if( principal == null ){
            throw new CustomException("로그인이 필요합니다.");
        }
        int result = userService.회원수정(updateReqDto, principal.getId());
        if( result != 1 ) {
            throw new CustomException("수정 실패");
        }
        return Script.href("수정 완료", "/");
    }
}
