<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <div class="container">
            <form action="/login" method="post" >
                <div class="form-group mb-2">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username" required>
                </div>
                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password" required>
                </div>
                <button type="submit" id="login-btn" class="btn btn-primary">로그인</button>
            </form>
        </div>
    </div>
    <script>
        // let loginSuccess = false;

        // function valid(){
        //     if ( loginSuccess ){
        //         return true;
        //     }else{
        //         return false;
        //     }
            
        // }
        // $('#login-btn').click(()=>{
        //     let logindata = {
        //         username: $('#username').val(),
        //         password: $('#password').val()
        //     }
        //     console.log(JSON.stringify(logindata));
        //     $.ajax({
        //         type: "post",
        //         url: "/login",
        //         data: JSON.stringify(logindata),
        //         headers:{
        //             "content-type":"application/json; charset=utf-8"
        //         },
        //         dataType:"json"
        //     }).done((res) => {
        //         if ( res.data != true ){
        //             alert(res.msg);
        //             loginSuccess = false;
        //         }else{
        //             alert(res.msg);
        //             loginSuccess = true;
        //             location.href="/";
        //         }           
        //     }).fail((err) => {
                
        //     });         
        // });
    </script>

<%@ include file="../layout/footer.jsp" %>