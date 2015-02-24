package com.epam.xmltransforming.logic.validator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.epam.xmltransforming.logic.validator.handler.ProductModelHandler;

/**
 * Class used for validation of product form
 * 
 */

public final class ProductFieldsCheck {
	private static final String PRODUCTS_FILE = "products.xml";
	
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
	private static final String MODEL_EXISTS = "A product with given model has already exists";
	private static final String MODEL_FORMAT_ERROR = "Incorrect model format (model consist of "
			+ "2 big letters and 3 digits)";
	private static final String DATE_FORMAT_ERROR = "Incorrect date format (should be dd-MM-yyyy)";
	private static final String PRICE_FORMAT_ERROR = "Incorrect price "
			+ "(can be positive value or Not In Stock)";
	
	private static final String MODEL_PATTERN = "[A-Z]{2}[0-9]{3}";
	private static final String DATE_COMMON_PATTERN = "[0-3][0-9]-[01][0-9]-[0-9]{4}";
	private static final String DATE_PATTERN = "dd-MM-yyyy";
	private static final String PRICE_PATTERN = "[0-9]+";
	
	// Map containing error messages for form fields
	private Map<String, String> errorsMap;
	
	// Result of validation
	private Boolean resultOfValidation;
	
	public ProductFieldsCheck(){
		errorsMap = new HashMap<String, String>();
		resultOfValidation = false;
	}

	public boolean validate(String name, String provider, String model,
			String dateOfIssue, String color, String price, String notInStock) {
		// Clear the map containing error messages
		errorsMap.clear();
		
		// Validate name field
		if (name == null || name.length() == 0) {
			errorsMap.put(NAME_STRING, NAME_EMPTY_ERROR);
		} else if (name.length() > 100) {
			errorsMap.put(NAME_STRING, NAME_TOO_LONG_ERROR);
		}
		
		// Validate provider field
		if (provider == null || provider.length() == 0) {
			errorsMap.put(PROVIDER_STRING, PROVIDER_EMPTY_ERROR);
		} else if (provider.length() > 200) {
			errorsMap.put(PROVIDER_STRING, PROVIDER_TOO_LONG_ERROR);
		}
		
		// Validate model field
		if (model == null || model.length() == 0) {
			errorsMap.put(MODEL_STRING, MODEL_EMPTY_ERROR);
		} else if (!model.matches(MODEL_PATTERN)) {
			errorsMap.put(MODEL_STRING, MODEL_FORMAT_ERROR);
		} else {
			// Check if current model exists in products list
			List<String> productModels = new ArrayList<String>();
			try {
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
				SAXParser parser = parserFactory.newSAXParser();
				ProductModelHandler handler = new ProductModelHandler();
				URL productsUrl = getClass().getClassLoader().getResource(PRODUCTS_FILE);
				String productsFileString = productsUrl.getFile();
				File productsFile = new File(productsFileString);
				parser.parse(productsFile, handler);
				productModels = handler.getExistingModels();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (productModels.contains(model)) {
				errorsMap.put(MODEL_STRING, MODEL_EXISTS);
			}
		}
		
		// Validate date field
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
		
		// Validate color field
		if (color == null || color.length() == 0){
			errorsMap.put(COLOR_STRING, COLOR_EMPTY_ERROR);
		} else if (color.length() > 100) {
			errorsMap.put(COLOR_STRING, COLOR_TOO_LONG_ERROR);
		}
		
		// Validate price field
		Boolean notInStockBoolean = Boolean.valueOf(notInStock);
		if (!notInStockBoolean){
			if (price == null || price.length() == 0 || !price.matches(PRICE_PATTERN)) {
				errorsMap.put(PRICE_STRING, PRICE_FORMAT_ERROR);
			}
		}
		
		// Map is always empty when form is valid
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
