<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  .chat {
    height: 700px;
    overflow: auto;
  }
  .chat::-webkit-scrollbar {
    width: 10px;
  }
  .chat::-webkit-scrollbar-thumb {
    background-color: #2f3542;
  }
  .chat::-webkit-scrollbar-track {
    background-color: grey;
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
					<div class="card mb-4">
						<div class="card-header">
							<div>
								<input type="hidden" value="${roomId}" name="roomId"> 
							</div>
							<div class="input-group">
								<div class="input-group-prepend">
									<label class="input-group-text">내용</label>
								</div>
								<input type="text" class="form-control" name="message" id="message">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="sendMessage">보내기</button>
								</div>
							</div>
						</div>
						<div class="card-body">
							<div class="container-fluid px-4">
								<div class="chat">
									<ul class="list-group" id="items">
										<c:forEach items="${chatMessage}" var="item">
											<li class="list-group-item d-flex justify-content-between align-items-start">
												<div class="ms-2 me-auto">
												${item.sender} : ${item.message}
												</div>
												<span class="badge bg-primary rounded-pill">${item.createdDate}</span>
											</li>
										</c:forEach>
							       	</ul>
						       	</div>
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
<script src="<c:url value='/webjars/stomp-websocket/2.3.3-1/stomp.min.js'/>"></script>
<script>
window.addEventListener('DOMContentLoaded', () => {
	let sock = new SockJS("<c:url value='/ws-stomp'/>");
	let ws = Stomp.over(sock);
	let messages = [];
	const roomId = document.getElementsByName('roomId')[0].value;
	let token;

	(function(){
		axios.get("<c:url value='/chat/user'/>").then(response => {
			token = response.data.token;
			ws.connect({"token" : token}, function(frame){
				ws.subscribe("/sub/chat/room/"+ roomId, function(message){
					var recv = JSON.parse(message.body);
					recvMessage(recv);
				});
				sendMessage('ENTER');
			}, function(error) {
				alert('서버 연결에 실패했습니다.');
			})
		})
	})();
	
	window.onbeforeunload = function(){
		ws.disconnect();
	}
	
	function sendMessage(type) {
		const message = document.getElementsByName('message')[0].value;
		ws.send("/pub/chat/message"
				, {"token" : token}
				, JSON.stringify({
					type : type
					, roomId : roomId
					, sender : localStorage.getItem('wschat.sender')
					, message : message
					}
				)
		);
		document.getElementsByName('message')[0].value = '';
	}
	
	function recvMessage(recv) {
		messages.unshift({
			"type" : recv.type,
			"sender" : recv.sender,
			"message" : recv.message
		});
		chatUpdate(recv);
	}
	
	function chatUpdate(messages){
		let items = document.getElementById('items');
		
		let li = document.createElement('li');
		li.className = 'list-group-item d-flex justify-content-between align-items-start';
		
		let div = document.createElement('div');
		div.className = 'ms-2 me-auto';
		div.append(messages.sender + ' : ' +  messages.message);
		
		let span = document.createElement('span');
		span.className = 'badge bg-primary rounded-pill'
		span.append(messages.createdDate);
		
		li.append(div);
		li.append(span);
		
		items.appendChild(li);
		
		let chatList = document.getElementsByClassName('chat')[0];
		chatList.scrollTop = chatList.scrollHeight;
	}
	
	document.getElementById('message').addEventListener('keydown', (e) => {
		if(e.keyCode === 13){
			sendMessage();
		}
	});
	
	document.getElementById('sendMessage').addEventListener('click', (e) => {
		sendMessage();
	});
});
</script>
</body>
</html>