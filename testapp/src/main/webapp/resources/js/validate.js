	function checkDelete(form){
		var totalCount = form.elements.length;
		var countUnchecked = 0;
		for (var i = 0; i < totalCount; i++){
			if (!form.elements[i].checked){
				countUnchecked = countUnchecked + 1;
			}
		}
		if (countUnchecked === totalCount){
			alert(chooseNewsAlert);
			return false;
		} else {
			if (confirm(confirmDeleteCheckedAlert)){
				return true;
			} else {
				return false;
			}
		}
	}

	function checkDeleteSingle(form){
		if (confirm(confirmDeleteSingleAlert)){
			return true;
		} else {
			return false;
		}
	}

	function checkCancel(form){
		var newTitleValue = document.getElementById("editform:textedittitle").value;
		var newDateValue = document.getElementById("editform:texteditdate").value;
		var newBriefValue = document.getElementById("editform:textareaeditbrief").value;
		var newContentValue = document.getElementById("editform:textareaeditcontent").value;
		if (newTitleValue == oldTitleValue && newDateValue == oldDateValue
				&& newBriefValue == oldBriefValue && newContentValue == oldContentValue) {
//			var hiddenValue = document.getElementById("method");
//			hiddenValue.value = "cancel";
			return true;
		}
		if (confirm(confirmCancelAlert)) {
//			var hiddenValue = document.getElementById("method");
//			hiddenValue.value = "cancel";
			return true;
		} else {
			return false;
		}
	}

	function validateNews(form){
		var title = document.getElementById("editform:textedittitle");
		var date = document.getElementById("editform:texteditdate");
		var brief = document.getElementById("editform:textareaeditbrief");
		var content = document.getElementById("editform:textareaeditcontent");
		var alertMessage = "";
		var result = true;
		if (title.value.length === 0){
			alertMessage += fillTitleAlert;
			alertMessage += "\n";
			result = false;
		}
		if (brief.value.length === 0){
			alertMessage += fillBriefAlert;
			alertMessage += "\n";
			result = false;
		}
		if (content.value.length === 0){
			alertMessage += fillContentAlert;
			alertMessage += "\n";
			result = false;
		}

		if (title.value.length > 100){
			alertMessage += titleLongAlert;
			alertMessage += "\n";
			result = false;
		}
		if (brief.value.length > 500){
			alertMessage += briefLongAlert;
			alertMessage += "\n";
			result = false;
		}
		if (content.value.length > 2000){
			alertMessage += contentLongAlert;
			alertMessage += "\n";
			result = false;
		}
		var dateFormat = /^(\d\d)\/(\d\d)\/(\d\d\d\d)$/;
		if (!dateFormat.test(date.value)){
			alertMessage += wrongDateAlert;
			alertMessage += "\n";
			result = false;
		} else {
			var month = date.value.split("/")[0];
			var day = date.value.split("/")[1];
			var year = date.value.split("/")[2];
			correctDate = new Date(year, month - 1, day);
			if (correctDate.getMonth() + 1 != month) {
				alertMessage += incorrectMonthAlert;
				alertMessage += "\n";
				result = false;
			}
			if (correctDate.getDate() != day) {
				alertMessage += incorrectDayAlert;
				alertMessage += "\n";
				result = false;
			}
			if (correctDate.getFullYear() != year) {
				alertMessage += incorrectYearAlert;
				alertMessage += "\n";
				result = false;
			}
		}
		if (result == false){
			alert(alertMessage);
		}
		return result;
	}