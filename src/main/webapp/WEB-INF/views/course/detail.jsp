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
	width: 1600px;
	height: 900px;
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
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<input type="hidden" value="${courseDto.id}" id="boardId">
					<h2>${courseDto.title}</h2>
					<p>[${courseDto.createdDate}]</p>
				    <p>등록자 : ${courseDto.registrant}</p>
					<div class="mb-3 mt-3">
						<ul class="nav nav-tabs">
						  <li class="nav-item">
						    <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#basic">기본정보</button>
						  </li>
						  <li class="nav-item">
						    <button class="nav-link" data-bs-toggle="tab" data-bs-target="#detail">상세정보</button>
						  </li>
						</ul>
					</div>
					<div class="tab-content">
						<div class="tab-pane fade show active" id="basic">
						    <p>(현재인원 / 최대인원) ${courseDto.curNum} / ${courseDto.maxNum}</p>
							<div class="mb-3" style="min-height: 500px;">
								<label for="content"></label>
								<p>${courseDto.content}</p>
							</div>
						</div>
						<div class="tab-pane fade show active" id="detail">
							<div style="height: 900px;">
						    	<div class="border border-dark" id="cy"></div>
							</div>
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
					<div class="card mb-2">
						<div class="card-header bg-light">
						        <i class="fa fa-comment fa"></i> REPLY
						</div>
						<div class="card-body">
							<ul class="list-group list-group-flush">
		
							</ul>
						</div>
					</div>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="<c:url value='/js/common.js'/>" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.10/lodash.js"></script>
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
<script src="<c:url value='/js/cytoscape/cytoscape-euler.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-klay.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-avsdf.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-dagre.js'/>"></script>
<%-- <script src="<c:url value='/js/cytoscape/cytoscape-tippy.js'/>"></script> --%>
<script>
	document.getElementById('list-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "<c:url value='/course/'/>";
	})
	
	document.addEventListener('DOMContentLoaded', function(){
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
						data.nodes = result.node;
						data.edges = result.edge;
						cy_for_rank = cytoscape({
					    	elements: data
					    });
						pageRank = cy_for_rank.elements().pageRank();
						resolve(data);
					},
					error : function(result){
						reject();
					}
				})
			});
		}
		
		getData().then(data => {
			const nodeMaxSize = 10;
			const nodeMinSize = 5;
			const fontMaxSize = 0.5;
			const fontMinSize = 5;
			cy = cytoscape({
		          container: document.getElementById('cy'),
					elements : data,
					style: [
		        		{
			        	selector: ':childless',
			        	elements: data.nodes,
			        	style: {
			        		'content': 'data(name)',
			        	    'text-valign': 'center',
			        	    'color': 'white',
			        	    'text-outline-width': 1,
			        	    'text-outline-color': '#dd4de2',
			        	    'font-size' : 7,
			        	    'shape' : 'data(shape)',
			        	    'width' : 'data(size)',
			        	    'height' : 'data(size)'
			            }
			            },
			            {
							selector: ':parent',
			        		elements: data.nodes,
			                css: {
			                	'text-valign': 'top',
			                    'text-halign': 'center',
			                }
			            },
			            {
			              selector: 'edge',
			              elements: data.edges,
			              style: {
			                'curve-style': 'bezier',
			                'target-arrow-shape': 'triangle'
			              }
			            },
			            {
			              selector: '.eh-handle',
			              style: {
			                'background-color': 'red',
			                'width': 12,
			                'height': 12,
			                'shape': 'ellipse',
			                'overlay-opacity': 0,
			                'border-width': 12, // makes the handle easier to hit
			                'border-opacity': 0
			              }
			            },
			
			            {
			              selector: '.eh-hover',
			              style: {
			                'background-color': 'red'
			              }
			            },
			
			            {
			              selector: '.eh-source',
			              style: {
			                'border-width': 2,
			                'border-color': 'red'
			              }
			            },
			
			            {
			              selector: '.eh-target',
			              style: {
			                'border-width': 2,
			                'border-color': 'red'
			              }
			            },
			
			            {
			              selector: '.eh-preview, .eh-ghost-edge',
			              style: {
			                'background-color': 'red',
			                'line-color': 'red',
			                'target-arrow-color': 'red',
			                'source-arrow-color': 'red'
			              }
			            },
			
			            {
			              selector: '.eh-ghost-edge.eh-preview-active',
			              style: {
			                'opacity': 0
			              }
			            }
			          ],
			          layout: {
			        	  name:'preset'
			          }
			
			});
			
		}).then(() => {
		    cy.nodes().forEach(node => {
		    	makeTippy(node, node.data().content);
			});
			eh = cy.edgehandles({
				snap: false
			});
			cy.userZoomingEnabled(false);
/* 			window.layoutArr = [
				{
				    name: 'dagre'
				},
				{
				    name: 'avsdf',
					animate: "during",
					animationDuration: 1000,
					animationEasing: 'ease-in-out',
					nodeSeparation: 120
				},
				{
				    name: 'klay'
				},
				{
				    name: 'euler',
					randomize: true,
					animate: false
			}];
			window.cyLayout = cy.layout(layoutArr[data.layout]);
			cyLayout.run(); */
			document.querySelector('#detail').classList.remove('show', 'active');
		})
	});
	
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
				location.href = "<c:url value='/course/'/>"
			},
			error : function(error) {
				alert(error.responseJSON.message);
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
				location.href = "<c:url value='/course/'/>"
			}
		})
	}
	
	var makeTippy = function(ele, text){
		if(!text || !text.trim()){
			return false;
		}
		var ref = ele.popperRef();
		// Since tippy constructor requires DOM element/elements, create a placeholder
		var domEle = document.querySelector('#cy');

		var tip = tippy( domEle, {
			getReferenceClientRect: ref.getBoundingClientRect,
			trigger: 'manual', // mandatory
			// dom element inside the tippy:
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

    let tabEl = document.querySelector('button[data-bs-target="#detail"]')
    tabEl.addEventListener('shown.bs.tab', function (e) {
    })
    tabEl.addEventListener('hidden.bs.tab', function (e) {
    })
</script>
</body>
</html>