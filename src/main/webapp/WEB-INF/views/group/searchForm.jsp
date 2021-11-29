<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<!--IE 호환성-->
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--viewport-->
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1, maximum-scale=1, user-scalable=no">
</head>

<body>
<div class="content_box">
	<div class="content">
	    <div class="white_box dip_bl mb10 mt30 clearfix">
	        <form id="form">
				<div class="search_list_block mb10">
					<span class="label"><img src="/resources/images/icon/dot_blue.png"
						class="list_dot_blue"></img>운영기간</span>
					<input type="text" name="eduBgngYmd" value="${searchData.eduBgngYmd}" class="datepicker tal">
					<span class="mr15 ml15">~</span>
					<input type="text" name="eduEndYmd" value="${searchData.eduEndYmd}" class="datepicker tal mr10">
		
					<div class="search_list">
						<span class="label"><img src="/resources/images/icon/dot_blue.png"
							class="list_dot_blue"></img>운영방법</span>
							<select name="crseOperSeCd" class="select">
								<c:forEach var="item" items="${commonList}">
									<option value="${item.cdValu}" ${searchData.crseOperSeCd eq item.cdValu ? 'selected' : ''}>${item.cdValuNm}</option>
								</c:forEach>
						</select>
					</div>
					<div class="search_list ">
						<span class="label"><img src="/resources/images/icon/dot_blue.png" class="list_dot_blue"></img>과정기수명</span>
						<input type="text" name="crseNm" value="${searchData.crseNm}" class="wfull">
					</div>
				</div>
			</form>
	        <div class="tar mb10">
	        	<button class="btn btn_blue" id="search_btn">검색</button>
	        </div>
		</div>
	
		<div class="row mt30 mb20">
			<div class="col">
				<div id="grid"></div>
			</div>
		</div>
	</div>
</div>
<script>
	
	class RadioRenderer {
	    constructor(props) {
    		let rowData = grid.getRow(props.rowKey);
	    	let func = function(){
	    		setCrseData(rowData);
	    		opener.getData(getCrseData());
	    		window.close();
	    	}
			this.el = createEle_radio(props,func);
	    }
	    getElement() {
	      return this.el;
	    }
	}
	
	const column = [
	        {
	          header: '선택',
	          name: 'idx',
	          width: '50',
	          renderer : {
	        	  type : RadioRenderer
	          }
	        },
	        {
	          header: '운영방법',
	          name: 'crseOperSeCd',
	          width: '50'
	        },
	        {
	          header: '과정명',
	          name: 'crseNm',
	          width: '50'
	        },
	        {
	          header: '기수',
	          name: 'crseGnrtn',
	          width: '150'
	        },
	        {
	          header: '운영기간',
	          name: 'eduYmd',
	          width: '50'
	        }
    ];
	
	let data = $("#form").serializeObject();
	data.crseYear = data.eduBgngYmd.substring(0,4);
	const apiInfo = {
		api:{
	    	readData:{ url: 'selectCrseList.do', method:'POST', initParams: data}
	    },
	    contentType: 'application/json',
   		//initialRequest: false
	};
	const grid = createGrid_paging('grid', apiInfo, column, 500, 10);

	document.getElementById('search_btn').addEventListener('click', e => {
		let data = $("#form").serializeObject();
		if(data.eduBgngYmd != ''){
			data.crseYear = data.eduBgngYmd.substring(0,4);	
		}else if(data.eduEndYmd != ''){
			data.crseYear = data.eduEndYmd.substring(0,4);	
		}
		grid.readData(1, data, true);
	})
	
	grid.on('successResponse', function(e) {
		let response = JSON.parse(e.xhr.response);
		if(response.data.contents.length === 1){
			opener.document.getElementsByName('crseNm')[0].value = document.getElementsByName('crseNm')[0].value;
			opener.document.getElementsByName('crseOperSeCd')[0].value = document.getElementsByName('crseOperSeCd')[0].value;
			opener.document.getElementsByName('eduBgngYmd')[0].value = document.getElementsByName('eduBgngYmd')[0].value;
			opener.document.getElementsByName('eduEndYmd')[0].value = document.getElementsByName('eduEndYmd')[0].value;
		}
	});
	
	
	
</script>
</body>
</html>