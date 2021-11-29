<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="card mb-4">
	<div class="card-header">
		<div class="mb10 mt10">
			<h3 id="title"></h3>
			<p>분배조건 설정 후 자동분배 시 자동으로 분반 설정됩니다. 단,
				자동부여 이후 분반을 변경하였다면 "저장" 버튼을 눌러주셔야합니다.</p>
		</div>
		<table class="vertical mb16 tal">
			<colgroup>
				<col style="width: 20%">
				<col style="width: 80%">
			</colgroup>
			<tbody>
				<tr>
					<th>분배조건</th>
					<td>
						<div class="row w350">
						<c:forEach begin="0" end="1" step="1" var="i" varStatus="cnt">
							<div class="col">
								<select name="autoDivideClass" class="form-select w150">
									<option value="">선택</option>
									<option value="createdDate">신청시간</option>
									<option value="memberName">이름</option>
								</select>
							</div>
						</c:forEach>
						</div>
					</td>
				</tr>
				<tr>
					<th>분배방법</th>
					<td>
						<label><input class="form-check-input" type="radio" id="divMth" name="divMth" value="1" checked>순차</label>
						<label><input class="form-check-input" type="radio" id="divMth" name="divMth" value="0">교차</label>
					</td>
				</tr>
			</tbody>
		</table>
		<div align="right">
			<button id="autoDivide-btn" class="btn btn-primary">자동분배</button>
		</div>
	</div>
	<div class="card-body">
		<div class="align-right">
			<button class="btn btn-primary" id="reset-btn">초기화</button>
			<button class="btn btn-primary" id="stdnt-regist-btn">저장</button>
		</div>
		<div class="row mt-3">
			<div class="col-sm-5" id="grid1-group">
				<div class="card mb-4">
					<div class="card-header">
						<span>미배정인원 <span id="unassignCnt"></span> / 총<span id="totCnt"></span></span>
					</div>
					<div class="card-body">
						<div id="grid1"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-2 text-center align-content-center" id="button-group">
				<div class="mb-3">
					<button class="btn btn-outline-dark w100" id="assign-btn">배정</button>
				</div>
				<div class="mb-3">
					<button class="btn btn-outline-dark w100" id="unassign-btn">미배정</button>
				</div>
			</div>
			<div class="col-sm-5" id="grid2-group">
				<div class="card mb-4">
					<div class="card-header">
						<select id="selectDiv" name="selectDiv" class="form-select w100">
						</select>
					</div>
					<div class="card-body">
						<div id="grid2"></div>
					</div>
				</div>
			</div>
		</div>						
	</div>
</div>
<script>
window.grid1 = createGrid_scroll('grid1', '', column, 220);

window.grid2 = createGrid_scroll('grid2', '', column, 220);

tui.Grid.applyTheme('clean');

const getData = function () {
	const divNo = $('input[name="divNo"]').val();
	const crseNm = $('input[name="crseNm"]').val();
	$.ajax({
		url : "<c:url value='/api/group/${id}'/>", 
		method : 'get',
		success : function(result) {
			window.divClassData = JSON.parse(JSON.stringify([...result.assignment.concat(...result.unassignment)]));
			window.updatedData = new Map();
			// grid1 Data insert
			grid1.resetData(result.unassignment);
			updateText($('#unassignCnt'), result.unassignment.length + '명');
			updateText($('#totCnt'), divClassData.length + '명');
			$('#title').text('Course ' + crseNm);
			
			$('#selectDiv').empty();
			for(var count = 1; count <= divNo; count++){
				console.log(count);
                var option = $("<option class='divNo' value=" + count + ">"+count+"그룹</option>");
                $('#selectDiv').append(option);
            }
			
			// grid2 Data insert
			let selectDiv = document.getElementsByClassName('divNo')[0].value;
			let grid2_data = [];
			result.assignment.forEach(data => {
				if(data.divNo == selectDiv) {
					grid2_data.push(data);
				}
			})
			grid2.resetData(grid2_data);
		}
	});
}

getData();

document.getElementById('assign-btn').addEventListener('click', e => {
	e.preventDefault();
	const checked = grid1.getCheckedRows();
	if(checked.length < 1) {
		alert('배정할 수강생을 선택해주세요.');
		return;
	}
	checked.forEach(rowData => {
		let {memberId, memberName, courseId} = rowData;
		let appendData = {};
		appendData.memberId = memberId;
		appendData.memberName = memberName;
		appendData.courseId = courseId;
		appendData.divNo = $('#selectDiv').val();
		
		divClassData.forEach(data => {
			if(data.memberId == memberId) {
				data.divNo = appendData.divNo;
			}
		})
		updatedData.set(appendData.memberId,appendData);
		grid2.appendRow(appendData);
	});
	
	grid1.removeCheckedRows();
	grid2.uncheckAll();
})

document.getElementById('unassign-btn').addEventListener('click', e => {
	e.preventDefault();
	const checked = grid2.getCheckedRows();
	if(checked.length < 1) {
		alert('미배정할 수강생을 선택해주세요.');
		return;
	}
	checked.forEach(rowData => {
		let {memberId, memberName, courseId} = rowData;
		let appendData = {};
		appendData.memberId = memberId;
		appendData.memberName = memberName;
		appendData.courseId = courseId;
		appendData.divNo = 0;
		
		divClassData.forEach(data => {
			if(data.memberId == memberId) {
				data.divNo = 0;
			}
		})
		updatedData.set(appendData.memberId,appendData);
		grid1.appendRow(appendData);
	});

	grid2.removeCheckedRows();
	grid1.uncheckAll();
})

document.getElementById('autoDivide-btn').addEventListener('click', e => {
	e.preventDefault();
	let autoDivide = [];
	document.getElementsByName('autoDivideClass').forEach(data => {
		if(data.value!=='') 
			autoDivide.push(data.value);
	})
	let param = {};
	param.divCnd = autoDivide;
	param.data = this.divClassData;
	param.divMth = $('input:radio[name=divMth]:checked').val()*1;
	param.divNo = document.getElementsByClassName('divNo').length*1;
	$.ajax({
		url : "<c:url value='/api/group/autoDivide'/>",
		method : "post",
		data : JSON.stringify(param),
		contentType : 'application/json',
		success : function(result){
			getData();
		}
	})
})

document.getElementById('reset-btn').addEventListener('click', e => {
	e.preventDefault();
	$.ajax({
		url : "<c:url value='/api/group/resetDivide/${id}'/>",
		type : 'post',
		success : function(result) {
			getData();
			alert('초기화가 완료되었습니다.')
		}
	})
})

document.getElementById('selectDiv').addEventListener('change', e => {
	let selectCls = e.target.value;
	let gridData = [];
	if(divClassData){
		divClassData.forEach(data => {
			if(data.divNo == selectCls) {
				gridData.push(data);
			}
		})
		grid2.resetData(gridData);
	}
})

document.getElementById('stdnt-regist-btn').addEventListener('click', e => {
	e.preventDefault();
	
	let param = [];
	updatedData.forEach(data => {
		param.push(data);
	})
	$.ajax({
		url : "<c:url value='/api/group/updateDivide'/>",
		type : 'POST',
		data : JSON.stringify(param),
		contentType :'application/json',
		success : function(result) {
			getData();
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