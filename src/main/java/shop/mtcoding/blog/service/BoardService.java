package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardResp;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDto;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public int 글수정하기(BoardSaveReqDto BoardSaveReqDto, int userId, int principalId) {
        if ( userId != principalId){
            throw new CustomException("글 수정 권한이 없습니다.");
        }
        BoardDto board = boardRepository.findById(BoardSaveReqDto.getId());
        if (board == null) {
            throw new CustomException("해당 글이 없습니다.");
        }
        if ( BoardSaveReqDto.getTitle() == null || BoardSaveReqDto.getTitle().isEmpty() ){
            throw new CustomException("글 제목이 없습니다.");
        }
        if ( BoardSaveReqDto.getContent() == null || BoardSaveReqDto.getContent().isEmpty() ){
            throw new CustomException("글 내용이 없습니다.");
        }
        return boardRepository.updateBoard(
            BoardSaveReqDto.getTitle(), 
            BoardSaveReqDto.getContent(),
            BoardSaveReqDto.getId());
    }

    @Transactional
    public int 글삭제하기(int id, String username) {
        BoardResp.BoardDto board = boardRepository.findById(id);
        if (board == null) { return -1; }      
        if (!board.getUsername().equalsIgnoreCase(username)) { return -1;}
        return boardRepository.deleteBoard(id);
    }

    // @Transactional
    // public int 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId) {
    //     if( BoardSaveReqDto.getId() != userId){
    //         throw new CustomException("잘못된 접근입니다.");
    //     }
        
    //     return boardRepository.insertBoard(
    //            boardSaveReqDto.getTitle(), 
    //            boardSaveReqDto.getContent(), 
    //            userId);
    // }

    // where 절에 걸리는 파라미터를 앞에서 받자
    @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId){
        int result = boardRepository.insertBoard(
                    boardSaveReqDto.getTitle(), 
                    boardSaveReqDto.getContent(), 
                    userId);
        if ( result != 1 ){
            throw new CustomException("글 쓰기에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
            // 글쓰기 실패는 서버의 오류 -> 500 번대의 오류 
            // 글을 너무 길게 넣으면 컨트롤러에서 잘라야함 -> 400 번대 오류
        }       
    }
}
