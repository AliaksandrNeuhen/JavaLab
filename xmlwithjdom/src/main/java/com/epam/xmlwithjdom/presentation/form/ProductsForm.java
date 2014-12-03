package com.epam.xmlwithjdom.presentation.form;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

public final class ProductsForm extends ActionForm {
	private static final long serialVersionUID = 0L;
	
	private Document productsDocument;
	private List<String> countOFProducts; 
	private String currCategoryName;
	private String currSubcategoryName;
	private int currCategoryIndex;
	private int currSubcategoryIndex;
	
	public ProductsForm() {
		productsDocument = new Document();
		countOFProducts = new ArrayList<String>();
		currCategoryName = "";
		currSubcategoryName = "";
	}
	public ProductsForm(Document productsDocument) {
		setProductsDocument(productsDocument);
	}
	
	public Document getProductsDocument() {
		return productsDocument;
	}
	public void setProductsDocument(Document productsDocument) {
		this.productsDocument = productsDocument;
	}
	
	public List<String> getCountOfProducts() {
		return countOFProducts;
	}
	public void setCountOfProducts(List<String> countOfProducts) {
		this.countOFProducts = countOfProducts;
	}
	
	public String getCurrCategoryName() {
		return currCategoryName;
	}
	public void setCurrCategoryName(String categoryName) {
		currCategoryName = categoryName;
	}
	
	public String getCurrSubcategoryName() {
		return currSubcategoryName;
	}
	public void setCurrSubcategoryName(String subcategoryName) {
		currSubcategoryName = subcategoryName;
	}
	
	public int getCurrCategoryIndex() {
		return currCategoryIndex;
	}
	public void setCurrCategoryIndex(int index) {
		currCategoryIndex = index;
	}
	
	public int getCurrSubcategoryIndex() {
		return currSubcategoryIndex;
	}
	public void setCurrSubcategoryIndex(int index) {
		currSubcategoryIndex = index;
	}
}
