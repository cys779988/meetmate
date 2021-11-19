<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
				    <form action="<c:url value='/api/board/${boardDto.id}'/>">
				        <input type="hidden" name="_method" value="put"/>
				        <input type="hidden" name="id" value="${boardDto.id}"/>
				
				        제목 : <input type="text" name="title" value="${boardDto.title}"> <br>
				        작성자 : <input type="text" name="writer" value="${boardDto.writer}"> <br>
				        <textarea name="content">${boardDto.content}</textarea><br>
				
				        <input type="submit" value="수정">
				    </form>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>

</body>
</html>