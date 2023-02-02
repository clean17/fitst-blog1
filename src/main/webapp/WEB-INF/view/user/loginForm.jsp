<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <div class="container">
            <form action="/login" method="post" onsubmit="return valid()">
                <div class="form-group mb-2">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password">
                </div>

                <button type="submit" id="login-btn" class="btn btn-primary">로그인</button>
            </form>

        </div>
    </div>
    <script>
        let loginSuccess = false;

        function valid(){
            if ( loginSuccess ){
                return true;
            }else{
                // alert('아이디 또는 비밀번호가 다릅니다'); 
        				// 회원가입 버튼누르면 뜬다
          return false;
            }
            
        }
        $('#login-btn').click(()=>{
            let logindata = {
                username: $('#username').val(),
                password: $('#password').val()
            }
            console.log(JSON.stringify(logindata));
            $.ajax({
                type: "post",
                url: "/login",
                data: JSON.stringify(logindata),
                headers:{
                    "content-type":"application/json; charset=utf-8"
                },
                dataType:"json"
            }).done((res) => {
                if ( res.data != true ){
                    alert(res.msg);
                    loginSuccess = false;
                }else{
                    loginSuccess = true;
                }           
            }).fail((err) => {
                
            });         
        });
        function login(){

        

        }

    </script>

<%@ include file="../layout/footer.jsp" %>