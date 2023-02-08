<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>

    <div class="container my-3 ">
        <div class="my-grid">
        <c:forEach items="${boardList}" var="board">
            <div class="card">
                <img class="card-img-top" src="${board.thumbnail}" alt="Card image">
                <hr>
                <div class="card-body">
                    <div>작성자 : ${board.username} </div>
                    <h4 class="card-title my-text-ellipsis">${board.title}</h4>
                    <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
                </div>
            </div>
        </c:forEach>
        </div>
        <ul class="pagination mt-3 d-flex justify-content-center">
            <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
            <li class="page-item"><a class="page-link" href="#">Next</a></li>
        </ul>
    </div>
<%@ include file="../layout/footer.jsp" %>
