package shop.mtcoding.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @GetMapping("/")
    public String main(){
    
        return "main" ;
    }

    @GetMapping("/joinForm")
    public String joinForm(){
    
        return "joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
    
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(){
    
        return "";
    }
    
}
