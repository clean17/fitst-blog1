package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @GetMapping("/")
    public String main(){
    
        return "user/main" ;
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
    public String logout(){
    
        return "";
    }
    
    @GetMapping("/updateForm")
    public String logou111t(){
    
        return "user/updateForm";
    }
}
