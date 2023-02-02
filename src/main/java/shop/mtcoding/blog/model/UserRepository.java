package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {
    
    public List<User> findAll();

    public User findByUsernameAndPassword(
        @Param("username") String username,
        @Param("password") String password
    );
}
