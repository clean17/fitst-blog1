<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>


    <div class="container my-3">
        <!-- <form action="/borad/${board.id}/update" method="post"> -->
        <form>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Enter title" id="title" name="title" value="${board.title}">
            </div>

            <div class="form-group">
                <textarea class="form-control summernote" id="content" rows="5" name="content">${board.content}</textarea>
            </div>
        <button type="button" class="btn btn-primary" onclick="updateById(`${board.id}`)">글수정완료</button>
        </form>
    </div>

    <script>
        $('.summernote').summernote({
            tabsize: 2,
            height: 400
        });
        function updateById(id){
            let post = {
                title: $('#title').val(),
                content: $('#content').val(),
            }
            // js 오브젝트  key=value 
            $.ajax({
                type: "put",
                url: "/borad/"+id,
                data: JSON.stringify(post),
                // headers:{
                //     "content-type":"application/json; charset=utf-8"
                // },
                
                    contentType:"application/json; charset=utf-8"
                ,
                dataType:"json"
                
            }).done((res) => {
                alert(res.msg);
                location.href="/board/"+id;
            }).fail((err) => {
                alert(err.responseJSON.msg);
            });
        }
    </script>

<%@ include file="../layout/footer.jsp" %>