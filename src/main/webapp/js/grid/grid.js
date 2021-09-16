/**
 * 
 */
	/* el : div id
	 * apiInfo : JSON {호출 url, method, param}
	 * columnData : JSON {column 데이터}
	 * height : grid 높이
	 */
	function createGrid_scroll(el, apiInfo, columnData, height) {
		if(height===null) height = 'auto';
		return new tui.Grid({
	        el: document.getElementById(el),
	        data: apiInfo,
	        scrollX: false,
	        scrollY: true,
	        bodyHeight: height,
	        minBodyHeight : height,
	        rowHeaders:[
	        	{
	                type: 'checkbox'
				}
	        ],
	        columns: columnData
	      });
	}
	
	/* el : div id
	 * apiInfo : JSON {호출 url, method, param}
	 * columnData : JSON {column 데이터}
	 * height : grid 높이
	 * page : 페이지당 조회데이터 개수
	 */
	function createGrid_paging(el, apiInfo, columnData, height, page) {
		if(height===null) height = 'auto';
		return new tui.Grid({
			el: document.getElementById(el),
			data: apiInfo,
			scrollX: false,
			scrollY: false,
			bodyHeight: height,
			minBodyHeight : height,
			pageOptions: {
				perPage: page
			},
			rowHeaders:[
				{
					type: 'checkbox'
				}
				],
				columns: columnData
		});
	}
	
	/* button 생성 함수
	 * props : 생성자 함수로 전달받은 parameter
	 * name : button 명칭
	 * func : callback 함수
	 */
	function createEle_btn(props, name, func){
		const { grid, rowKey } = props;
		const button = document.createElement('input');
		button.type = 'button';
		button.className = 'btn btn btn-secondary';
		button.setAttribute('for', String(rowKey));
		button.value = name;
		button.addEventListener('click', func);
		
		const label = document.createElement('label');
		label.appendChild(button);
		return label;
	}

	/* input type='text' + button 생성함수
	 * props : 생성자 함수로 전달받은 parameter
	 * name : button 명칭
	 * func : callback 함수
	 */
	function createEle_textbtn(props, name, func){
		const { grid, rowKey } = props;
		const button = document.createElement('input');
		button.type = 'button';
		button.className = 'btn btn_gray_line_small';
		button.setAttribute('for', String(rowKey));
		button.value = name;
		button.addEventListener('click', func);
		
		const text = document.createElement('input');
		text.type = 'text';
		text.value = props.value;
		const label = document.createElement('label');
		label.appendChild(text);
		label.appendChild(button);
		return label;
	}
	
	/* row num 생성함수
	 * props : 생성자 함수로 전달받은 parameter
	 */
	function createEle_num(props){
		const { grid, rowKey } = props;
		const label = document.createElement('label');
		
		const span = document.createElement('span');
		
		span.innerHTML = rowKey+1;
		label.appendChild(span);
		return label;
	}
	
	/* input type='text' 생성함수
	 * props : 생성자 함수로 전달받은 parameter
	 */
	function createEle_text(props) {
		const el = document.createElement('input');
		const{maxLength} = props.columnInfo.editor.options;
		
		el.type="text";
		el.maxLength = maxLength;
		el.value = String(props.value);
		
		
		el.addEventListener('focusout', e => {
			var gridData = grid.getRow(props.rowKey);
			props.columnInfo.__storage__.name = e.target.value;
			grid.dataManager.push('UPDATE', gridData);
		})
		return el;
	}

	/* input type='text' 생성함수
	 * props : 생성자 함수로 전달받은 parameter
	 */
	function createEle_ahref(props, func) {
	   const el = document.createElement('a');
	   el.href = "#";
	   el.text = props.value;
	   el.addEventListener('click', func);
	   return el;
	}