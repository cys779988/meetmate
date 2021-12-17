<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Course Regist</title>
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

.sticky {
	width: 85.65%;
	position: fixed;
	top: 56px;
	z-index: 9999999;
}

</style>
<link rel="stylesheet" href="<c:url value='/css/toolbar.css'/>"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-colorpicker/3.4.0/css/bootstrap-colorpicker.css" integrity="sha512-HcfKB3Y0Dvf+k1XOwAD6d0LXRFpCnwsapllBQIvvLtO2KMTa0nI5MtuTv3DuawpsiA0ztTeu690DnMux/SuXJQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<h2>Course Regist</h2>
						<form name="form" id="form" role="form" method="post">
							<div class="mb-3">
								<input type="text" class="form-control" name="title" id="title" placeholder="제목">
							    <div>
							    	<p id="titleError"></p>
							    </div>
							</div>
							<div class="mb-3">
								<label for="category" class="form-label">분류</label>
								<select class="form-control" name="category" id="category">
									<option value="1">스터디</option>
									<option value="2">모임</option>
								</select>
								<br>
							    <div>
							    	<p id="typeError"></p>
							    </div>
							</div>
							<div class="row g-3">
								<div class="col-auto">
									<input type="number" class="form-control" name="divclsNo" id="divclsNo" placeholder="분반">
								</div>
								<div class="col-auto">
									<input type="text" class="form-control" name="maxNum" id="maxNum" placeholder="최대인원">
								</div>
							    <div>
							    	<p id="maxNumError"></p>
							    </div>
							</div>
							<div class="mb-3">
								<textarea class="form-control" rows="5" name="content" id="content" placeholder="내용"></textarea>
								<div>
							    	<p id="contentError"></p>
							    </div>
							</div>
						</form>
						<div id="toolbar" class="mb-2">
							<div class="toolbar">
								<div class="tool_wrap mr10">
						            <div class="toggle-switch">
						                <input id="drawMode" type="checkbox">
						                <label for="drawMode">
						                    <span class="toggle-track"></span>
						                </label>
						            </div>
						            <span>그리기모드</span>
						        </div>
							    <div class="tool_wrap">
							        <ul class="tool_box clearfix">
							            <li data-layout="0" onclick="changeLayout(this)" class="tool">
											<img class="rotate_180" src="<c:url value='/images/mindmap/layout_02.png'/>" alt="레이아웃_01">
							            </li>
							            <li data-layout="1" onclick="changeLayout(this)" class="tool" >
											<img src="<c:url value='/images/mindmap/layout_01.png'/>" alt="레이아웃_02">
							            </li>
							            <li data-layout="2" onclick="changeLayout(this)" class="tool">
											<img src="<c:url value='/images/mindmap/layout_03.png'/>" alt="레이아웃_03">
							            </li>
							        </ul>
							        <span>레이아웃</span>
							    </div>
							    <div class="tool_wrap">
							        <div class="tool_btn">
							             <a href="#" id="sipNode-add-btn">
							                 <img src="<c:url value='/images/mindmap/tool_add.png'/>" alt="하위노드추가">
							             </a>
							        </div>
							        <span>하위노드 추가</span>
							    </div>
							    <div class="tool_wrap">
							        <div class="tool_btn">
							            <a href="#" id="delete-btn">
							                <img src="<c:url value='/images/mindmap/tool_remove.png'/>" alt="삭제">
							            </a>
							        </div>
							        <span>삭제</span>
							    </div>
							    <div class="tool_wrap">
							        <div class="tool_btn">
							            <a href="#" id="form-reset-btn">
							                <img src="<c:url value='/images/mindmap/tool_reset.png'/>" alt="초기화">
							            </a>
							        </div>
							        <span>선택 초기화</span>
							    </div>

							    <div class="tool_wrap mr10">
							        <ul class="tool_box clearfix">
							            <li onclick="updateNode(this)" data-id="shape" data-shape="ellipse" class="tool">
											<img src="<c:url value='/images/mindmap/shape_01.png'/>" alt="원">
							            </li>
							            <li onclick="updateNode(this)" data-id="shape" data-shape="round-rectangle" class="tool">
							            	<img src="<c:url value='/images/mindmap/shape_02.png'/>" alt="사각형">
							            </li>
							            <li onclick="updateNode(this)" data-id="shape" data-shape="round-diamond" class="tool">
							            	<img src="<c:url value='/images/mindmap/shape_03.png'/>" alt="마름모">
							            </li>
							            <li onclick="updateNode(this)" data-id="shape" data-shape="round-hexagon" class="tool">
							            	<img src="<c:url value='/images/mindmap/shape_04.png'/>" alt="육각형">
							            </li>
							        </ul>
							        <span>도형</span>
							    </div>
								<form id="mindmap" name="mindmap">
							        <div class="tool_wrap">
							            <input type="color" class="color_picker" id="nodeColor" onchange="updateNode(this)" data-id="color" value="#FFFFFF">
							            <span>색상</span>
							        </div>
							        <span class="line">구분선</span>
							        <span class="tool_shapes disabled">
								        <div class="tool_wrap">
											<input type="text"  id="addName" onblur="updateNode(this)" onkeyup="enterKey(this)" data-id="name" maxlength="10">
								            <span>Node Name</span>
								        </div>
								        <div class="tool_wrap">
								            <input type="text" id="addContent" onblur="updateNode(this)" onkeyup="enterKey(this)" data-id="content" maxlength="20">
								        	<span>Node Content</span>
								        </div>
								        <div class="tool_wrap">
								        	<input type="number" id="addWidth" class="w60" onblur="updateNode(this)" oninput="maxLengthCheck(this)" onkeyup="enterKey(this)" data-id="width" maxlength="3">
								        	<span>Node Width</span>
								        </div>
								        <div class="tool_wrap">
								        	<input type="number" id="addHeight" class="w60" onblur="updateNode(this)" oninput="maxLengthCheck(this)" onkeyup="enterKey(this)" data-id="height" maxlength="3">
								        	<span>Node Height</span>
								        </div>
									</span>
								</form>
							    <span class="line">구분선</span>
							    <span class="tool_arrow disabled">
							        <div class="tool_wrap mr10">
							            <ul class="tool_box clearfix">
							                <li onclick="updateEdge(this)" data-shape="vee" class="tool">
												<img src="<c:url value='/images/mindmap/arrow_01.png'/>" alt="vee">
							                </li>
							                <li onclick="updateEdge(this)" data-shape="none" class="tool">
												<img src="<c:url value='/images/mindmap/arrow_02.png'/>" alt="none">
							                </li>
							                <li onclick="updateEdge(this)" data-shape="triangle" class="tool">
												<img src="<c:url value='/images/mindmap/arrow_03.png'/>" alt="triangle">
							                </li>
							                <li onclick="updateEdge(this)" data-shape="dot" class="tool">
												<img src="<c:url value='/images/mindmap/arrow_04.png'/>" alt="dot">
							                </li>
							            </ul>
							            <span>화살표</span>
							        </div>
							    </span>
							</div>
						</div>
						<p class="tbox">※ Shift + 드래그 : 여러 노드 선택</p>
						<div style="height: 1300px;" class="content">
							<div class="border border-dark" id="cy"></div>
						</div>
						<div class="mb-3 mt-3">
							<button class="btn btn-primary mb-3" id="add-btn">등록</button>
							<button class="btn btn-primary mb-3" id="list-btn">목록</button>
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
<script src="<c:url value='/js/cytoscape/cytoscape-klay.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-avsdf.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-dagre.js'/>"></script>
<script src="<c:url value='/js/cytoscape/cytoscape-compound.js'/>"></script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-colorpicker/3.4.0/js/bootstrap-colorpicker.js" integrity="sha512-YFnmLQFOKs4p/gDLhgmMfqdYO9rzXjgeYhjZjomhAXHrJ23AI59keb31/krV4AISRQHGwJhAKfSzzcYF64BxIA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<script>
	document.addEventListener('DOMContentLoaded', function(){
		let data = {
		 	nodes: [],
		  	edges: []
		};
		window.cy_for_rank, window.pageRank, window.cy, window.eh, window.selectLayout = 0, window.tippyEl = [];
		
		data.nodes.push({
			"data" : {
				"id" : "Main",
				"name" : "Main",
				"content" : "Main",
				"shape" : "round-hexagon",
				"width" : 80,
				"height" : 40,
				"font" : 7,
				"color" : "#D0C1B2"
			}
		});
		
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
		            },
		            {
		              selector: '.eh-handle',
		              style: {
		                'background-color': 'red',
		                'width': 10,
		                'height': 10,
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
		            },
		            {
		                selector: '.cdnd-grabbed-node',
		                style: {
		                  'background-color': 'red'
		                }
		            },
		            {
		              selector: '.cdnd-drop-sibling',
		              style: {
		              	'background-color': 'red'
		              }
		            },
	              	{
		              selector: '.cdnd-drop-target',
		              style: {
		                'border-color': 'red',
		                'border-style': 'dashed'
		              }
		            }
		          ]
		});
		/* document.querySelector('#detail').classList.remove('show', 'active'); */
		
		cy.nodes().forEach(node => {
		    	makeTippy(node, node.data().content);
		});
		eh = cy.edgehandles({
			snap: false
		});
		
		cy.minZoom(1);
		cy.maxZoom(3);
		window.layoutArr = [
			{
			    name: 'klay',
				animate: true,
				animationDuration: 500,
			},
			{
			    name: 'dagre',
				animate: true,
				animationDuration: 500
			},
			{
				  name: 'concentric',

				  fit: true,
				  padding: 30,
				  startAngle: 3 / 2 * Math.PI,
				  sweep: undefined,
				  clockwise: true,
				  equidistant: false,
				  minNodeSpacing: 10,
				  boundingBox: undefined,
				  avoidOverlap: true,
				  nodeDimensionsIncludeLabels: false,
				  height: undefined,
				  width: undefined,
				  spacingFactor: undefined,
				  concentric: function( node ){
				  return node.degree();
				  },
				  levelWidth: function( nodes ){
				  return nodes.maxDegree() / 4;
				  },
				  animate: true,
				  animationDuration: 500,
				  animationEasing: undefined,
				  animateFilter: function ( node, i ){ return true; },
				  ready: undefined,
				  stop: undefined,
				  transform: function (node, position ){ return position; }
			}
		];
		window.cyLayout = function cyLayout(){
			let layout = cy.layout(layoutArr[selectLayout]);
			layout.run();
		};
		window.selectLayout = 0;
		cyLayout();
		
		cy.on('ehcomplete', (event, sourceNode, targetNode, addedEdge) => {
			let { position } = event;
		});

		cy.on('select', 'node:childless', e => {
			let node = e.target.data();
			
			showTool('.tool_shapes');
			
			document.mindmap.reset();
			if(cy.nodes(":selected").length === 1){
				document.getElementById('addName').value = node.name;
				document.getElementById('addContent').value = node.content;
				document.getElementById('addWidth').value = node.width;
				document.getElementById('addHeight').value = node.height;
				document.getElementById('nodeColor').value = node.color;
			}
		});

		cy.on('select', 'node:parent', e => {
			let node = e.target.data();
			
			showTool('.tool_shapes');
			document.mindmap.reset();
			document.getElementById('addName').value = node.name===undefined?'':node.name;
			document.getElementById('addContent').disabled = "disabled";
			document.getElementById('addWidth').disabled = "disabled";
			document.getElementById('addHeight').disabled = "disabled";
		});

		cy.on('unselect', 'node', e => {
			if(cy.nodes(":selected").length === 0) {
				hideTool('.tool_shapes');
				document.mindmap.reset();
			}
		});
		
		cy.on('select', 'edge', e => {
			showTool('.tool_arrow');
		});
		
		cy.on('unselect', 'edge', e => {
			if(cy.edges(":selected").length === 0) {
				let node = e.target.data();
				hideTool('.tool_arrow');
				document.mindmap.reset();
			}
		});
		
		var cdnd = cy.compoundDragAndDrop();

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

        cy.on('cdndout', function(event, dropTarget){
          if(isParentOfOneChild(dropTarget) ){
            removeParent(dropTarget);
          }
        });

        cy.on('remove', function(event){
            removeParentsOfOneChild();
        });
        
        $(function() {
            $('#color-container').colorpicker({
              //popover: false,
              inline: true,
              format: "hex",
              container: '#color-container'
              
            });
		});
	});
	
	console.warn = () => {};
	
