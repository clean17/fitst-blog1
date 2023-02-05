package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.board.BoardDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardReqDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public int 글수정하기(BoardReqDto boardReqDto, int userId, int principalId) {
        if ( userId != principalId){
            throw new CustomException("글 수정 권한이 없습니다.");
        }
        BoardDto board = boardRepository.findById(boardReqDto.getId());
        if (board == null) {
            throw new CustomException("해당 글이 없습니다.");
        }
        if ( boardReqDto.getTitle() == null || boardReqDto.getTitle().isEmpty() ){
            throw new CustomException("글 제목이 없습니다.");
        }
        if ( boardReqDto.getContent() == null || boardReqDto.getContent().isEmpty() ){
            throw new CustomException("글 내용이 없습니다.");
        }
        return boardRepository.updateBoard(
            boardReqDto.getTitle(), 
            boardReqDto.getContent(),
            boardReqDto.getId());
    }

    @Transactional
    public int 글삭제하기(int id, String username) {
        BoardDto board = boardRepository.findById(id);
        if (board == null) { return -1; }      
        if (!board.getUsername().equalsIgnoreCase(username)) { return -1;}
        return boardRepository.deleteBoard(id);
    }

    @Transactional
    public int 글쓰기(BoardReqDto boardReqDto, int userId) {
        if( boardReqDto.getId() != userId){
            throw new CustomException("잘못된 접근입니다.");
        }
        if ( boardReqDto.getTitle() == null || boardReqDto.getTitle().isEmpty() ){
            throw new CustomException("글 제목이 없습니다.");
        }
        if ( boardReqDto.getContent() == null || boardReqDto.getContent().isEmpty() ){
            throw new CustomException("글 내용이 없습니다.");
        }
        return boardRepository.insertBoard(
                boardReqDto.getTitle(), 
                boardReqDto.getContent(), 
                userId);
    }
}
