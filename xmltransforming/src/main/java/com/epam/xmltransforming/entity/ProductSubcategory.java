package com.epam.xmltransforming.entity;

import java.util.ArrayList;
import java.util.List;

public class ProductSubcategory implements Cloneable{
	private String name;
	private List<Product> products;

	public ProductSubcategory() {};
	
	public ProductSubcategory(String name, List<Product> products) {
		setName(name);
		setProducts(products);
	}
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((products == null) ? 0 : products.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductSubcategory other = (ProductSubcategory) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [name=" + name + ", products=" + products
				+ "]";
	}
	
	public ProductSubcategory clone() throws CloneNotSupportedException{
		ProductSubcategory copy;
		try {
			copy = (ProductSubcategory)super.clone();
			List<Product> copiedProducts = new ArrayList<Product>();
			Product copiedProduct;
			for (Product sourceProduct: products) {
				copiedProduct = sourceProduct.clone();
				copiedProducts.add(copiedProduct);
			}
			copy.setProducts(copiedProducts);
		} catch (CloneNotSupportedException e) {
			throw e;
		}
		return copy;
	}
}
