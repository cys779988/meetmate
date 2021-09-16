(function ($) {	
	$.fn.serializeObject = function(){
		var obj = {};
		try {
			if(this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
				var arr = this.serializeArray();
				if(arr){
					arr.forEach((item) => {
				        obj[item.name] = item.value;
					});
				}
			}
		} catch (e) {
			alert(e.message);
		} finally {
			
		}
		return obj;
	};
})(jQuery);


function popUp(url, name){
	window.open(url, name, 'width=800, height=600, scrollbars=true, toolbar=0, menubar=no');
}