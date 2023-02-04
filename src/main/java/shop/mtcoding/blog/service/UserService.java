package shop.mtcoding.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.ResponseDto;
import shop.mtcoding.blog.model.User;
import shop.mtcoding.blog.model.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 유저네임 중복체크는 서비스에서 꼭 한번 더 해야 한다
    public ResponseDto<?> 중복체크(String username) {
        if (username == null || username.isEmpty()) {
            return new ResponseDto<>(-1, "username을 입력해주세요", null);
        }
        User sameUser = userRepository.findByUsername(username);
        if (sameUser != null) {
            return new ResponseDto<>(1, "동일한 username이 존재합니다", false);
        } else {
            return new ResponseDto<>(1, username + " 사용 가능", true);
        }
    }
    
    @Transactional
    public int 회원가입(JoinReqDto joinReqDto) {
        User sameUser = userRepository.findByUsername(joinReqDto.getUsername());
        if (sameUser != null) {
            throw new CustomException("동일한 username이 존재합니다");
        }
        int result = userRepository.insertUser(joinReqDto.getUsername(), joinReqDto.getPassword(), joinReqDto.getEmail());
        return result;
    };
}
