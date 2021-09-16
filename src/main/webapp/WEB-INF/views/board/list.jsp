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
							            <input name="keyword" type="text" class="form-control" placeholder="검색어를 입력해주세요">
							        </div>
								</div>
								<div class="col">
							        <button id="search-btn" class="btn btn-primary mb-3">검색</button>
							    </div>
								<div class="col">
						        <button class="btn btn-primary mb-3" id="add-btn">글쓰기</button>
						    	</div>
							</div>
						</div>
					</div>
				</div>
		    	<div class="container-fluid px-4">
					<div class="card mb-4">
						<div class="card-header">
						    <i class="fas fa-table me-1"></i>
						    Table
						</div>
						<div class="card-body">
							<div id="grid"></div>
						    <div style="display: inline-block; text-align: center;">
						    	<ul id="page" class="pagination">
						    	</ul>
						    </div>
						</div>
					</div>
				</div>
			</main>
		<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="/js/grid/tui-code-snippet.js"></script>
<script src="/js/grid/tui-pagination.js"></script>
<script src="/js/grid/tui-grid.js"></script>
<script src="/js/grid/grid.js"></script>
<script src="/js/common.js"></script>
<script>
	document.getElementById("search-btn").addEventListener("click", (e) => {
		getBoard(1);
	});
	
	document.getElementById("add-btn").addEventListener("click", (e) => {
		e.preventDefault();
		location.href = "/board/add";
	})
	
	
	class RowNumberRenderer {
		constructor(props) {
	        this.el = createEle_num(props);
		}

		getElement() {
			return this.el;
		}
    }
			const columnData = [{
		         header: '번호',
		         name: 'id',
		         sortingType: 'asc',
		         width: '100',
		         renderer: {
		             type: RowNumberRenderer
	         }
		     },
		     {
		         header: '제목',
		         name: 'title',
		         sortingType: 'asc',
		         sortable: true,
		         width: '100'
		     },
		     {
		         header: '작성자',
		         name: 'writer',
		         sortingType: 'asc',
		         sortable: true,
		         width: '250'
		     },
		     {
		         header: '작성일',
		         name: 'createdDate',
		         sortingType: 'asc',
		         sortable: true,
		         width: '250'
		     }
		 ];
	 
	const dataSource = {
		api: {
			readData: { url: '/api/board/get', method: 'GET', initParams: {keyword: $("input[name = keyword]").val()}}
		},
		contentType: 'application/json'
	};
	
	const grid = createGrid_paging('grid', dataSource, columnData, 'auto', 10);
</script>
</body>
</html>