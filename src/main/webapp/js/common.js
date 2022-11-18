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

$(document).ajaxError((e, xhr, opt, err) => {
	e.preventDefault();
	e.stopPropagation();
	$('p.errorText').empty();
	const responseText = JSON.parse(xhr.responseText);
	const fieldErrorMessage = responseText.errors;
	
	if(fieldErrorMessage.length > 0) {
		fieldErrorMessage.forEach((error, i) => {
			$('#'+error.field+'Error').append(error.reason);
			
			if(i === 0) {
				$('#'+error.field).focus();
			}
		});
	} else {
		alert(responseText.message);
	}
});

function popUp(url, name){
	window.open(url, name, 'width=800, height=600, scrollbars=true, toolbar=0, menubar=no');
}