/* 	let tabEl = document.querySelector('button[data-bs-target="#detail"]');
	tabEl.addEventListener('shown.bs.tab', function (e) {
		e.preventDefault();
	})
	tabEl.addEventListener('hidden.bs.tab', function (e) {
		e.preventDefault();
	}) */

	$('.tool').click(function(){
		$(this).siblings('.tool').removeClass('active');
		$(this).addClass('active');
	});
	
	//툴바 비활성화 설정
	$('.tool_shapes, .tool_arrow').find('input, select').attr('disabled',true);
	$('.tool_shapes, .tool_arrow').find('li').addClass('disabled');
	
	function hideTool(className) {
	    $(className).find('input, select').attr('disabled',true);
	    $(className).find('li').addClass('disabled').removeClass('active');
	    $(className).addClass('disabled');
	}
	
	function showTool(className){
	    $(className).removeClass('disabled');
	    $(className).find('li').removeClass('disabled');
	    $(className).find('input, select').attr('disabled',false);
	}
	
	let resizeTimer;

	window.addEventListener('resize', function () {
	    this.clearTimeout(resizeTimer);
	    resizeTimer = this.setTimeout(function(){
	        cy.fit();
	    },200);
	});
	
	window.onscroll = function() {myFunction()};

	var navbar = document.getElementById("toolbar");

	var sticky = navbar.offsetTop;

	function myFunction() {
	  if (window.pageYOffset >= sticky) {
	    navbar.classList.add("sticky")
	  } else {
	    navbar.classList.remove("sticky");
	  }
	}
	
	var makeTippy = function(ele, text){
		if(!text || !text.trim()){
			return false;
		}
		var ref = ele.popperRef();
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
    
	document.querySelector('#sipNode-add-btn').addEventListener('click', e => {
		e.preventDefault();
		if(cy.nodes(":selected").length !== 1) {
			alert('하나의 노드를 선택해주세요.');
			return false;
		}
		var node = cy.add({
			group: 'nodes',
			data: {"name" : ''
				, "content" : ''
				, "shape" : window.selectedShape===undefined ? 'round-rectangle' : window.selectedShape
				, "color" : '#FFFFFF'
				, "width"  : 100
				, "height" : 40
			}
		});

		cy.add({
			group: 'edges',
			data: {target: node.id(), source: cy.nodes(":selected")[0].id()}
		})

      	cyLayout();
		makeTippy(node, node.data().content);
	});
	
	document.querySelector('#form-reset-btn').addEventListener('click', function(e) {
		e.preventDefault();
		document.mindmap.reset();
		cy.elements(":selected").unselect();
	});

	document.querySelector('#drawMode').addEventListener('change', function(e) {
		if(e.target.checked) {
			eh.enableDrawMode();
		} else {
			eh.disableDrawMode();
		}
	});
	
	document.querySelector('#delete-btn').addEventListener('click', function(e) {
		e.preventDefault();
		cy.batch(function(){
			cy.nodes(':selected').forEach(node => {
				if(node.id() === 'Main'){
					alert('루트노드는 삭제할 수 없습니다.');
					return;
				}
				cy.remove(node);
				let tippy = tippyEl.find(data => {if(data.id === node.id()) return true});
				if(tippy){
					_.remove(tippyEl, data=> {if(data==tippy) return true; });
					tippy.destroy();
				}
			})
			
			cy.edges(':selected').forEach(edge => {
				cy.remove(edge);
			})
		});
	});

    function changeLayout(ele) {
    	window.selectLayout = ele.dataset.layout;
    	cyLayout();
    }
    
	let updateNode = function(e) {
		
		if(e.dataset.id === 'shape'){
			window.selectedShape = e.dataset.shape;
			updateShape(e.dataset.id, e.dataset.shape);
			return;
		}
		
		if(cy.nodes(':selected').length === 0) {
			return false;
		}
		
		cy.nodes(':selected').forEach(node => {
			node.data(e.dataset.id, e.value);
		})
		
		if(e.dataset.id === 'content') {
			updateContent();
		}
	}
	
	let updateShape = function(id, shape) {
		if(cy.nodes(':selected').length > 0) {
			cy.batch(function(){
				cy.nodes(':selected').forEach(node => {
					if(node.id() === 'Main'){
						alert('메인 노드는 수정할 수 없습니다.');
						return;
					} else{
						node.data(id, shape);
					}
				});
			});
		} else {
	    	let node = cy.add({
		    	group: 'nodes',
		    	data: {"name" : ''
		    		, "content" : ''
		    		, "shape" : shape
		    		, "color" : '#FFFFFF'
					, "width" : 100
					, "height" : 40
		    	}
		    });
	    	cyLayout();
		    return;
		}
	}
	
	let updateContent = function() {
		cy.nodes(':selected').forEach(node => {
			let content = node.data().content;
	    	let target = tippyEl.find(data => {
	    		if(data.id === node.id())
	    		return true;
	    	});
	    	
			if(target) {
				if(content === undefined || content === '') {
					_.remove(tippyEl, data=> {if(data==target) return true; });
					target.destroy();
					return;
				}
				target.popper._tippy.setContent(content);
			} else {
				makeTippy(node, content);
			}
		});
	}
	
	let updateEdge = function(e) {
		cy.batch(function(){
			cy.edges(':selected').forEach(edge => {
				 if(e.dataset.shape === 'dot') {
					edge.data('line', 'dotted');
					edge.data('shape', 'vee');
				} else {
					edge.data('line', '');
					edge.data('shape', e.dataset.shape);
				}
			});
		});
	}

    function maxLengthCheck(object){
        if (object.value.length > object.maxLength){
          object.value = object.value.slice(0, object.maxLength);
        }    
	}
	
	function enterKey(ele) {
		if (window.event.keyCode == 13) {
			updateNode(ele);
	    }
	}
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
	    	let data = { "data" : e._private.data}
		  	param.edge.push(data);
	    })
		$.ajax({
			url : "<c:url value='/api/course/'/>",
			method : "post",
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
				$('#'+Object.getOwnPropertyNames(messages[0])).focus();
			}
		});
	})
</script>
</body>
</html>
