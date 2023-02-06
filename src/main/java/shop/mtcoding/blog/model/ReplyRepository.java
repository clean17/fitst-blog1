package shop.mtcoding.blog.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import shop.mtcoding.blog.dto.reply.ReplyDto;

@Mapper
public interface ReplyRepository {
    
    public List<ReplyDto> findAll();
}
