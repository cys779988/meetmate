<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Course Update</title>
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
				<h2>Course Regist</h2>
					<div class="mb-3 mt-3">
						<ul class="nav nav-tabs" id="myTab">
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
							<form name="form" id="form" role="form" method="post">
								<div class="mb-3">
									<input type="text" class="form-control" name="title" id="title" value="${courseDto.title}" placeholder="제목">
								    <div>
								    	<p id="titleError"></p>
								    </div>
								</div>
								<div class="mb-3">
									<label for="category" class="form-label">분류</label>
									<select class="form-control" name="category" id="category">
										<c:forEach var="category" items="${category}">
											<option value="${category.id}" ${courseDto.category eq category.id ? "selected":""}>${category.name}</option>
										</c:forEach>
									</select>
									<br>
								    <div>
								    	<p id="typeError"></p>
								    </div>
								</div>
								<div class="row g-3">
									<div class="col-auto">
										<input type="number" class="form-control" name="divclsNo" id="divclsNo" value="${courseDto.divclsNo}" placeholder="분반">
									</div>
									<div class="col-auto">
										<input type="text" class="form-control" name="maxNum" id="maxNum" value="${courseDto.maxNum}" placeholder="최대인원">
									</div>
								    <div>
								    	<p id="maxNumError"></p>
								    </div>
								</div>
								<div class="mb-3">
									<textarea class="form-control" rows="5" name="content" id="content" placeholder="내용">${courseDto.content}</textarea>
									<div>
								    	<p id="contentError"></p>
								    </div>
								</div>
							</form>
						</div>
						<div class="tab-pane fade show active" id="detail">
							<div style="height: 900px;">
						    	<div class="border border-dark" id="cy"></div>
							</div>
						    <div>
								<div class="row g-3 mt-3">
									<div class="col-auto">
										<input type="text" class="form-control" id="addName1" placeholder="Node Name" maxlength="10">
									</div>
									<div class="col-auto">
										<input type="text" class="form-control" id="addContent1" placeholder="Node Content" maxlength="20">
									</div>
									<div class="col-auto">
										<input type="number" class="form-control" id="addSize1" placeholder="Node Size" maxlength="2">
									</div>
									<div class="col-auto">
								        <select class="form-select" id="addShape1">
								        	<option value="ellipse" selected="selected">원</option>
								        	<option value="triangle">삼각형</option>
								        	<option value="rectangle">사각형</option>
								        	<option value="diamond">마름모</option>
								        	<option value="star">별</option>
								        </select>
									</div>
								    <div class="col-auto">
								      <button class="btn btn-outline-dark" id="sipNode-add-btn">노드추가</button>
								      <button class="btn btn-outline-dark" id="drawOn-btn">그리기모드 on</button>
								      <button class="btn btn-outline-dark" id="drawOff-btn">그리기모드 off</button>
								    </div>
									<div class="col-auto">
	                                   	<select class="form-select" id="selectLayout">
	                                   		<option value="0" selected="selected">layout1</option>
	                                   		<option value="1">layout2</option>
	                                   		<option value="2">layout3</option>
	                                   		<option value="3">layout4</option>
	                                   	</select>
                                   	</div>
									<div class="col-auto">
								        <input id="remove-1ch-parents" type="checkbox" value="false" />
								        <label for="remove-1ch-parents">자식노드 1개인 부모노드 제거</label>
								    </div>
								</div>
						    </div>
						    <div id="myModal" class="modal" tabindex="-1">
						    	<div class="modal-dialog">
								    <div class="modal-content">
								      <div class="modal-header">
								        <h5 class="modal-title">MindMap</h5>
								        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
								      </div>
								      <div class="modal-body">
								      	<div class="mb-3 mt-3">
											<ul class="nav nav-tabs">
											  <li class="nav-item">
											    <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#modifyTab">수정</button>
											  </li>
											  <li class="nav-item">
											    <button class="nav-link" data-bs-toggle="tab" data-bs-target="#registTab">등록</button>
											  </li>
											</ul>
										</div>
										<div class="tab-content">
											<div class="tab-pane fade show active" id="modifyTab">
												<div class="clearfix mb10">
						                            <button type="button" class="btn btn-secondary" id="modifyNode-btn">저장</button>
	       											<button type="button" class="btn btn-primary" id="deleteNode-btn">삭제</button>
						                        </div>
						                        <form id="modifyForm">
						                        	<input type="hidden" id="id">
							                        <table class="vertical mb16 tal">
							                            <colgroup>
							                                <col style="width:30%">
							                                <col style="width:70%">
							                            </colgroup>
							                            <tbody>
							                                <tr>
							                                    <th>name</th>
							                                    <td colspan="3">
							                                        <input type="text" class="form-control" id="nodeId" maxlength="10">
							                                    </td>
							                                </tr>
							                                <tr>
							                                    <th>content</th>
							                                    <td colspan="3">
							                                        <input type="text" class="form-control" id="nodeContent" maxlength="20">
							                                    </td>
							                                </tr>
							                                <tr>
							                                    <th>size</th>
							                                    <td colspan="3">
							                                        <input type="number" class="form-control" id="nodeSize" maxlength="2">
							                                    </td>
							                                </tr>
							                                <tr>
							                                    <th>shape</th>
							                                    <td colspan="3">
							                                    	<select class="form-select" id="nodeShape">
							                                    		<option value="ellipse" selected="selected">원</option>
							                                    		<option value="triangle">삼각형</option>
							                                    		<option value="rectangle">사각형</option>
							                                    		<option value="diamond">마름모</option>
							                                    		<option value="star">별</option>
							                                    	</select>
							                                    </td>
							                                </tr>
							                            </tbody>
							                        </table>
						                        </form>
											</div>
											<div class="tab-pane fade" id="registTab">
												<div class="clearfix mb10">
						                            <button type="button" class="btn btn-secondary" id="addNode-btn">저장</button>
						                        </div>
						                        <form id="addForm">
							                        <input type="hidden" id="id">
							                        <table class="vertical mb16 tal">
							                            <colgroup>
							                                <col style="width:30%">
							                                <col style="width:70%">
							                            </colgroup>
							                            <tbody>
							                                <tr>
							                                    <th>name</th>
							                                    <td colspan="3">
							                                        <input type="text" class="form-control" id="addId" maxlength="10">
							                                    </td>
							                                </tr>
							                                <tr>
							                                    <th>content</th>
							                                    <td colspan="3">
							                                        <input type="text" class="form-control" id="addContent" maxlength="20">
							                                    </td>
							                                </tr>
							                                <tr>
							                                    <th>size</th>
							                                    <td colspan="3">
							                                        <input type="number" class="form-control" id="addSize" maxlength="2">
							                                    </td>
							                                </tr>
							                                <tr>
							                                    <th>shape</th>
							                                    <td colspan="3">
							                                    	<select class="form-select" id="addShape">
							                                    		<option value="ellipse" selected="selected">원</option>
							                                    		<option value="triangle">삼각형</option>
							                                    		<option value="rectangle">사각형</option>
							                                    		<option value="diamond">마름모</option>
							                                    		<option value="star">별</option>
							                                    	</select>
							                                    </td>
							                                </tr>
							                            </tbody>
							                        </table>
												</form>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="mb-3 mt-3">
						<button class="btn btn-primary mb-3" id="add-btn">등록</button>
						<button class="btn btn-primary mb-3" id="list-btn">목록</button>
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
<script src="<c:url value='/js/cytoscape/cytoscape-compound.js'/>"></script>
<%-- <script src="<c:url value='/js/cytoscape/cytoscape-tippy.js'/>"></script> --%>
<script>
	const myModal = new bootstrap.Modal(document.getElementById('myModal'), {
	  keyboard: false
	})
	
	document.addEventListener('DOMContentLoaded', function(){
		let data = {
		 	nodes: [],
		  	edges: []
		};
		window.cy_for_rank, window.pageRank, window.cy, window.eh, window.tippyEl = [];
	    
		function getData() {
			return new Promise((resolve, reject) => {
				$.ajax({
					url: "<c:url value='/api/course/${courseDto.id}'/>",
					type: 'get',
					success : function(result) {
						$('#title').value = result.title;
						data.nodes = result.node;
						data.edges = result.edge;
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
			                'border-width': 12,
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
			document.querySelector('#detail').classList.remove('show', 'active');
		}).then(() => {
			cy.nodes().forEach(node => {
			    	makeTippy(node, node.data().content);
			});
			eh = cy.edgehandles({
				snap: false
			});
			cy.userZoomingEnabled(false);
			
			window.layoutArr = [
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
			window.cyLayout = function cyLayout(){
				let selectLayout = document.getElementById('selectLayout').value;
				
				let layout = cy.layout(layoutArr[selectLayout]);
				layout.run();
			};
			cyLayout();
			
			cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
				let { position } = event;
				console.log(event);
				console.log(sourceNode);
				console.log(targetNode);
				console.log(addedEdge);
			});
				
			cy.on('tap', 'node', e => {
				var node = e.target;
				document.getElementById('id').value = node.id();
				myModal.show();
				document.getElementById('nodeId').value = e.target._private.data.name;
				document.getElementById('nodeContent').value = e.target._private.data.content;
				document.getElementById('nodeShape').value = e.target._private.data.shape;
				document.getElementById('nodeSize').value = e.target._private.data.size;
			});
			
			cy.on('tap', 'edge', e => {
				let check = confirm("Edge를 삭제하시겠습니까?");
				if(check){
					cy.remove(e.target);
				}
			});
			
			
			var cdnd = cy.compoundDragAndDrop();
	        var removeEmptyParents = false;

	        var isParentOfOneChild = function(node){
	          return node.isParent() && node.children().length === 1;
	        };

	        var removeParent = function(parent){
	          parent.children().move({ parent: null });
	          parent.remove();
	        };

	        var removeParentsOfOneChild = function(){
	          cy.nodes().filter(isParentOfOneChild).forEach(removeParent);
	        };

	        // custom handler to remove parents with only 1 child on drop
	        cy.on('cdndout', function(event, dropTarget){
	          if( removeEmptyParents && isParentOfOneChild(dropTarget) ){
	            removeParent(dropTarget);
	          }
	        });

	        // custom handler to remove parents with only 1 child (on remove of drop target or drop sibling)
	        cy.on('remove', function(event){
	          if( removeEmptyParents ){
	            removeParentsOfOneChild();
	          }
	        });
	        document.getElementById('remove-1ch-parents').addEventListener('click', function(){
	            removeEmptyParents = !removeEmptyParents;

	            if( removeEmptyParents ){
	              removeParentsOfOneChild();
	            }
			});
		})
	});
		
	let resizeTimer;

	window.addEventListener('resize', function () {
	    this.clearTimeout(resizeTimer);
	    resizeTimer = this.setTimeout(function(){
	        cy.fit();
	    },200);
	});
	
	var makeTippy = function(ele, text){
		if(!text || !text.trim()){
			return false;
		}
		var ref = ele.popperRef();
		// Since tippy constructor requires DOM element/elements, create a placeholder
		var domEle = document.querySelector('#cy');

		var tip = tippy( domEle, {
			getReferenceClientRect: ref.getBoundingClientRect,
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
		tip.id = ele.id();
		tippyEl.push(tip);
		return tip;
	};

	document.getElementById('list-btn').addEventListener('click',(e) => {
		e.preventDefault();
		location.href = "<c:url value='/course/'/>";
	})

	document.getElementById('add-btn').addEventListener("click", (e)=> {
		e.preventDefault();
		const param = $("#form").serializeObject();
		param.node = []
		param.edge = []
	    
	    cy.nodes().forEach(e=> {
	    	let data = { "data" : e._private.data, "renderedPosition" : e._private.position }
		  	param.node.push(data);
	    })
	    cy.edges().forEach(e=> {
	    	let data = { "data" : e._private.data }
		  	param.edge.push(data);
	    })
		$.ajax({
			url : "<c:url value='/api/course/${courseDto.id}'/>",
			method : "put",
			data : JSON.stringify(param),
			contentType : "application/json",
			success : function(result) {
				location.href = "<c:url value='/course/'/>";
			},
			error : function(result){
				$('p').empty();
				var messages = JSON.parse(result.responseText);
				
				messages.forEach(error => {
					$('#'+Object.getOwnPropertyNames(error)+'Error').append(Object.values(error));
				});
				let firstTabEl = document.querySelector('#myTab button[data-bs-target="#basic"]')
			    let firstTab = new bootstrap.Tab(firstTabEl);
			    
			    firstTab.show();
			}
		});
	})
	
	document.getElementById('modifyNode-btn').addEventListener('click', e => {
		let id = document.getElementById('id').value;
		let nodeId = document.getElementById('nodeId').value;
		let content = document.getElementById('nodeContent').value;
		let shape = document.getElementById('nodeShape').value;
		let size = document.getElementById('nodeSize').value;
		let node = cy.$('#' + id);
		node.data('name', nodeId)
		.data('content', content)
		.data('shape', shape)
		.data('size', size);
		
		tippyEl.find(data => {if(data.id === id) return true})
				.popper._tippy.setContent(content);
		document.getElementById('modifyForm').reset();
		myModal.hide();
	});
    
	document.getElementById('addNode-btn').addEventListener('click', e => {
		var node = cy.add({
			group: 'nodes',
			data: {name: document.getElementById('addId').value
				, content : document.getElementById('addContent').value
				, shape : document.getElementById('addShape').value
				, size : document.getElementById('addSize').value ? document.getElementById('addSize').value : 30}
		});
		
		cy.add({
			group: 'edges',
			data: {target: node.id(), source: document.getElementById('id').value}
		})

      	cyLayout();
		makeTippy(node, node.data().content);
		document.getElementById('addForm').reset();
		myModal.hide();
	});

    document.getElementById('deleteNode-btn').addEventListener('click', e => {
		let id = document.getElementById('id').value;
		cy.remove(cy.$('#' + id));
		tippyEl.find(data => {if(data.id === id) return true}).destroy();
		myModal.hide();
	});
	document.querySelector('#drawOn-btn').addEventListener('click', function() {
		eh.enableDrawMode();
	});

	document.querySelector('#drawOff-btn').addEventListener('click', function() {
		eh.disableDrawMode();
	});

	document.querySelector('#sipNode-add-btn').addEventListener('click', function() {
		if(!document.getElementById('addName1').value){
			alert('Node Name을 입력해주세요.')
			return false;
		}
		let node = cy.add({
			group: 'nodes',
			data: {name: document.getElementById('addName1').value
				, content : document.getElementById('addContent1').value
				, shape : document.getElementById('addShape1').value
				, size : document.getElementById('addSize1').value ? document.getElementById('addSize1').value : 30}
		});
		cyLayout();
		makeTippy(node, node.data().content);
	});

    let tabEl = document.querySelector('button[data-bs-target="#detail"]')
    tabEl.addEventListener('shown.bs.tab', function (e) {
    	e.preventDefault();
    })
    tabEl.addEventListener('hidden.bs.tab', function (e) {
    	e.preventDefault();
    })
    
	document.querySelector('#selectLayout').addEventListener('change', function() {
    	cyLayout();
    })
</script>
</body>
</html>
