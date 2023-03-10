<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
    <%-- <c:if test="${principal.id == dto.userId}" > --%>
        <div class="mb-3">
            <a href="/board/${dto.id}/updateForm" class="btn btn-warning">수정</a>
            <button type="button" onclick="deleteBoard(${dto.id})" class="btn btn-danger">삭제</button>
        </div>
    <%-- </c:if> --%>

        <div class="mb-2 d-flex justify-content-end">
            글 번호 : &nbsp<span id="id">${dto.id}<i>&nbsp&nbsp&nbsp&nbsp </i></span> 작성자 : &nbsp<span class="me-3"><i>${dto.username} </i></span> 
            <i id="heart" class="fa-regular fa-heart my-xl my-cursor"></i>
        </div>
        <div>
            <h1><b>${dto.title}</b></h1>
        </div>
        <hr />
        <div>
            <div>${dto.content}</div>
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
        function deleteBoard(id) {  // 스크립트에 el표현식을 사용하지 마라 !!! 나중에 자바스크립트를 파일로 추출할때 안 먹힌다.
            $.ajax({
                type: "delete",
                url: "/board/"+id,
                dataType:"json"
            }).done((res) => { // 2xx 일때
                console.dir(res);
                alert(res.msg);
                location.href = '/';
            }).fail((err) => { // 4xx 5xx 일때
                console.dir(err);
                alert(err.responseJSON.msg);
                location.href = '/';
            });
        }










        $('#btn-reply-save').click(()=>{
            let reply = { reply: $('#reply-content').val() };
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