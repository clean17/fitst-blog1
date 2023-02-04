<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
    <c:if test="${principal.username == board.username}" >
    <div class="mb-3">
            <a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
            <button id="btn-delete" class="btn btn-danger" onclick="deleteBoard()">삭제</button>
        </div>
    </c:if>
        

        <div class="mb-2">
            글 번호 : <span id="id">${board.id}<i> </i></span> 작성자 : <span class="me-3"><i>${board.username} </i></span> 
            <i id="heart" class="fa-regular fa-heart my-xl my-cursor"></i>
        </div>
        <div>
            <h3>${board.title}</h3>
        </div>
        <hr />
        <div>
            <div>${board.body}</div>
        </div>
        <hr />
        <div class="card">
            <form>
                <div class="card-body">
                    <textarea id="reply-content" class="form-control" rows="1"></textarea>
                </div>
                <div class="card-footer">
                    <button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
                </div>
            </form>
        </div>
        <br />
        <div class="card">
            <div class="card-header">댓글 리스트</div>
            <ul id="reply-box" class="list-group">
                <li id="reply-1" class="list-group-item d-flex justify-content-between">
                    <div>댓글내용입니다</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자 : cos &nbsp;</div>
                        <button onClick="replyDelete()" class="badge bg-secondary">삭제</button>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <script>
        function deleteBoard(){
        
            $.ajax({
                type: "delete",
                url: `/board/${board.id}/delete`,
                dataType:"json"                
            }).done((res) => {
                if ( res.code !== 1) 
                    alert(res.msg);
                    location.href='/errorpage';
                if ( res.data == true){
                    alert(res.msg);
                    location.href='/';
                }else{
                    alert(res.msg);
                }
            }).fail((err) => {
            
            });
        }
    </script>
<%@ include file="../layout/footer.jsp" %>