<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
				<h2>Board Update</h2>
					<form name="form" id="form" role="form">
						<div class="mb-3">
							<input type="text" class="form-control" name="title" id="title" value="${board.title}" placeholder="제목">
						    <div>
						    	<p class="errorText" id="titleError"></p>
						    </div>
						</div>
						<div class="mb-3">
							<textarea class="form-control" rows="5" name="content" id="content" placeholder="내용">${board.content}</textarea>
							<div>
						    	<p class="errorText" id="contentError"></p>
						    </div>
						</div>
					</form>
					<div>
						<div id="attachFile"></div>
						<ul class="list-group">
						<c:forEach items="${board.fileList}" var="item">
							<li class="list-group-item d-flex justify-content-between align-items-center">
								${item.originalFileName} (<fmt:formatNumber type="number" maxFractionDigits="0"  value="${item.fileSize / 1000}" />KB)
								<a href="#" class="badge badge-danger" data-id="${item.id}" onclick="deleteFile(this)">삭제</a>
							</li>
						</c:forEach>
						</ul>
					</div>
					<div class="mt-5">
						<button class="btn btn-primary mb-3" id="regist-btn">등록</button>
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
const deleteFiles = [];
function deleteFile(_this, fileId) {
	event.preventDefault();
	deleteFiles.push(_this.dataset.id);
	_this.parentElement.remove();
}

window.addEventListener('DOMContentLoaded', () => {
	guManager = new guUploadManager({
		fileid: "attachFile",
		maxFileSize: 1,
		maxFileCount: 3,
		useButtons: true
	});
	
	document.getElementById('regist-btn').addEventListener("click", (e) => {
		e.preventDefault();
		const data = $("#form").serializeObject();
		data.deleteFiles = deleteFiles;
		
		const formData = new FormData();
		
		if(guManager.uploader.fileCount > 0) {
		    Object.keys(guManager.uploader.fileList).forEach(key => {
				formData.append('files', guManager.uploader.fileList[key].file);
			});
		}
		
		Object.keys(data).forEach(key => {
			formData.append(key, data[key]);
		})
		
		deleteFiles.forEach(fileId => {
			formData.append('deleteFiles', fileId);
		})
		
		$.ajax({
			url : "<c:url value='/api/board/${board.id}'/>",
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