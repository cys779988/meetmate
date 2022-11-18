<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>study</title>
<link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/v3.3.0/tui-pagination.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/grid/v4.19.0/tui-grid.css" />
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<div class="card mb-4">
						<div class="card-body">
							<div class="row">
						    	<div class="col">
							        <div class="search">
							        	<select class="form-control" id="search" name="search">
							        		<c:forEach var="item" items="${code}">
							        			<option value="${item.getKey()}">${item.getValue()}</option>
											</c:forEach>
							        	</select>
							        </div>
								</div>
								<div class="col">
							        <button id="search-btn" class="btn btn-primary mb-3">검색</button>
							    </div>
							</div>
						</div>
					</div>
				</div>
		    	<div class="container-fluid px-4">
					<div class="card mb-4">
						<div class="card-header">
						    <i class="fas fa-table me-1"></i>
						    Code
						</div>
						<div class="card-body">
							<div id="grid"></div>
							<button class="btn btn-primary mb-3" id="append-btn">항목추가</button>
							<button class="btn btn-primary mb-3" id="remove-btn">항목삭제</button>
							<button class="btn btn-primary mb-3" id="add-btn">저장</button>
						</div>
					</div>
				</div>
			</main>
		<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="<c:url value='/js/grid/tui-code-snippet.js'/>"></script>
<script src="<c:url value='/js/grid/tui-pagination.js'/>"></script>
<script src="<c:url value='/js/grid/tui-grid.js'/>"></script>
<script src="<c:url value='/js/grid/grid.js'/>"></script>
<script src="<c:url value='/js/common.js'/>"></script>
<script>
window.addEventListener('DOMContentLoaded', () => {
	const columnData = [
	    {
			header: '타입',
			name: 'type',
			editor: {
				type : 'select',
				options : {
					listItems: [
						<c:forEach var="item" items="${code}">
							{text: '${item.getValue()}', value: '${item.getKey()}'}
						</c:forEach>
					]
				}
	    	}
	    },
		{
			header: '코드명',
			name: 'name',
			editor: {
				type : 'text'
			}
		},
		{
			header: '등록자',
			name: 'registrant'
		},
		{
			header: '등록일시',
			name: 'createdDate'
		}
	];
	
	const dataSource = {
		api: {
			readData: { url: "<c:url value='/admin/api/code/grid'/>", method: 'GET', initParams: {search: $('#search').val()}}
		},
		contentType: 'application/json'
	};
	
	const grid = createGrid_paging('grid', dataSource, columnData, 'auto', 10, 'checkbox');
	tui.Grid.applyTheme('clean');
	
	document.getElementById("search-btn").addEventListener("click", (e) => {
		grid.readData(1, {search: $("#search").val()});
	});
	
	document.getElementById("append-btn").addEventListener("click", (e) => {
		grid.appendRow();
	});
	
	document.getElementById("remove-btn").addEventListener("click", (e) => {
		const param = {
			"rows" : grid.getCheckedRows()
		}
		$.ajax({
			url : "<c:url value='/admin/api/code'/>",
			method : "delete",
			data : JSON.stringify(param),
			contentType : "application/json",
			success : function(result) {
				grid.removeCheckedRows();
			}
		});
	});
	
	document.getElementById("add-btn").addEventListener("click", (e) => {
		grid.blur();
		const modifiedRows = grid.getModifiedRows();
		const param = modifiedRows.createdRows.concat(modifiedRows.updatedRows);
		
		$.ajax({
			url : "<c:url value='/admin/api/code'/>",
			method : "post",
			data : JSON.stringify({"rows" : param}),
			contentType : "application/json",
			success : function(result) {
				grid.reloadData();
			}
		});
	});
	
});
</script>
</body>
</html>