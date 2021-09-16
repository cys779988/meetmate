<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
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
				    <form id="form">
				        제목 : <input type="text" name="title"> <br>
				        <div>
				        	<p id="titleError"></p>
				        </div>
				        <br>
				        작성자 : <input type="text" name="writer"> <br>
				        <div>
				        	<p id="writerError"></p>
				        </div>
				        <br>
				        <textarea name="content"></textarea><br>
						<div>
				        	<p id="contentError"></p>
				        </div>
				        <br>
				        <!-- <input type="submit" value="등록"> -->
				        <div class="row" style="float: right;">
					    	<div class="col">
					            <button class="btn btn-primary mb-3" id="add-btn">등록</button>
							</div>
					    	<div class="col">
					            <button class="btn btn-primary mb-3" id="list-btn">목록</button>
							</div>
						</div>
				    </form>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script type="text/javascript" src="/js/common.js" ></script> 
<script>
	document.getElementById('list-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "/board/";
	})

	document.getElementById('add-btn').addEventListener("click", (e)=> {
		e.preventDefault();
		const data = $("#form").serializeObject();
		
		$.ajax({
			url : "/api/board/add",
			method : "post",
			data : JSON.stringify(data),
			contentType : "application/json",
			dataType : "application/json",
			success : function(result) {
				location.href = result;
			},
			error : function(result){
				$('p').empty();
				var messages = JSON.parse(result.responseText);
				
				messages.forEach(error => {
					$('#'+Object.getOwnPropertyNames(error)+'Error').append(Object.values(error));
				});
				
				/* Array.prototype.forEach.call(messages, error => {
					console.log(error);
				});
				
				[].forEach.call(messages, error => {
					
				}); */
			}
		});
	})
</script>
</body>
</html>
