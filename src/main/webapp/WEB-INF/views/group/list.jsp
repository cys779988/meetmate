<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<!--IE 호환성-->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--viewport-->
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1, maximum-scal=1, user-scalable=no">
<link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/v3.3.0/tui-pagination.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/grid/v4.19.0/tui-grid.css" />
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<input type="hidden" name="divNo" id="divNo">
				<input type="hidden" name="crseNm" id="crseNm">
				<div class="container-fluid px-4">
					<div class="card mb-4">
						<div class="card-body">
							<div class="row">
						    	<div class="col">
						    		<c:forEach var="course" items="${course}">
						    			<ul class="list-group" id="items">
						    				<li class="list-group-item list-group-item-action">
						    					<a href="#" data-id="${course.id}" data-divno="${course.divclsNo}" onclick="selectCourse(this)">${course.title}</a>
						    				</li>
				    					</ul>
						    		</c:forEach>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="container-fluid px-4">
					<div id="viewPage"></div>
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

class NumRenderer {
    constructor(props) {
		this.el = createEle_num(props);
    }
    getElement() {
      return this.el;
    }
}

const column = [
    {
      header: '번호',
      name: 'num',
      renderer : {
	        type : NumRenderer
	  }
    },
    {
      header: '이름',
      name: 'memberName'
    },
    {
      header: 'email',
      name: 'memberId'
    },
    {
      header: '신청일자',
      name: 'createdDate'
    }
];

function selectCourse(e) {
	const crseId = e.dataset.id;
	$('input[name="crseNm"]').val(e.innerText);
	$('input[name="divNo"]').val(e.dataset.divno);
	if(!e.dataset.divno){
		$("#viewPage").load("<c:url value='/group/basicForm/'/>" + crseId);
	} else {
		$("#viewPage").load("<c:url value='/group/divForm/'/>" + crseId);
	}
}

function updateText(target, data){
	target.empty();
	target.append(data);
}
</script>
</body>
</html>