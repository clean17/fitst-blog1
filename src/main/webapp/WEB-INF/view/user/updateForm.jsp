<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3">
        <div class="container">
            <form>
                <div class="form-group mb-2">
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username"
                        value="${principal.username}" readonly>
                </div>

                <div class="form-group mb-2">
                    <input type="password" name="password" class="form-control" placeholder="Enter password"
                        id="password" value="${principal.password}">
                </div>

                <div class="form-group mb-2">
                    <input type="email" name="email" class="form-control" placeholder="Enter email" id="email"
                        value="${principal.email}">
                </div>

                <button type="button" id="update-btn" class="btn btn-primary">회원수정</button>
            </form>

        </div>
    </div>

    <script>
        $('#update-btn').click(()=>{
            let logindata = {               
                password: $('#password').val(),
                email: $('#email').val()
            }
            console.log(JSON.stringify(logindata));
            $.ajax({
                type: "put",
                url: "/update",
                data: JSON.stringify(logindata),
                headers:{
                    "content-type":"application/json; charset=utf-8"
                },
                dataType:"json"
            }).done((res) => {
                if ( res.data != true ){
                    alert(res.msg);                    
                }else{
                    alert(res.msg);           
                    location.href="/";
                }           
            }).fail((err) => {
                
            });         
        });
    </script>

<%@ include file="../layout/footer.jsp" %>