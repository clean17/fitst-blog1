package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import shop.mtcoding.blog.dto.BoardDto;

@Mapper
public interface BoardRepository {
    public int insertBoard(
        @Param("title") String title,
        @Param("body") String body,
        @Param("userId") int userId
    );
    public int deleteBoard(int id); 

    public int updateBoard(
        @Param("title") String title,
        @Param("body") String body,
        @Param("id") int id
    );
    public List<Board> findAll();
    public BoardDto findById(int id);
}

