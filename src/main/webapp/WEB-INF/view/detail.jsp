<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://kit.fontawesome.com/32aa2b8683.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
    <link rel="stylesheet" href="/css/style.css">
</head>

<body>

    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Blog</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse justify-content-between" id="collapsibleNavbar">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/loginForm">로그인</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/joinForm">회원가입</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/board/writeForm">글쓰기</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/updateForm">회원정보</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">로그아웃</a>
                    </li>
                </ul>
                <div>
                    <a href="/user/profileUpdate"><img src="images/profile.jfif" style="width: 35px;"
                            class="rounded-circle" alt="Cinque Terre"></a>
                </div>
            </div>

        </div>
    </nav>

    <div class="container my-3">
        <div class="mb-3">
            <a href="#" class="btn btn-warning">수정</a>
            <button id="btn-delete" class="btn btn-danger">삭제</button>
        </div>

        <div class="mb-2">
            글 번호 : <span id="id"><i>3 </i></span> 작성자 : <span><i>ssar </i></span>
        </div>

        <div>
            <h3>제목입니다</h3>
        </div>
        <hr />
        <div>
            <div>내용입니다</div>
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
                <li id="reply-1" class="list-group-item d-flex justify-content-between">
                    <div>댓글내용입니다</div>
                    <div class="d-flex">
                        <div class="font-italic">작성자 : cos &nbsp;</div>
                        <button onClick="replyDelete()" class="badge bg-danger">삭제</button>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div class="mt-4 p-5 bg-dark text-white rounded text-center" style="margin-bottom: 0">
        <p>Created by MetaCoding</p>
        <p>📞 010-2222-7777</p>
        <p>🏴 부산 수영구 XX동</p>
    </div>
</body>

</html>