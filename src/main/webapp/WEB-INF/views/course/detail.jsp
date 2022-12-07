<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Course Detail</title>
<style>
#cy {
	position: absolute;
	width: 1630px;
	height: 1300px;
	display: block;
}

h1 {
	opacity: 0.5;
	font-size: 1em;
	font-weight: bold;
}

.tippy-popper {
	transition: none !important;
}
</style>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<nav>
						<div class="mb-3 mt-3 nav nav-tabs" id="nav-tab">
						    <button class="nav-link active" id="basic-tab" data-bs-toggle="tab" data-bs-target="#basic" role="tab" aria-controls="nav-basic" aria-selected="true">기본정보</button>
						    <button class="nav-link" id="basic-tab" data-bs-toggle="tab" data-bs-target="#detail" role="tab" aria-controls="nav-detail" aria-selected="true">상세정보</button>
						</div>
					</nav>
					<div class="tab-content" id="nav-tabContent">
						<div class="tab-pane fade show active" id="basic" role="tabpanel" aria-labelledby="nav-basic-tab">
							<div class="card mb-3" style="border-radius: 15px;">
								<form name="form" id="form" role="form" method="post">
									<div class="card-body">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">제목</h6>
											</div>
											<div class="col-md-11 pe-5">
												<h6 class="mb-0">${courseDto.title}</h6>
												
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">운영일자</h6>
											</div>
											<div class="col-md-11 pe-5">
												<h6 class="mb-0">${courseDto.startDate} ~ ${courseDto.endDate}</h6>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">분류</h6>
											</div>
											<div class="col-md-11 pe-5">
												<h6 class="mb-0">${courseDto.categoryName}</h6>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">현재인원 / 최대인원</h6>
											</div>
											<div class="col-md-11 pe-5">
												<h6 class="mb-0">${courseDto.curNum} / ${courseDto.maxNum}</h6>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5" id="viewer"></div>
										</div>
									</div>
								</form>
							</div>
							<div style="height: 1300px;">
						    	<div class="border border-dark" id="cy"></div>
							</div>
						</div>
						<div class="tab-pane fade" id="detail" role="tabpanel" aria-labelledby="nav-detail-tab">
							<div id="calendar" style="height: 600px;"></div>
						</div>
					</div>
					<div class="mt-3">
						<button class="btn btn-primary mb-3" id="list-btn">목록</button>
						<c:choose>
							<c:when test="${owner}">
								<button class="btn btn-primary mb-3" onclick="modifyData()">수정</button>
								<button class="btn btn-primary mb-3" onclick="deleteData()">삭제</button>
							</c:when>
							<c:otherwise>
								<button class="btn btn-primary mb-3" onclick="apply()">신청</button>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="<c:url value='/js/common.js'/>" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.10/lodash.js"></script>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
<script src="https://unpkg.com/layout-base/layout-base.js"></script>
<script src="https://unpkg.com/cose-base/cose-base.js"></script>
<script src="<c:url value='/js/cytoscape/cytoscape.js'/>"></script>
<script src="https://unpkg.com/@popperjs/core@2/dist/umd/popper.min.js"></script>
<script src="https://unpkg.com/tippy.js@6/dist/tippy-bundle.umd.js"></script>
<script src="https://unpkg.com/avsdf-base/avsdf-base.js"></script>
<script src="https://unpkg.com/klayjs@0.4.1/klay.js"></script>
<script src="https://unpkg.com/dagre@0.7.4/dist/dagre.js"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-popper.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-edgehandles.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-klay.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-avsdf.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-dagre.js'/>"></script>
<script>
console.warn = () => {};

let resizeTimer;

window.addEventListener('resize', function () {
    this.clearTimeout(resizeTimer);
    resizeTimer = this.setTimeout(function(){
        cy.fit();
    },200);
});

function apply() {
	$.ajax({
		url: "<c:url value='/api/group/apply/${courseDto.id}'/>",
		type: 'post',
		success : function(result) {
			location.href = "<c:url value='/course'/>"
		}
	})
}
function modifyData() {
	location.href = "<c:url value='/course/edit/${courseDto.id}'/>"
}

function deleteData() {
	$.ajax({
		url: "<c:url value='/api/course/${courseDto.id}'/>",
		type: 'delete',
		success : function(result) {
			location.href = "<c:url value='/course'/>"
		}
	})
}

