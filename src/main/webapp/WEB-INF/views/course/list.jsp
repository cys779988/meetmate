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
							        	<select name="search" class="form-select">
							        		<c:forEach items="${category}" var="result">
							        			<option value="${result.id}">${result.name}</option>
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
						    Course
						</div>
						<div class="card-body">
							<div id="grid"></div>
							<button class="btn btn-primary mb-3" id="add-btn">등록</button>
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
	document.getElementById("search-btn").addEventListener("click", (e) => {
		
		grid.readData(1, {search: $("select[name = search]").val()});
	});
	
	document.getElementById("add-btn").addEventListener("click", (e) => {
		e.preventDefault();
		location.href = "<c:url value='/course/add'/>";
	})
	
	
	class RowNumberRenderer {
		constructor(props) {
	        this.el = createEle_num(props);
		}

		getElement() {
			return this.el;
		}
    }
	
	class LinkRenderer {
		constructor(props){
			let func = function(){
				let id = grid.getRow(props.rowKey).id;
				location.href = "<c:url value='/course/'/>" + id;
			};
			
			this.el = createEle_ahref(props,func);
		}
		getElement(){
			return this.el;
		}
		getValue(){
			return this.el.value;
		}
	}
	const columnData = [{
	        header: '번호',
	        name: 'num',
	        renderer: {
	            type: RowNumberRenderer
			}
	    },
	    {
	        header: '제목',
	        name: 'title',
	        sortingType: 'asc',
	        sortable: true,
	        renderer: {
	            type: LinkRenderer
			}
	    },
	    {
	        header: '분류',
	        name: 'categoryName'
	    },
	    {
	        header: '작성자',
	        name: 'registrant'
	    },
	    {
	        header: '현재인원',
	        name: 'curNum'
	    },
	    {
	        header: '최대인원',
	        name: 'maxNum'
	    },
	    {
	        header: '작성일',
	        name: 'createdDate'
	    }
	];
	
	const dataSource = {
		api: {
			readData: { url: "<c:url value='/api/course'/>", method: 'GET'}
		},
		contentType: 'application/json'
	};
	
	const grid = createGrid_paging('grid', dataSource, columnData, 'auto', 10);
	tui.Grid.applyTheme('clean');
</script>
</body>
</html>