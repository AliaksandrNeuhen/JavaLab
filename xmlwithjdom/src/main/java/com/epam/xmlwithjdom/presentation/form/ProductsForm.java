package com.epam.xmlwithjdom.presentation.form;

import org.apache.struts.action.ActionForm;
import org.jdom2.Document;

public final class ProductsForm extends ActionForm {
	private static final long serialVersionUID = 0L;
	
	private Document productsDocument;
	private String currCategoryName;
	private String currSubcategoryName;
	private int currCategoryIndex;
	private int currSubcategoryIndex;
	private String models;
	
	public ProductsForm() {
		productsDocument = new Document();
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
	
	public String getModels() {
		return models;
	}
	public void setModels(String models) {
		this.models = models;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [productsDocument=" + productsDocument
				+ ", currCategoryName="
				+ currCategoryName + ", currSubcategoryName="
				+ currSubcategoryName + ", currCategoryIndex="
				+ currCategoryIndex + ", currSubcategoryIndex="
				+ currSubcategoryIndex + ", models=" + models + "]";
	}
}
