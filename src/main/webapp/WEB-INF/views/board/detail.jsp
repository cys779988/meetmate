<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<input type="hidden" value="${boardDto.id}" id="boardId">
					<h2>${boardDto.title}</h2>
				    <p>[${boardDto.createdDate}]</p>
				    <p>작성자 : ${boardDto.registrant}</p>
						<div class="mb-3" style="min-height: 500px;">
							<label for="content"></label>
							<p>${boardDto.content}</p>
						</div>
					<div class="mt-3">
						<button class="btn btn-primary mb-3" id="list-btn">목록</button>
						<c:if test="${owner}">
							<button class="btn btn-primary mb-3" id="modify-btn">수정</button>
							<button class="btn btn-primary mb-3" id="delete-btn">삭제</button>
						</c:if>
					</div>
					<div class="card mb-2">
						<div class="card-header bg-light">
						        <i class="fa fa-comment fa"></i> REPLY
						</div>
						<div class="card-body">
							<ul class="list-group list-group-flush">

							</ul>
						</div>
					</div>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="<c:url value='/js/common.js'/>" ></script> 
<script>
	window.addEventListener('DOMContentLoaded', (e) => {
		e.preventDefault();
		getData();
	});
	
	function getData(){
		$.ajax({
			url : "<c:url value='/api/board/reply/${boardDto.id}'/>",
			method : "get",
			success : function(result) {
				$('.list-group').empty();
				let str = "<li class='list-group-item'> <textarea class='form-control' id='replyContent' rows='3'></textarea> <button type='button' class='btn btn-dark mt-3' id='addReply-btn' onClick='addReply()'>저장</button> </li>";
				result.forEach(data => {
					str += "<li class='list-group-item'><p>" + data.registrant + "    " + data.createdDate + "</p>" + data.content + "</li>"
				})
				$('.list-group').append(str);
			}
		});
	}
	
	document.getElementById('list-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "<c:url value='/board/'/>";
	})

	document.getElementById('modify-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "<c:url value='/board/edit/${boardDto.id}'/>";
	})
	
	document.getElementById('delete-btn').addEventListener('click',(e) => {
		e.preventDefault();
		$.ajax({
			url : "<c:url value='/api/board/${boardDto.id}'/>",
			type : "delete",
			success : function(result){
				location.href = "<c:url value='/board/'/>";
			}
		});
	})
		
	function addReply(){
		const param = {
			"boardId" : $("#boardId").val(),
			"content" : $("#replyContent").val()
		}
		$.ajax({
			url : "<c:url value='/api/board/reply'/>",
			type : "post",
			data : JSON.stringify(param),
			contentType : "application/json",
			success : function(result){
				getData();
			}
		});
	}
	
</script>
</body>
</html>