var makeTippy = function(ele, text){
	if(!text || !text.trim()){
		return false;
	}
	var ref = ele.popperRef();
	var domEle = document.querySelector('#cy');

	var tip = tippy( domEle, {
		getReferenceClientRect: ref.getBoundingClientRect,
		trigger: 'manual',
		content: function() {
			var div = document.createElement('div');
			div.className = 'tippy';
			div.innerHTML = text;
			return div;
		},
		arrow: true,
		placement: 'bottom',
		hideOnClick: false,
		sticky: 'reference',
		interactive: true,
		trigger: 'mouseenter',
		appendTo: 'parent'
	});
	return tip;
};
document.addEventListener('DOMContentLoaded', function(){
	document.getElementById('list-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "<c:url value='/course'/>";
	})
	
	let data = {
	 	nodes: [],
	  	edges: []
	};
	window.cy_for_rank, window.pageRank, window.cy, window.eh;
    
	function getData() {
		
		return new Promise((resolve, reject) => {
			$.ajax({
				url: "<c:url value='/api/course/${courseDto.id}'/>",
				type: 'get',
				success : function(result) {
					const viewer = new toastui.Editor.factory({
						el: document.querySelector('#viewer'),
						viewer: true,
						initialValue: result.content
					});
					
					data.nodes = result.node;
					data.edges = result.edge;
					cy_for_rank = cytoscape({
				    	elements: data
				    });
					pageRank = cy_for_rank.elements().pageRank();
					data.schedules = result.schedules;
					resolve(data);
				},
				error : function(result){
					reject();
				}
			})
		});
	}
	
	getData().then(data => {
		cy = cytoscape({
	          container: document.getElementById('cy'),
				elements : data,
				style: [
	        		{
	        		selector: 'node:childless',
	        		elements: data.nodes,
	        		style: {
	        			'content': 'data(name)',
	        	        'text-valign': 'center',
	        	        'background-color': 'data(color)',
	        	        'color' : 'black',
	        	        'text-outline-width': 1,
	        	        'text-outline-color': 'data(color)',
	        	        'font-size' : 7,
	        	        'shape' : 'data(shape)',
		                'border-width': 1,
		                'border-color': 'black',
	        	        'width' : 'data(width)',
	        	        'height' :'data(height)'
	                }
	            	},
	            	{
						selector: 'node:parent',
		        		elements: data.nodes,
		        		style: {
		        			'content' : function (ele) {
					    	 	if(ele.data().name === undefined){
					    	 		return '';
					    	 	}
			                    return ele.data().name;
			             	},
		        	        'color' : 'black',
		        	        'background-color': function (ele) {
					    	 	if(ele.data().color === undefined){
					    	 		return 'white';
					    	 	}
			                    return ele.data().color;
			             	},
			             	'shape' : 'barrel',
			                'border-width': 2,
			                'border-color': 'black',
	            	    	'text-valign': 'top',
		        	        'font-size' : 14,
	            	        'text-halign': 'center'
	            	    }
	            	},
		            {
			            selector: 'edge',
			            elements: data.edges,
				        style: {
					    'curve-style': 'bezier',
					    'line-color': 'black',
					    'line-style': function(ele) {
					   	 if(ele.data().line === undefined || ele.data().line === ''){
					   		 return 'solid';
					   	 }
					   	 return ele.data().line;
					    },
					    'target-arrow-shape': function (ele) {
					   	 	if(ele.data().shape === undefined){
					   	 		return 'vee';
					   	 	}
				                  return ele.data().shape;
				           },
					    'target-arrow-color': 'black',
					    'width': 1
						}
			        },
		            {
		              selector: 'node:selected',
		              style: {
			                'border-width': 2,
			                'border-color': 'red'
		              }
		            },
		            {
		              selector: 'edge:selected',
		              style: {
		            	  'target-arrow-color': 'red',
			              'line-color': 'red'
		              }
		            }
				],
				layout: {
		        	name:'preset'
				}
		});
		
		(function createCalendar() {
			FullCalendar.globalLocales.push(function () {
				var ko = {
					code: 'ko',
					buttonText: {
						prev: '이전달',
						next: '다음달',
						today: '오늘',
						month: '월',
						week: '주',
						day: '일',
						list: '일정목록',
					},
					weekText: '주',
					allDayText: '종일',
					moreLinkText: '개',
					noEventsText: '일정이 없습니다',
				};
				return ko;
			}());
			
			const calendarEl = document.getElementById('calendar');
			window.calendar = new FullCalendar.Calendar(calendarEl, {
				themeSystem: 'bootstrap5',
				contentHeight: 600,
				headerToolbar : {
					left : 'prev next today',
					center : 'title',
					right : 'dayGridMonth'
				},
				initialView: 'dayGridMonth',
				events: data.schedules,
				locale: 'ko'
			});
			
			calendar.render();
		})();
		
		let tabEl = document.querySelector('button[data-bs-target="#detail"]');
		tabEl.addEventListener('shown.bs.tab', function (e) {
			e.preventDefault();
			calendar.render();
		})
		tabEl.addEventListener('hidden.bs.tab', function (e) {
			e.preventDefault();
		})
		
	}).then(() => {
	    cy.nodes().forEach(node => {
	    	makeTippy(node, node.data().content);
		});
		eh = cy.edgehandles({
			snap: false
		});
		cy.minZoom(1);
		cy.maxZoom(3);
	})
});
</script>
</body>
</html>