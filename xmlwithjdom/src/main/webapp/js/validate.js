function validateName(field, index) {
	if (field.value.length == 0 || field.value.length > 100) {
		field.classList.add("invalid");
		var errorMessage = document.getElementById("nameError" + index);
		errorMessage.style.display = "block";
		errorMessage.innerHTML = "Please, fill the name field.";
	} else {
		field.classList.remove("invalid");
		var errorMessage = document.getElementById("nameError" + index);
		errorMessage.style.display = "none";
	}
}

function validateProvider(field, index) {
	if (field.value.length == 0 || field.value.length > 100) {
		field.classList.add("invalid");
		var errorMessage = document.getElementById("providerError" + index);
		errorMessage.style.display = "block";
		errorMessage.innerHTML = "Please, fill the provider field.";
	} else {
		field.classList.remove("invalid");
		var errorMessage = document.getElementById("providerError" + index);
		errorMessage.style.display = "none";
	}
}

function validateColor(field, index) {
	if (field.value.length == 0 || field.value.length > 100) {
		field.classList.add("invalid");
		var errorMessage = document.getElementById("colorError" + index);
		errorMessage.style.display = "block";
		errorMessage.innerHTML = "Please, fill the color field.";
	} else {
		field.classList.remove("invalid");
		var errorMessage = document.getElementById("colorError" + index);
		errorMessage.style.display = "none";
	}
}

function validateModel(field, index, models) {
	var model = field.value;
	var modelFormat = /[A-Z]{2}[0-9]{3}/;
	if (!modelFormat.test(model)) {
		field.classList.add("invalid");
		var errorMessage = document.getElementById("modelError" + index);
		errorMessage.style.display = "block";
		errorMessage.innerHTML = "Incorrect model format (should consist of 2 big letters and 3 numbers)."
	} else {
		var modelsArray = models.split(" ");
		var i;
		var isExist = false;
		for (i = 0; i < modelsArray.length; i++) {
			if (model == modelsArray[i]) {
				isExist = true;
			}
		}
		var modelInputs = document.getElementsByClassName("model");
		for (i = 0; i < modelInputs.length; i++) {
			if ((modelInputs[i].value == model) && (i != index)) {
				field.classList.add("invalid");
				var errorMessage = document.getElementById("modelError" + index);
				errorMessage.style.display = "block";
				errorMessage.innerHTML = "Duplicate model name.";
				return;
			}
		}
		if (isExist == true) {
			field.classList.add("invalid");
			var errorMessage = document.getElementById("modelError" + index);
			errorMessage.style.display = "block";
			errorMessage.innerHTML = "A product with current model name is already exists. Please, enter another model name";
		} else {
			field.classList.remove("invalid");
			var errorMessage = document.getElementById("modelError" + index);
			errorMessage.style.display = "none";
		}
	}
}

function validateDate(field, index) {
	var date = field.value;
	var dateFormat = new RegExp("(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.]((19|20)\\d\\d)");
	if (!dateFormat.test(date)) {
		field.classList.add("invalid");
		var errorMessage = document.getElementById("dateError" + index);
		errorMessage.style.display = "block";
		errorMessage.innerHTML = "Incorrect date format (should be dd-MM-yyyy).";
	} else {
		field.classList.remove("invalid");
		var errorMessage = document.getElementById("dateError" + index);
		errorMessage.style.display = "none";
	}
}

function validatePrice(field, index) {
	var price = field.value;
	if (isNaN(price)) {
		field.classList.add("invalid");
		var errorMessage = document.getElementById("priceError" + index);
		errorMessage.style.display = "block";
		errorMessage.innerHTML = "Incorrect price. Please, enter a valid number value or leave the field " +
				"empty if product is not in stock";
	} else {
		field.classList.remove("invalid");
		var errorMessage = document.getElementById("priceError" + index);
		errorMessage.style.display = "none";
	}
}

function validateForm(form) {
	var inputs = document.getElementsByClassName("validationField");
	var i = 0;
	for (i = 0; i < inputs.length; i ++) {
		console.log(inputs[i]);
		if (inputs[i].classList.contains("invalid")) {
			alert("Please, fix all errors before saving.");
			return false;
		}
	}
	return true;
}