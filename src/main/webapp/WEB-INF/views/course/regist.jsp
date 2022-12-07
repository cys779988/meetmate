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
	z-index: 33;
}

.tui-datepicker {
	z-index: 22;
}

</style>
<link rel="stylesheet" href="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-colorpicker/3.4.0/css/bootstrap-colorpicker.css" integrity="sha512-HcfKB3Y0Dvf+k1XOwAD6d0LXRFpCnwsapllBQIvvLtO2KMTa0nI5MtuTv3DuawpsiA0ztTeu690DnMux/SuXJQ==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="<c:url value='/css/toolbar.css'/>"/>
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<h2>Course Regist</h2>
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
												<input type="text" class="form-control w-25" name="title" id="title" placeholder="제목">
											    <div>
											    	<p class="errorText" id="titleError"></p>
											    </div>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">신청일자</h6>
											</div>
											<div class="col-md-11 pe-5">
												<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
													<input type="text" name="applyStartDate" id="applyStartDate" aria-label="Date">
													<span class="tui-ico-date"></span>
													<div class="d-inline" id="applyStartpickerContainer" style="margin-top: -1px;"></div>
												</div>
												<span>~</span>
												<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
													<input type="text" name="applyEndDate" id="applyEndDate" aria-label="Date">
													<span class="tui-ico-date"></span>
													<div class="d-inline" id="applyEndpickerContainer" style="margin-top: -1px;"></div>
												</div>
											    <div>
											    	<p class="errorText" id="applyStartDateError"></p>
											    	<p class="errorText" id="applyEndDateError"></p>
											    </div>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">운영일자</h6>
											</div>
											<div class="col-md-11 pe-5">
												<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
													<input type="text" name="startDate" id="startDate" aria-label="Date">
													<span class="tui-ico-date"></span>
													<div class="d-inline" id="startpickerContainer" style="margin-top: -1px;"></div>
												</div>
												<span>~</span>
												<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
													<input type="text" name="endDate" id="endDate" aria-label="Date">
													<span class="tui-ico-date"></span>
													<div class="d-inline" id="endpickerContainer" style="margin-top: -1px;"></div>
												</div>
											    <div>
											    	<p class="errorText" id="startDateError"></p>
											    	<p class="errorText" id="endDateError"></p>
											    </div>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">분류</h6>
											</div>
											<div class="col-md-11 pe-5">
												<select class="form-select w-25" name="category" id="category">
											        <c:forEach items="${category}" var="result">
											        	<option value="${result.key}">${result.value}</option>
											        </c:forEach>
												</select>
											    <div>
											    	<p class="errorText" id="typeError"></p>
											    </div>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">분반</h6>
											</div>
											<div class="col-md-11 pe-5">
												<input type="number" class="form-control w-25" name="divclsNo" id="divclsNo" placeholder="분반">
											</div>
										</div>
										<hr class="mx-n3">
										<div class="row align-items-center">
											<div class="col-md-1 ps-5">
												<h6 class="mb-0">최대인원</h6>
											</div>
											<div class="col-md-11 pe-5">
												<input type="text" class="form-control w-25" name="maxNum" id="maxNum" placeholder="최대인원">
												<div>
										    		<p class="errorText" id="maxNumError"></p>
										    	</div>
											</div>
										</div>
										<hr class="mx-n3">
										<div class="mb-3">
											<div id="editor"></div>
										</div>
									</div>
								</form>
							</div>
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
												<img class="rotate_180" src="<c:url value='/images/icon/layout_02.png'/>" alt="레이아웃_01">
								            </li>
								            <li data-layout="1" onclick="changeLayout(this)" class="tool" >
												<img src="<c:url value='/images/icon/layout_01.png'/>" alt="레이아웃_02">
								            </li>
								            <li data-layout="2" onclick="changeLayout(this)" class="tool">
												<img src="<c:url value='/images/icon/layout_03.png'/>" alt="레이아웃_03">
								            </li>
								        </ul>
								        <span>레이아웃</span>
								    </div>
								    <div class="tool_wrap">
								        <div class="tool_btn">
								             <a href="#" id="sipNode-add-btn">
								                 <img src="<c:url value='/images/icon/tool_add.png'/>" alt="하위노드추가">
								             </a>
								        </div>
								        <span>하위노드 추가</span>
								    </div>
								    <div class="tool_wrap">
								        <div class="tool_btn">
								            <a href="#" id="delete-btn">
								                <img src="<c:url value='/images/icon/tool_remove.png'/>" alt="삭제">
								            </a>
								        </div>
								        <span>삭제</span>
								    </div>
								    <div class="tool_wrap">
								        <div class="tool_btn">
								            <a href="#" id="form-reset-btn">
								                <img src="<c:url value='/images/icon/tool_reset.png'/>" alt="초기화">
								            </a>
								        </div>
								        <span>선택 초기화</span>
								    </div>
	
								    <div class="tool_wrap mr10">
								        <ul class="tool_box clearfix">
								            <li onclick="updateNode(this)" data-id="shape" data-shape="ellipse" class="tool">
												<img src="<c:url value='/images/icon/shape_01.png'/>" alt="원">
								            </li>
								            <li onclick="updateNode(this)" data-id="shape" data-shape="round-rectangle" class="tool">
								            	<img src="<c:url value='/images/icon/shape_02.png'/>" alt="사각형">
								            </li>
								            <li onclick="updateNode(this)" data-id="shape" data-shape="round-diamond" class="tool">
								            	<img src="<c:url value='/images/icon/shape_03.png'/>" alt="마름모">
								            </li>
								            <li onclick="updateNode(this)" data-id="shape" data-shape="round-hexagon" class="tool">
								            	<img src="<c:url value='/images/icon/shape_04.png'/>" alt="육각형">
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
													<img src="<c:url value='/images/icon/arrow_01.png'/>" alt="vee">
								                </li>
								                <li onclick="updateEdge(this)" data-shape="none" class="tool">
													<img src="<c:url value='/images/icon/arrow_02.png'/>" alt="none">
								                </li>
								                <li onclick="updateEdge(this)" data-shape="triangle" class="tool">
													<img src="<c:url value='/images/icon/arrow_03.png'/>" alt="triangle">
								                </li>
								                <li onclick="updateEdge(this)" data-shape="dot" class="tool">
													<img src="<c:url value='/images/icon/arrow_04.png'/>" alt="dot">
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
						</div>
						<div class="tab-pane fade" id="detail" role="tabpanel" aria-labelledby="nav-detail-tab">
							<div id="calendar" style="height: 600px;"></div>
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
<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">Schedule</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<form id="modal-form">
					<div class="mb-3">
						<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
							<input type="text" name="startDate" id="m-startDate" aria-label="Date">
							<span class="tui-ico-date"></span>
							<div class="d-inline" id="m-startpickerContainer" style="margin-top: -1px;"></div>
						</div>
						<span>~</span>
						<div class="tui-datepicker-input tui-datetime-input tui-has-focus">
							<input type="text" name="endDate" id="m-endDate" aria-label="Date">
							<span class="tui-ico-date"></span>
							<div class="d-inline" id="m-endpickerContainer" style="margin-top: -1px;"></div>
						</div>
					</div>
					<div class="mb-3">
						<label for="message-text" class="col-form-label">활동</label>
						<input type="text" name="schedule" class="form-control" id="schedule">
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="add-btn">저장</button>
				<button type="button" class="btn btn-primary" id="delete-btn">삭제</button>
			</div>
		</div>
	</div>
