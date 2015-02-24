package com.epam.xmltransforming.entity;

/**
 * Product entity
 * 
 */

public class Product implements Cloneable{
	private String name;
	private String model;
	private String provider;
	private String dateOfIssue;
	private String color;
	private Integer price;
	
	public Product() {};
	
	public Product(String name, String model, String provider, String dateOfIssue,
			String color, Integer price) {
		setName(name);
		setModel(model);
		setProvider(provider);
		setDateOfIssue(dateOfIssue);
		setColor(color);
		setPrice(price);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getProvider() {
		return provider;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result
				+ ((dateOfIssue == null) ? 0 : dateOfIssue.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
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
		Product other = (Product) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (dateOfIssue == null) {
			if (other.dateOfIssue != null)
				return false;
		} else if (!dateOfIssue.equals(other.dateOfIssue))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  getClass().getSimpleName() + " [name=" + name + ", model=" + model + ", provider="
				+ provider + ", dateOfIssue=" + dateOfIssue + ", color="
				+ color + ", price=" + price + "]";
	}
	
	public Product clone() throws CloneNotSupportedException{
		Product copy;
		try {
			copy = (Product)super.clone();
		} catch (CloneNotSupportedException e){
			throw e;
		}
		return copy;
	}
}
