function priceCheckboxChanged(form){
	var price = document.getElementById("priceText");
	if (price.hasAttribute('readonly')) {
		price.removeAttribute('readonly');
		price.style.backgroundColor = "white"
	} else {
		price.setAttribute('readonly', 'readonly');
		price.style.backgroundColor = "darkgray";
	}
}