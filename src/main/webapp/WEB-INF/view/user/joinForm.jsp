<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <div class="container">
            <form action="/join" method="post" onsubmit="return valid()">
                <div class="d-flex form-group mb-2">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username" required>
                    <button type="button" class="badge bg-secondary ms-2" id="usernameCheck">중복확인</button>
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password" required>
                </div>

                <div class="form-group mb-2">
                    <input type="password" class="form-control" placeholder="Enter passwordCheck" id="passwordCheck" required>
                </div>

                <div class="form-group mb-2">
                    <input type="email" name="email" class="form-control" placeholder="Enter email" id="email" required>
                </div>

                <button type="submit" id="join-btn" class="btn btn-primary">회원가입</button>
            </form>

        </div>
    </div>

    <script>    
        let joinOk = false;

        function valid(){
            if ( joinOk ){
                if( $('#password').val()===$('#passwordCheck').val()) {
                    return true;
                }else{
                    alert('패스워드가 다릅니다')
                    return false;
                }
            }else{
                alert('아이디 중복확인이 필요합니다'); // 회원가입 버튼누르면 뜬다                     				
                return false;
            }
        }
        $('#usernameCheck').click(()=>{            
            let username = { username: $('#username').val() }

            $.ajax({
                type: "post",
                url: "/usernameCheck",
                data: JSON.stringify(username),
                headers:{
                    "content-type":"application/json; charset=utf-8"
                },
                dataType:"json"
            }).done((res) => {
                if( res.code !== 1) {
                    alert(res.msg);
                }
                if( res.data === true){
                    alert(res.msg);
                    joinOk = true;
                }else{
                    alert(res.msg);
                    joinOk = false;
                }            
            }).fail((err) => {
                alert('실패'); 
            });
        });

        // $('#join-btn').click(()=>{
        //     if( valid() === true ){
        //         alert("회원가입 성공");
        //     }
        // })

        // $('#join-btn').click(()=>{
        //     if( loginSuccess === true) {
        //         let logindata = {
        //         username: $('#username').val(),
        //         password: $('#password').val(),
        //         email: $('#email').val()
        //     }
        //     $.ajax({
        //         type: "post",
        //         url: "/join",
        //         data: JSON.stringify(logindata),
        //         headers:{
        //             "content-type":"application/json; charset=utf-8"
        //         },
        //         dataType:"json"
        //     }).done((res) => {
        //         if ( res.data != true ){
        //             loginSuccess = false;
        //             alert(res.msg);
        //             // history.go(-1);
                    
        //         }else{
        //             alert(res.msg);
        //             loginSuccess = true;
        //             location.href="/loginForm";
        //         }           
        //     }).fail((err) => {
                
        //     });         
        //     };            
        // });
    </script>

<%@ include file="../layout/footer.jsp" %>