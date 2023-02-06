package shop.mtcoding.blog.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.dto.user.UserReq.UpdateReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.ResponseDto;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.service.UserService;
import shop.mtcoding.blog.util.Script;

@Controller
public class UserController {
    // @Autowired
    // private UserRepository userRepository;

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
        // 프로그램을 만들때는 모든 기능이 다 작동하는지 테스트를 하고 다음단계로 넘어가야 한다. !!!!!!!!!!!!!!!!!!!!!!!!!
        // 나중에 디버깅하기가 힘들어진다. 제대로 검증을 하고 넘어가라 !!!!!!!!!!!!!!!!
        return "redirect:/loginForm";      
    }

    @PostMapping("/login")
    public String login(LoginReqDto loginReqDto){
        if (loginReqDto.getUsername() == null || loginReqDto.getUsername().isEmpty()) {
            // 원래는 한글막고 길이제한걸고.. 정규표현식을 사용해야함 
            throw new CustomException("username을 작성해주세요");
        }
        if (loginReqDto.getPassword() == null || loginReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        User principal = userService.로그인(loginReqDto);
        
        session.setAttribute("principal", principal);
        System.out.println(principal.getUsername()); // @Test 가 현 메소드를 실제상황처럼 실행시켜준다.
        return "redirect:/";
    }
 
    

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
