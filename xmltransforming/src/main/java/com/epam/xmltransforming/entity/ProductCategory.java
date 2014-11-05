package com.epam.xmltransforming.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductCategory implements Cloneable{
	private String name;
	private List<ProductSubcategory> subcategories;
	
	public ProductCategory() {};
	public ProductCategory(String name, List<ProductSubcategory> subcategories) {
		setName(name);
		setSubcategories(subcategories);
	}
	public List<ProductSubcategory> getSubcategories() {
		return subcategories;
	}

	public void setSubcategories(List<ProductSubcategory> subcategories) {
		this.subcategories = subcategories;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ProductCategory clone() throws CloneNotSupportedException{
		ProductCategory copy;
		try {
			copy = (ProductCategory)super.clone();
			List<ProductSubcategory> copiedSubcategories = new ArrayList<ProductSubcategory>();
			ProductSubcategory copiedSubcategory;
			for (ProductSubcategory sourceSubcategory: this.getSubcategories()) {
				copiedSubcategory = sourceSubcategory.clone();
				copiedSubcategories.add(copiedSubcategory);
			}
			copy.setSubcategories(copiedSubcategories);
		} catch (CloneNotSupportedException e) {
			throw e;
		}
		return copy;
	}
}
