	var date = document.getElementById("texteditdate");
	date.addEventListener("keypress", function(event) {
		if (event.keyCode < 32 || event.keyCode === 47){
			return null;
		} else {
			if (date.value.length === 2 || date.value.length === 5){
				date.value += "/";
			}
		}
		} , false);
	var oldTitleValue = document.getElementById("textedittitle").value;
	var oldDateValue = document.getElementById("texteditdate").value;
	var oldBriefValue = document.getElementById("textareaeditbrief").value;
	var oldContentValue = document.getElementById("textareaeditcontent").value;