<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card mb-4">
	<div class="card-header">
		<div class="mb10 mt10">
			<h3 id="title"></h3>
		</div>
	</div>
	<div class="card-body">
		<div class="align-right">
			<button class="btn btn-primary" id="delete-btn">삭제</button>
			<button class="btn btn-primary" id="stdnt-regist-btn">저장</button>
		</div>
		<div class="row mt-3">
			<div class="card mb-4">
				<div class="card-header">
					<span class="lists">총<span id="totCnt"></span></span>
				</div>
				<div class="card-body">
					<div id="grid1"></div>
				</div>
			</div>
		</div>						
	</div>
</div>
<script>
window.addEventListener('DOMContentLoaded', e => {
});

window.grid1 = createGrid_scroll('grid1', '', column, 400);

tui.Grid.applyTheme('clean');

(function getData() {
	const crseNm = $('input[name="crseNm"]').val();
	$.ajax({
		url : "<c:url value='/api/group/${id}'/>", 
		method : 'get',
		success : function(result) {
			result.unassignment ??= [];
			grid1.resetData(result.unassignment);
			updateText($('#totCnt'), result.unassignment.length + '명');
			$('#title').text('Course ' + crseNm);
			
		},
		error : function(req, status, err) {
			console.log(req);
			console.log(status);
			console.log(err);
		}
	});
})();

document.getElementById('stdnt-regist-btn').addEventListener('click', e => {
	e.preventDefault();
	
	let param = [];
	updatedData.forEach(data => {
		param.push(data);
	})
	$.ajax({
		url : 'updateDivClass.do',
		type : 'POST',
		data : JSON.stringify(param),
		contentType :'application/json',
		success : function(result) {
			getData(getCrseData());
			alert('저장이 완료되었습니다.')
		},
		error : function(request,status,error){
			console.log(request);
			console.log(status);
			console.log(error);
		}
	})
})
</script>