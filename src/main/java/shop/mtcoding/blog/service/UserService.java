package shop.mtcoding.blog.service;

import org.springframework.stereotype.Service;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;

@Service
public class UserService {
    
    public int 회원가입(JoinReqDto joinReqDto){
        // 유저네임 중복체크는 서비스에서 꼭 한번 더 해야 한다


        return 1;
    }
}
