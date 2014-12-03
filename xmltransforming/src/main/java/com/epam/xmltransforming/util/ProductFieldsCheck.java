package com.epam.xmltransforming.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public final class ProductFieldsCheck {
	private static final String NAME_STRING = "name";
	private static final String PROVIDER_STRING = "provider";
	private static final String MODEL_STRING = "model";
	private static final String DATE_STRING = "dateOfIssue";
	private static final String COLOR_STRING = "color";
	private static final String PRICE_STRING = "price";
	
	private static final String NAME_EMPTY_ERROR = "You should fill the name field";
	private static final String PROVIDER_EMPTY_ERROR = "You should fill the provider field";
	private static final String MODEL_EMPTY_ERROR = "You should fill the model field";
	private static final String DATE_EMPTY_ERROR = "You should fill the date field";
	private static final String COLOR_EMPTY_ERROR = "You should fill the color field";
	private static final String NAME_TOO_LONG_ERROR = "Name field is too long";
	private static final String PROVIDER_TOO_LONG_ERROR = "Provider field is too long";
	private static final String COLOR_TOO_LONG_ERROR = "Color field is too long";
	private static final String MODEL_FORMAT_ERROR = "Incorrect model format (model consist of "
			+ "2 big letters and 3 digits)";
	private static final String DATE_FORMAT_ERROR = "Incorrect date format (should be dd-MM-yyyy)";
	private static final String PRICE_FORMAT_ERROR = "Incorrect price "
			+ "(can be positive value or Not In Stock)";
	
	private static final String MODEL_PATTERN = "[A-Z]{2}[0-9]{3}";
	private static final String DATE_COMMON_PATTERN = "[0-3][0-9]-[01][0-9]-[0-9]{4}";
	private static final String DATE_PATTERN = "dd-MM-yyyy";
	private static final String PRICE_PATTERN = "[0-9]+";
	
	private Map<String, String> errorsMap;
	private Boolean resultOfValidation;
	
	public ProductFieldsCheck(){
		errorsMap = new HashMap<String, String>();
		resultOfValidation = false;
	}

	public boolean validate(String name, String provider, String model,
			String dateOfIssue, String color, String price, String notInStock) {
		errorsMap.clear();
		if (name == null || name.length() == 0) {
			errorsMap.put(NAME_STRING, NAME_EMPTY_ERROR);
		} else if (name.length() > 100) {
			errorsMap.put(NAME_STRING, NAME_TOO_LONG_ERROR);
		}
		
		if (provider == null || provider.length() == 0) {
			errorsMap.put(PROVIDER_STRING, PROVIDER_EMPTY_ERROR);
		} else if (provider.length() > 200) {
			errorsMap.put(PROVIDER_STRING, PROVIDER_TOO_LONG_ERROR);
		}
		
		if (model == null || model.length() == 0) {
			errorsMap.put(MODEL_STRING, MODEL_EMPTY_ERROR);
		} else if (!model.matches(MODEL_PATTERN)) {
			errorsMap.put(MODEL_STRING, MODEL_FORMAT_ERROR);
		}
		
		if (dateOfIssue == null || dateOfIssue.length() == 0) {
			errorsMap.put(DATE_STRING, DATE_EMPTY_ERROR);
		} else {
			if (!dateOfIssue.matches(DATE_COMMON_PATTERN)) {
				errorsMap.put(DATE_STRING, DATE_FORMAT_ERROR);
			} else {
				SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
				format.setLenient(false);
				try {
					format.parse(dateOfIssue);	
				} catch (ParseException e) {
					e.printStackTrace();
					errorsMap.put(DATE_STRING, DATE_FORMAT_ERROR);
				}	
			}
		}
		
		if (color == null || color.length() == 0){
			errorsMap.put(COLOR_STRING, COLOR_EMPTY_ERROR);
		} else if (color.length() > 100) {
			errorsMap.put(COLOR_STRING, COLOR_TOO_LONG_ERROR);
		}
		
		System.out.println(notInStock);
		Boolean notInStockBoolean = Boolean.valueOf(notInStock);
		if (!notInStockBoolean){
			if (price == null || price.length() == 0 || !price.matches(PRICE_PATTERN)) {
				errorsMap.put(PRICE_STRING, PRICE_FORMAT_ERROR);
			}
		}
		
		if (errorsMap.isEmpty()) {
			resultOfValidation = true;
			return true;
		} else {
			resultOfValidation = false;
			return false;
		}
	}
	
	
	public String getErrorsForName() {
		return errorsMap.get(NAME_STRING);
	}
	public String getErrorsForProvider() {
		return errorsMap.get(PROVIDER_STRING);
	}
	public String getErrorsForModel() {
		return errorsMap.get(MODEL_STRING);
	}
	public String getErrorsForDateOfIssue() {
		return errorsMap.get(DATE_STRING);
	}
	public String getErrorsForColor() {
		return errorsMap.get(COLOR_STRING);
	}
	public String getErrorsForPrice() {
		return errorsMap.get(PRICE_STRING);
	}
	public Boolean getResultOfValidation() {
		return resultOfValidation;
	}
}
