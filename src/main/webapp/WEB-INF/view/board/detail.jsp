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
            <div>${board.content}</div>
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
            <c:forEach items="${replyList}" var="reply">
                <li id="reply-1" class="list-group-item d-flex justify-content-between">
                    <div>${reply.content}</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자 : {reply.} &nbsp;</div>
                        <button onClick="replyDelete()" class="badge bg-secondary">삭제</button>
                    </div>
                </li>
            </c:forEach>
                
            </ul>
        </div>
    </div>

    <script>
        function deleteBoard() {
            $.ajax({
                type: "delete",
                url: `/board/${board.id}/delete`,
            }).done((res) => {
                if (res.code === 0) {
                    alert(res.msg);
                    location.href = '/loginForm';
                }
                if (res.code === -1) {
                    alert(res.msg);
                    location.href = '/';
                }
                if (res.data === true) {
                    alert(res.msg);
                    location.href = '/';
                } else {
                    alert(res.msg);
                }
            }).fail((err) => {
                alert('실패')
            });
        }
        $('#btn-reply-save').click(()=>{
            let reply = { reply: $('#reply-content').val(); }
            $.ajax({
                type: "put",
                url: "/reply/insert",
                data: JSON.stringify(reply),
                headers:{
                    "content-type":"application/json; charset=utf-8"
                },
                dataType:"json"
            }).done((res) => {
                if( res.code === -1 ){
                
                }
                if( res.data === true ){
                    
                }
            }).fail((err) => {
            
            });
        });
    </script>
<%@ include file="../layout/footer.jsp" %>