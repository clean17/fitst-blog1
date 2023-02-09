package shop.mtcoding.blog.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.mtcoding.blog.dto.board.BoardReq.BoardSaveReqDto;
import shop.mtcoding.blog.dto.board.BoardReq.BoardUpdateRqeDto;
import shop.mtcoding.blog.handler.ex.CustomApiException;
import shop.mtcoding.blog.handler.ex.CustomException;
import shop.mtcoding.blog.model.Board;
import shop.mtcoding.blog.model.BoardRepository;
import shop.mtcoding.blog.util.HtmlParser;

@Transactional( readOnly =  true) // 트랜잭션 안붙으면 이게 자동으로 생성
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    
    @Transactional
    public void 글수정하기(BoardUpdateRqeDto bu, int id, int principalId) {
        Board boardPS = boardRepository.findbyId(id);
        if ( boardPS == null ){
            throw new CustomApiException("해당 게시글을 찾을 수가 없습니다.");
        }
        if ( boardPS.getUserId() != principalId){
            throw new CustomException("글 수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        String thumbnail = HtmlParser.getThumbnail(bu.getContent());
        int result = boardRepository.updateBoard(
            bu.getTitle(),
            bu.getContent(),
            thumbnail,            
            boardPS.getId());
        if (result != 1) {
            throw new CustomApiException("게시글 수정에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
     
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
    // @Transactional
    public void 글쓰기(BoardSaveReqDto boardSaveReqDto, int userId){
        String thumbnail = HtmlParser.getThumbnail(boardSaveReqDto.getContent());

        int result = boardRepository.insertBoard(
                    boardSaveReqDto.getTitle(), 
                    boardSaveReqDto.getContent(),
                    thumbnail,
                    userId);
        if ( result != 1 ){
            throw new CustomException("글 쓰기에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }
}
