<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body class="sb-nav-fixed">
	<c:import url="../common/header.jsp"></c:import>
	<div id="layoutSidenav">
	<c:import url="../common/nav.jsp"></c:import>
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid px-4">
					<div class="card mb-4">
						<div class="card-header">
							<div class="mb10 mt10">
								<div class="input-group">
									<div class="input-group-prepend">
										<label class="input-group-text">방제목</label>
									</div>
									<input type="text" class="form-control w200" name="room_name">
									<div class="input-group-append">
						                <button class="btn btn-primary" type="button" id="create-btn">채팅방 개설</button>
						            </div>
								</div>
							</div>
						</div>
						<div class="card-body">
							<div class="container-fluid px-4">
								<ul class="list-group" id="items">
							    </ul>
							</div>
						</div>
					</div>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="<c:url value='/webjars/axios/0.17.1/dist/axios.min.js'/>"></script>
<script src="<c:url value='/webjars/sockjs-client/1.1.2/sockjs.min.js'/>"></script>
<script>
	
	window.addEventListener('DOMContentLoaded', (e) => {
		e.preventDefault();
		getList();
	});
	
	function getList(){
        var items = document.getElementById('items');
        while(items.hasChildNodes()){
        	items.removeChild(items.firstChild);
        }

        $.ajax({
			url : "<c:url value='/chat/rooms'/>",
			method : 'GET',
			success : function(result){
				console.log(result);
				var items = document.getElementById('items');
				result.forEach(data => {
					var li = document.createElement('li');
					var a = document.createElement('a');
					li.className = 'list-group-item list-group-item-action';
					a.href = "#";
					a.innerText = data.name;
					li.appendChild(a);
					items.appendChild(li);
					a.addEventListener('click', e => {
						enterRoom(data.roomId)
					});
				});
			}
		})
	}
	
	document.getElementById('create-btn').addEventListener('click', e => {
		var inputData = document.getElementsByName('room_name')[0].value;
		if(!inputData){
			alert('방 제목을 입력해 주세요');
			return false;
		} else {
			var params = new URLSearchParams();
            params.append("name", inputData);
            axios.post("<c:url value='/chat/room'/>", params)
            .then( response => {
                    alert(response.data.name+"방 개설에 성공하였습니다.")
                    inputData = '';
                    getList();
                }
            )
            .catch( response => { alert("채팅방 개설에 실패하였습니다."); } );
		}
		
	})
	
	function enterRoom(roomId) {
	    localStorage.setItem('wschat.sender', '${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.name}');
	    localStorage.setItem('wschat.roomId', roomId);
		location.href="<c:url value='/chat/room/enter/'/>" + roomId;
	}
</script>
</body>
</html>