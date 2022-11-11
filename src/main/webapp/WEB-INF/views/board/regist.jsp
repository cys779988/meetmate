<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<title>Title</title>
	<link rel="stylesheet" href="<c:url value='/css/guupload.css'/>" />
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
				<h2>Board Regist</h2>
					<form name="form" id="form" role="form">
						<div class="mb-3">
							<input type="text" class="form-control" name="title" id="title" placeholder="제목">
						    <div>
						    	<p class="errorText" id="titleError"></p>
						    </div>
						</div>
						<div class="mb-3">
							<textarea class="form-control" rows="5" name="content" id="content" placeholder="내용"></textarea>
							<div>
						    	<p class="errorText" id="contentError"></p>
						    </div>
						</div>
					</form>
					<div id="attachFile"></div>
					<div class="mt-5">
						<button class="btn btn-primary mb-3" id="add-btn">등록</button>
						<button class="btn btn-primary mb-3" id="list-btn">목록</button>
					</div>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="<c:url value='/js/guupload/guuploadManager.js'/>" ></script>
<script src="<c:url value='/js/common.js'/>" ></script>
<script>
window.addEventListener('DOMContentLoaded', () => {
	guManager = new guUploadManager({
		fileid: "attachFile",
		maxFileSize: 1,
		maxFileCount: 3,
		useButtons: true
	});
	
	document.getElementById('list-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "<c:url value='/board/'/>";
	})

	document.getElementById('add-btn').addEventListener("click", (e)=> {
		e.preventDefault();
		const data = $("#form").serializeObject();
		
		const formData = new FormData();
		
		if(guManager.uploader.fileCount > 0) {
		    Object.keys(guManager.uploader.fileList).forEach(key => {
				formData.append('files', guManager.uploader.fileList[key].file);
			});
		}
		
		Object.keys(data).forEach(key => {
			formData.append(key, data[key]);
		})
		
		$.ajax({
			url : "<c:url value='/api/board/'/>",
			method : "post",
			data : formData,
			enctype : 'multipart/form-data',
			processData : false,
			contentType : false,
			success : function(result) {
				console.log(result);
				location.href = "<c:url value='/board/'/>";
			}
		});
	})
});
</script>
</body>
</html>
