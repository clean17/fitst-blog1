package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.board.BoardDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public int 글수정하기(String title, String content, int id, String username) {
        BoardDto board = boardRepository.findById(id);
        if (board == null) {
            throw new CustomException("해당 글이 없습니다.");
        }
        if (!board.getUsername().equalsIgnoreCase(username)) {
            throw new CustomException("글 수정 권한이 없습니다.");
        }
        return boardRepository.updateBoard(title, content, id);
    }

    @Transactional
    public int 글삭제하기(int id, String username) {
        BoardDto board = boardRepository.findById(id);
        if (board == null) {
            throw new CustomException("해당 글이 없습니다.");
        }
        if (!board.getUsername().equalsIgnoreCase(username)) {
            throw new CustomException("글 삭제 권한이 없습니다.");
        }
        return boardRepository.deleteBoard(id);
    }

    @Transactional
    public int 글쓰기(String title, String content, int userId) {
        if ( title == null || title.isEmpty() ){
            throw new CustomException("글 제목이 없습니다.");
        }
        if ( content == null || content.isEmpty() ){
            throw new CustomException("글 내용이 없습니다.");
        }
        return boardRepository.insertBoard(title, content, userId);
    }
}
