<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <form>
        <!-- action="/board/Write" method="post" -->
        
            <div class="form-group">
                <input type="text" class="form-control" id="title" placeholder="Enter title" name="title">
            </div>
            <div class="form-group">
                <textarea class="form-control summernote" id="content" rows="5" name="content" value=""></textarea>
            </div>
            <button type="button" onclick="saveById()" class="btn btn-primary">글쓰기완료</button>
            <!-- type="submit"  -->
        </form>
    </div>

    <script>
        $('.summernote').summernote({
            tabsize: 2,
            height: 400
        });
        function saveById(id){
            let post = {
                title: $('#title').val(),
                content: $('#content').val(),
            }
            $.ajax({
                type: "post",
                url: "/board/Write",
                // "/borad/"+id
                data: JSON.stringify(post),
                // headers:{
                //     "content-type":"application/json; charset=utf-8"
                // },
                    contentType:"application/json; charset=utf-8",
                dataType:"json"
            }).done((res) => {
                alert(res.msg);
                location.href="/";
            }).fail((err) => {
                alert(err.responseJSON.msg);
                location.href="/";
            });
        }
    </script>

<%@ include file="../layout/footer.jsp" %>