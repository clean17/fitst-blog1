package shop.mtcoding.blog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import shop.mtcoding.blog.model.ResponseDto;
import shop.mtcoding.blog.model.UserInfo;
import shop.mtcoding.blog.model.UserRepository;
import shop.mtcoding.blog.util.Script;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/")
    public String main(){
    
        return "user/main" ;
    }

    @GetMapping("/22")
    @ResponseBody
    public String test(){
        return Script.href("/");
    }

    @GetMapping("/joinForm")
    public String joinForm(){
    
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
    
        return "user/loginForm";
    }

    @GetMapping("/logout")
    @ResponseBody
    public String logout(){
        session.invalidate();
        return Script.href("/");
    }
    
    @GetMapping("/updateForm")
    public String logou111t(){
    
        return "user/updateForm";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseDto<?> login(@RequestBody UserInfo userInfo){        
        Gson gson = new Gson();
        UserInfo userInfo2 = gson.fromJson(userInfo, UserInfo.class);
        // (userInfo, UserInfo.class);
        // System.out.println(userInfo2.getUsername());
        // userInfo2.getUsername()

        // if ( username == null || username.isEmpty() || password == null || password.isEmpty()){
        //     return new ResponseDto<>(1, "아이디가 비밀번호가 비었습니다",null);
        // }
        // User principal = userRepository.findByUsernameAndPassword(username, password);
        // if ( principal == null ) {
        //     return new ResponseDto<>(1, "아이디 또는 비밀번호가 다릅니다", false);
        // }else{
        //     // Cookie cookie = new Cookie("remember",username);
        // }
        // session.setAttribute("principal", principal);
        // return new ResponseDto<>(1, "로그인 성공", true);
        return new ResponseDto<>(1, "로그인 성공", false);
    }
}
