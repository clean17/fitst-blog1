package shop.mtcoding.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateRqeDto;
import shop.mtcoding.blog.dto.board.BoardResp.BoardDetailRespDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    
    @Transactional
    public void 글수정하기(BoardUpdateRqeDto boardUpdateRqeDto, int principalId) {
        if ( boardUpdateRqeDto.getUserId() != principalId){
            throw new CustomException("글 수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        Board board = boardRepository.findbyId(boardUpdateRqeDto.getId());
        if (board == null) {
            throw new CustomException("존재하지 않은 글을 수정할 수 없습니다.");
        }
        try {
            boardRepository.updateBoard(
                boardUpdateRqeDto.getTitle(), 
                boardUpdateRqeDto.getContent(),
                boardUpdateRqeDto.getId());
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // boardRepository.updateBoard(
        //     boardUpdateRqeDto.getTitle(), 
        //     boardUpdateRqeDto.getContent(),
        //     boardUpdateRqeDto.getId());
    }

    @Transactional
    public void 글삭제하기(int id, int userId) {
        //db가 try catch로 에러를 자기고 던짐.. 개발자가 처리하기 힘드니까 다시  try catch로 감싸야 한다
        Board boardPS = boardRepository.findbyId(id);
        if (boardPS == null ){
            throw new CustomApiException("존재하지 않는 게시글을 삭제할 수 없습니다.");
        }
        if ( boardPS.getUserId() != userId ){
            throw new CustomApiException("해당 게시글을 삭제할 권한이 없습니다.", HttpStatus.FORBIDDEN); // 403
        }
        try {
            boardRepository.deleteBoard(id);
        } catch (Exception e) {
            throw new CustomApiException("서버에 일시적인 문제가 생겼습니다.", HttpStatus.INTERNAL_SERVER_ERROR); //500
            // !!!!!!! 중요 - 로그를 남겨야한다 !!! DB or File 이후 서버개발자에게 로그를 보내줘야 한다..
            // 보내야 하는 정보는 익셉션 메세지, 발생날짜 등 서버의 개발자에게 필요한 내용들을 CustomServerException 을 만들어서 처리
            // 
        }
    }
    // , String username
    // ) {
        // BoardResp.BoardDetailRespDto board = boardRepository.findByIdWithUser(id);
        // if (board == null) { return -1; }      
        // if (!board.getUsername().equalsIgnoreCase(username)) { return -1;}
        // return boardRepository.deleteBoard(id);

        /* 
         *  BoardDto board = boardRepository.findById(id);
        if (board == null) {
            return new ResponseDto<>( -1, "글이 존재하지 않습니다.",null);
        }
        if (!board.getUsername().equalsIgnoreCase(principal.getUsername())) {
            return new ResponseDto<>( -1, "글 삭제 권한이 없습니다.",null);
        }
        int result = boardService.글삭제하기(id, principal.getUsername());
        if ( result != 1 ){
            return new ResponseDto<>(1, "글 삭제를 실패했습니다.",false);
        }
        return new ResponseDto<>(1, "삭제 완료",true);            
         */
    // }

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