</div>
<script src="<c:url value='/js/common.js'/>" ></script>
<script src="https://uicdn.toast.com/tui.code-snippet/latest/tui-code-snippet.js"></script>
<script src="https://uicdn.toast.com/tui.time-picker/latest/tui-time-picker.js"></script>
<script src="https://uicdn.toast.com/tui.date-picker/latest/tui-date-picker.js"></script>
<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
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
console.warn = () => {};

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

function makeTippy(ele, text){
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
}

function changeLayout(ele) {
	window.selectLayout = ele.dataset.layout;
	cyLayout();
}

function updateNode(e) {
	
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

function updateShape(id, shape) {
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

function updateContent() {
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

function updateEdge(e) {
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

function dateFormat(date) {
	return date.toLocaleDateString().replaceAll('.', '').replaceAll(' ', '-');
}

document.addEventListener('DOMContentLoaded', function(){
	(function createDatePicker() {
		const today = new Date();
		const applyDatePicker = tui.DatePicker.createRangePicker({
			startpicker: {
				date: today,
				input: '#applyStartDate',
				container: '#applyStartpickerContainer'
			},
			endpicker: {
				date: today,
				input: '#applyEndDate',
				container: '#applyEndpickerContainer'
			},
			selectableRanges: [
				[today, new Date(today.getFullYear() + 1, today.getMonth(), today.getDate())]
			]
		});
		
		const operDatePicker = tui.DatePicker.createRangePicker({
			startpicker: {
				date: today,
				input: '#startDate',
				container: '#startpickerContainer'
			},
			endpicker: {
				date: today,
				input: '#endDate',
				container: '#endpickerContainer'
			},
			selectableRanges: [
				[today, new Date(today.getFullYear() + 1, today.getMonth(), today.getDate())]
			]
		});

		const modalDatePicker = tui.DatePicker.createRangePicker({
			startpicker: {
				date: today,
				input: '#m-startDate',
				container: '#m-startpickerContainer'
			},
			endpicker: {
				date: today,
				input: '#m-endDate',
				container: '#m-endpickerContainer'
			},
			selectableRanges: [
				[today, new Date(today.getFullYear() + 1, today.getMonth(), today.getDate())]
			]
		});
	})();

	(function createEditor() {
		window.editor = new toastui.Editor({
			el: document.querySelector('#editor'),
			height: '500px',
			initialEditType: 'wysiwyg',
			hooks:{
				addImageBlobHook:(fileOrBlob, callback) => {
				    return false;
				}
			}
		});
		editor.removeToolbarItem('image');
	})();
        
	(function createMindmap() {
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
		})();
		
		$('#color-container').colorpicker({
			//popover: false,
			inline: true,
			format: "hex",
			container: '#color-container'
		});
        
		let resizeTimer;
		window.addEventListener('resize', function () {
		    this.clearTimeout(resizeTimer);
		    resizeTimer = this.setTimeout(function(){
		        cy.fit();
		    },200);
		});

		const navbar = document.getElementById("toolbar");
		const sticky = navbar.offsetTop;
		window.addEventListener('scroll', function () {
			if (window.pageYOffset >= sticky) {
				navbar.classList.add("sticky");
			} else {
				navbar.classList.remove("sticky");
			}
		});
		
		$('.tool').click(function(){
			$(this).siblings('.tool').removeClass('active');
			$(this).addClass('active');
		});
		
		//툴바 비활성화 설정
		$('.tool_shapes, .tool_arrow').find('input, select').attr('disabled',true);
		$('.tool_shapes, .tool_arrow').find('li').addClass('disabled');
		
		(function createCalendar() {
			let eventId;
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
				locale: 'ko',
				selectable: true,
				eventClick: function(info) {
					$('#modal').modal('toggle');
					eventId = info.event.id;
					$('#modal #m-startDate').val(dateFormat(info.event._instance.range.start));
					$('#modal #m-endDate').val(dateFormat(info.event._instance.range.end));
					$('#modal #schedule').val(info.event._def.title);
					$('#modal #delete-btn').show();
				},
				select: function(info) {
					eventId = null;
					$('#modal').modal('toggle');
					$('#modal #m-startDate').val(info.startStr);
					$('#modal #m-endDate').val(info.endStr);
					$('#modal #delete-btn').hide();
				}
			});
			
			calendar.render();
			
			(function() {
				let id = 1;
				document.querySelector('#modal #add-btn').addEventListener('click', function (e) {
					e.preventDefault();
					const data = $('#modal-form').serializeObject();
					if(!data.schedule || !data.startDate || !data.endDate) {
						alert('항목을 입력해주세요.');
						return;
					}
					if(eventId) {
						const item = calendar.getEventById(eventId);
						item.setProp('title', data.schedule);
						item.setStart(data.startDate);
						item.setEnd(data.endDate);
					} else {
						calendar.addEvent({
							id: id++,
							title: data.schedule,
							start: data.startDate,
							end: data.endDate
						});
					}
					
					$('#modal').modal('toggle');
				});

				document.querySelector('#modal #delete-btn').addEventListener('click', function (e) {
					e.preventDefault();
					const target = calendar.getEventById(eventId);
					if(target) target.remove();
					$('#modal').modal('toggle');
				});
			})();
		})();
		
		document.querySelector('#list-btn').addEventListener('click',(e) => {
			e.preventDefault();
			location.href = "<c:url value='/course'/>";
		})

		document.querySelector('#add-btn').addEventListener("click", (e)=> {
			e.preventDefault();
			const param = $("#form").serializeObject();
			param.content = editor.getHTML();
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
		    
		    param.schedules = calendar.getEvents().map(item => {
		    	return {
		    		id: item.id,
		    		title: item.title,
			    	start: item.startStr,
			    	end: item.endStr
		    	}
	    	});
	    	
			$.ajax({
				url : "<c:url value='/api/course'/>",
				method : "post",
				data : JSON.stringify(param),
				contentType : "application/json",
				success : function(result) {
					location.href = "<c:url value='/course'/>";
				}
			});
		})
		
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
		
		document.querySelector('button[data-bs-target="#detail"]').addEventListener('shown.bs.tab', function (e) {
			e.preventDefault();
			calendar.render();
		})
		
		document.querySelector('#modal').addEventListener('hidden.bs.modal', function (e) {
			e.preventDefault();
			$('#modal-form')[0].reset();
			eventId = null;
		})
	});
</script>
</body>
</html>
