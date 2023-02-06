package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.ResponseDto;
import shop.mtcoding.blog.dto.user.UserReq.JoinReqDto;
import shop.mtcoding.blog.dto.user.UserReq.LoginReqDto;
import shop.mtcoding.blog.dto.user.UserReq.UpdateReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
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
    public void 회원가입(JoinReqDto joinReqDto) {  // void 로 만들어 여기서 -1 을 받아서 회원가입실패 진단을 내려 .. 컨트롤러의 책임을 가볍게 만들어 
        User sameUser = userRepository.findByUsername(joinReqDto.getUsername());
        if (sameUser != null) {
            throw new CustomException("동일한 username이 존재합니다");
        }
        int result = userRepository.insertUser(
                joinReqDto.getUsername(), 
                joinReqDto.getPassword(), 
                joinReqDto.getEmail());
        if (result != 1) {
            throw new CustomException("회원가입실패");
        }        
    };

    @Transactional
    public int 회원수정(UpdateReqDto updateReqDto, int principalId){
        if( updateReqDto.getId() != principalId){
            throw new CustomException("본인 정보만 수정 가능합니다.");
        }
        if (updateReqDto.getPassword() == null || updateReqDto.getPassword().isEmpty()) {
            throw new CustomException("password를 작성해주세요");
        }
        if (updateReqDto.getEmail() == null || updateReqDto.getEmail().isEmpty()) {
            throw new CustomException("email을 작성해주세요");
        }
        return userRepository.updateUser(
            updateReqDto.getPassword(), 
            updateReqDto.getEmail(), 
            principalId);
    }

    // 로그인은 조회만 하지만 조회중에 다른메소드에서 변경을 하면 현 조회메소드가 진행중에
    // 데이터가 바뀌면 메소드가 에러가 난다. 그래서 처음메소드에 진입했을때부터 나갈때까지 
    // 데이터를 보존하기 위해서 ( 아이솔레이션 ) 조회할때도 트랜잭션을 걸어준다. 
    // 개발자들을 보통 조회에도 트랜잭션을 걸어준다. 어차피 조회는 순식간에 끝나고 
    // 그 찰나에 변경에 의한 에러를 도출하기 싫다는거야

    @Transactional(readOnly = true)
    public User 로그인(LoginReqDto loginReqDto) {
        User principal = userRepository.findByUsernameAndPassword(
                loginReqDto.getUsername(),
                loginReqDto.getPassword());
        if (principal == null) {
            throw new CustomException("유저네임 혹은 패스워드가 잘못 입력되었습니다.");
        }
        return principal;
    }


    // junit 테스트에서 사용
    // public User 로그인(LoginReqDto loginReqDto){
        
    //     User user = new User();
    //     user.setId(1);
    //     user.setUsername("ssar");
    //     user.setPassword("1234");
    //     user.setEmail("ssar@nate.com");
    //     // return null;
    //     return user;
    // }
    

}
