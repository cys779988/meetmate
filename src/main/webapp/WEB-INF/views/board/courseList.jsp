<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://uicdn.toast.com/tui.pagination/v3.3.0/tui-pagination.css" />
<link rel="stylesheet" href="https://uicdn.toast.com/grid/v4.19.0/tui-grid.css" />
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
						<div class="card-body">
							<div class="row">
						    	<div class="col">
							        <div class="search">
							            <input name="keyword" type="text" class="form-control" placeholder="검색어를 입력해주세요">
							        </div>
								</div>
								<div class="col">
							        <button id="search-btn" class="btn btn-primary mb-3">검색</button>
							    </div>
							</div>
						</div>
					</div>
				</div>
				<div class="container-fluid px-4">
				  <div class="btn-wrapper">
				        <button class="btn btn-primary" id="regist-btn">추가</button>
				        <button class="btn btn-primary" id="modify-btn">저장</button>
				        <button class="btn btn-primary" id="delete-btn">삭제</button>
				      </div>
				   <div id="grid"></div>
				</div>
			</main>
			<c:import url="../common/footer.jsp"></c:import>
		</div>
	</div>
<script src="/js/grid/tui-code-snippet.js"></script>
<script src="/js/grid/tui-pagination.js"></script>
<script src="/js/grid/tui-grid.js"></script>
<script src="/js/grid/grid.js"></script>
<script src="/js/common.js"></script>
<script>

	class TextEditor{
		constructor(props){
			this.el = createEle_text(props);
		}
		getElement(){
			return this.el;
		}
	 	getValue(){
			return this.el.value;
		}
		mounted(){
			this.el.select();
		} 
	}
	
	class RowNumberRenderer {
		constructor(props) {
	        this.el = createEle_num(props);
		}

		getElement() {
			return this.el;
		}
    }
	
    class ButtonRenderer {
        constructor(props) {
		const { grid, rowKey } = props;
		/*const button = document.createElement('input');
		button.type = "button"
		button.className = 'display-btn';
		button.setAttribute('for', String(rowKey));
		button.value = '미리보기설정';
		button.addEventListener('click', );

		const text = document.createElement('span');
		text.innerHTML = props.value;
		const label = document.createElement('label');
		label.appendChild(text);
		label.appendChild(button); */
		
		/* var openPop = function () {
			popUp(grid.getRow(rowKey).id, '테스트팝업');
		} */
		let label = createEle_btn(props, '테스트', function () {
			popUp(grid.getRow(rowKey).id, '테스트팝업');
		});
		const text = document.createElement('span');
		text.innerHTML = props.value;
		label.appendChild(text);
		this.el = label;
		
        this.render(props);
        }

        getElement() {
          return this.el;
        }
  	
        render(props) {
        }
	}
    
		const columnData = [{
		         header: '강좌순서',
		         name: 'id',
		         sortingType: 'desc',
		         sortable: true,
		         width: '100',
		         renderer: {
		             type: RowNumberRenderer
	         }
		     },
		     {
		         header: '구분',
		         name: 'courseDiv',
		         sortingType: 'asc',
		         sortable: true,
		         width: '100'
		     },
		     {
		         header: '교과명(교과ID/코스웨어ID/사용여부)',
		         name: 'name',
		         sortingType: 'asc',
		         sortable: true,
		         width: '250',
		         renderer: {
		             type: ButtonRenderer
		         }
		     },
		     {
		         header: '신구/개편여부',
		         name: 'type',
		         filter: 'select',
		         width: '110',
		         editor: {
		             type: 'select',
		             options: {
		                 listItems: [{
		                         text: 'Y',
		                         value: 'Y'
		                     },
		                     {
		                         text: 'N',
		                         value: 'N'
		                     }
		                 ]
		             }
		         }
		     },
		     {
		         header: '콘텐츠재생시간',
		         name: 'playTime',
		         width: '110'
		     },
		     {
		         header: '교과편성',
		         name: 'col1',
		         width: '100',
		         editor: {
		             type: 'select',
		             options: {
		                 listItems: [{
		                         text: '기본소양',
		                         value: '기본소양'
		                     },
		                     {
		                         text: '기본역량',
		                         value: '기본역량'
		                     },
		                     {
		                         text: '전문역량',
		                         value: '전문역량'
		                     },
		                     {
		                         text: '역량/전문',
		                         value: '역량/전문'
		                     }
		                 ]
		             }
		         }
		     },
		     {
		         header: '운영형태',
		         name: 'col2',
		         width: '100',
		         editor: {
		             type: 'select',
		             options: {
		                 listItems: [{
		                         text: '분반',
		                         value: '분반'
		                     },
		                     {
		                         text: '일반과목',
		                         value: '일반과목'
		                     },
		                     {
		                         text: '선택과목',
		                         value: '선택과목'
		                     },
		                     {
		                         text: '분임활동',
		                         value: '분임활동'
		                     }
		                 ]
		             }
		         }
		     },
		     {
		         header: '교육방법',
		         name: 'col3',
		         width: '100',
		         editor: {
		             type: 'select',
		             options: {
		                 listItems: [{
		                         text: '집합교육',
		                         value: '집합교육'
		                     },
		                     {
		                         text: '원격교육',
		                         value: '원격교육'
		                     },
		                 ]
		             }
		         }
		     },
		     {
		         header: '시수',
		         name: 'col4',
		         width: '50',
		         editor: {
		             type: TextEditor,
		             options: {
		                 maxLength: 3
		             }
		         }
		     }
		 ];
	 
	const dataSource = {
		api: {
			readData: { url: '/api/course/get', method: 'GET' },
			createData: { url: '/api/course/post', method: 'POST' },
			modifyData: { url: '/api/course/put', method: 'PUT' },
			deleteData: { url: '/api/course/delete', method: 'POST' }
		},
		contentType: 'application/json'
	};
	
	const grid = createGrid_paging('grid', dataSource, columnData, 'auto', 10);
	
    tui.Grid.setLanguage('ko');
    
	grid.on('errorResponse', function(data) {
		alert('에러가 발생했습니다.');
		grid.reloadData();
	})
    
    
    document.getElementById('regist-btn').addEventListener('click', () => {
		grid.getCheckedRowKeys().forEach(rowKey => {
			var rowData = grid.getRow(rowKey);
			grid.appendRow(rowData);
			//grid.request('createData');
			/* $.ajax({
				url : '/api/course/post',
				method : 'post',
				data : JSON.stringify(rowData),
				contentType : "application/json",
				success : function(result){
					alert('저장완료');
				}
			}) */
		});
	})
	
	document.getElementById('modify-btn').addEventListener('click', () => {
		var {rowKey, columnName} = grid.getFocusedCell();
    	console.log(rowKey, columnName);
    	if(columnName) {
    		grid.finishEditing(rowKey, columnName);
    	}
		grid.request('modifyData');
	})
	
	document.getElementById('delete-btn').addEventListener('click', () => {
		deleteRows().then( response => {
			alert(response);
			grid.reloadData();
		});
		
	})
	
	function deleteRows(){
    	return new Promise((resolve, reject) => {
			grid.getCheckedRows().forEach(data => grid.dataManager.push('DELETE', data));
			grid.request('deleteData', options = {showConfirm : false});
			resolve('삭제완료');
		});
    }
</script>
</body>
</html>