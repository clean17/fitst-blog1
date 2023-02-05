<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <form action="/board/Write" method="post">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Enter title" name="title">
            </div>
            <div class="form-group">
                <textarea class="form-control summernote" rows="5" name="content" value=""></textarea>
            </div>
                <input type="hidden" name="id" value="${principal.id}">
            <button type="submit" class="btn btn-primary">글쓰기완료</button>
        </form>
    </div>

    <script>
        $('.summernote').summernote({
            tabsize: 2,
            height: 400
        });
        
        // $('#write-btn').click(()=>{
        //     $('#title').val();
        //     $('#content').val();
        //     $('#id').val();

        // });

        // function writeBoard(){
        //     let post = {
        //         title: $('#title').val(),
        //         content: $('#content').val(),
        //         id: $('#id').val()
        //     }
        //     console.log(JSON.stringify(post)); 
        //     $.ajax({
        //         type: "post",
        //         url: "/board/Write",
        //         data: JSON.stringify(post),
        //         headers:{
        //             "content-type":"application/json; charset=utf-8"
        //         },
        //         dataType:"json"
        //     }).done((res) => {
        //         if(res.code !== 1 )
        //             location.href="#";
        //         if(res.data === true){
        //             alert(res.msg);
        //             window.location.href='/';
        //         }else{
        //             alert(res.msg);
        //         }
        //     }).fail((err) => {
            
        //     });    
        // }        
    </script>

<%@ include file="../layout/footer.jsp" %>