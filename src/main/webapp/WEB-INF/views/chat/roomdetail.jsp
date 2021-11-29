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
								<input type="text" class="form-control" name="message">
								<div class="input-group-append">
									<button class="btn btn-primary" type="button" id="sendMessage">보내기</button>
								</div>
							</div>
						</div>
						<div class="card-body">
							<div class="container-fluid px-4">
								<div class="chat">
									<ul class="list-group" id="items">
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

	var sock = new SockJS("<c:url value='/ws-stomp'/>");
	var ws = Stomp.over(sock);
	var messages = [];
	var roomId = document.getElementsByName('roomId')[0].value;
	window.addEventListener('DOMContentLoaded', (e) => {
		e.preventDefault();
		created();
	});
	
	window.onbeforeunload = function(){
		ws.disconnect();
	}
	
	document.getElementById('sendMessage').addEventListener('keyup', (e) => {
		if(e.keyCode === 13){
			sendMessage();
		}
	});
	
	document.getElementById('sendMessage').addEventListener('click', (e) => {
		sendMessage();
	});
	
	function created(){
		var _this = this;
		axios.get("<c:url value='/chat/user'/>").then(response => {
			_this.token = response.data.token;
			console.log(_this.token);
			console.log(_this.roomId);
			ws.connect({"token" : _this.token}, function(frame){
				ws.subscribe("<c:url value='/sub/chat/room/'/>"+ _this.roomId, function(message){
					var recv = JSON.parse(message.body);
					_this.recvMessage(recv);
				});
				_this.sendMessage('ENTER');
			}, function(error) {
				alert('서버 연결에 실패했습니다.');
			})
		})
	}
	
	function sendMessage(type) {
		var message = document.getElementsByName('message')[0].value;
		ws.send("<c:url value='/pub/chat/message'/>"
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
			"sender" : recv.type == 'ENTER' ? '[알림]' : recv.sender,
			"message" : recv.message
		});
		chatUpdate(recv);
	}
	
	/* 
	ws.connect({}, function(frame) {
		ws.subscribe("/sub/chat/room/"+ roomId, function(message) {
			var recv = JSON.parse(message.body);
			recvMessage(recv);
		});
		
		ws.send("/pub/chat/message"
				, {}
				, JSON.stringify({
					type:'ENTER'
					, roomId: roomId
					, sender: localStorage.getItem('wschat.sender')
					}
				)
		);
		
	}, function(error) {
		alert(error);
	}) */
	
	function chatUpdate(messages){
		console.log(messages);
		var items = document.getElementById('items');
		var li = document.createElement('li');
		li.className = 'list-group-item';
		li.append(messages.sender + ' : ' +  messages.message);
		items.appendChild(li);
		var chatList = document.getElementsByClassName('chat')[0];
		chatList.scrollTop = chatList.scrollHeight;
	}
	
</script>
</body>
</html>