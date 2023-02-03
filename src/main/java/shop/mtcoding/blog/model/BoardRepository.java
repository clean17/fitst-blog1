package shop.mtcoding.blog.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BoardRepository {
    public int insertBoard(
        @Param("title") String title,
        @Param("body") String body,
        @Param("userId") int userId
    );
    public int deleteBoard(int userId); 
}
