package shop.mtcoding.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DController {
    
    @GetMapping("/")
    public String d(){
        return "tet";
    }
}
