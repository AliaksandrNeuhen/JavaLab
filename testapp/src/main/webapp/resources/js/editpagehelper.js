	var date = document.getElementById("editform:texteditdate");
	date.addEventListener("keypress", function(event) {
		if (event.keyCode < 32 || event.keyCode === 47){
			return null;
		} else {
			if (date.value.length === 2 || date.value.length === 5){
				date.value += "/";
			}
		}
		} , false);
	var oldTitleValue = document.getElementById("editform:textedittitle").value;
	var oldDateValue = document.getElementById("editform:texteditdate").value;
	var oldBriefValue = document.getElementById("editform:textareaeditbrief").value;
	var oldContentValue = document.getElementById("editform:textareaeditcontent").value;