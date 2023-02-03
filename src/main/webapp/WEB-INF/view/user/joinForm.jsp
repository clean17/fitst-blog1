<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <div class="container">
            <form action="/join" method="post" onsubmit="return valid()">
                <div class="d-flex form-group mb-2">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
                    <button type="button" class="badge bg-secondary ms-2" id="usernameCheck">중복확인</button>
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password">
                </div>

                <div class="form-group mb-2">
                    <input type="password" class="form-control" placeholder="Enter passwordCheck" id="passwordCheck">
                </div>

                <div class="form-group mb-2">
                    <input type="email" name="email" class="form-control" placeholder="Enter email" id="email">
                </div>

                <button type="button"  id="join-btn" class="btn btn-primary">회원가입</button>
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
        $('#join-btn').click(()=>{
            let logindata = {
                username: $('#username').val(),
                password: $('#password').val(),
                email: $('#email').val()
            }
            console.log(JSON.stringify(logindata));
            $.ajax({
                type: "post",
                url: "/join",
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
                    alert(res.msg);
                    loginSuccess = true;
                    location.href="/loginForm";
                }           
            }).fail((err) => {
                
            });         
        });
    </script>

<%@ include file="../layout/footer.jsp" %>