<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <form>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Enter title" name="title" id="title" value="${board.title}">
            </div>

            <div class="form-group">
                <textarea class="form-control summernote" rows="5" id="content" name="content">${board.body}</textarea>
            </div>
        </form>
        <button type="button" class="btn btn-primary" onclick="update()">글수정완료</button>

    </div>

    <script>
        $('.summernote').summernote({
            tabsize: 2,
            height: 400
        });
        function update(){
            let post = {
                title: $('#title').val(),
                content: $('#content').val()
            }
            $.ajax({
                type: "put",
                url: "/borad/${board.id}/update",
                data: JSON.stringify(post),
                headers:{
                    "content-type":"application/json; charset=utf-8"
                },
                dataType:"json"
            }).done((res) => {
                if(res.code === -1 ){
                    alert(res.msg);
                    location.href='/loginForm';
                }
                if(res.data === true){
                    alert(res.msg);
                    location.href='/board/${board.id}';
                }else{  
                    alert(res.msg);
                    location.href='/';
                }
            }).fail((err) => {
            
            });
        }
    </script>

<%@ include file="../layout/footer.jsp" %>