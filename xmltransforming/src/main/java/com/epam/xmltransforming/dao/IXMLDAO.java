package com.epam.xmltransforming.dao;

import java.util.List;

import com.epam.xmltransforming.entity.ProductCategory;
import com.epam.xmltransforming.exception.XMLDAOException;

public interface IXMLDAO {
	public List<ProductCategory> getCategories() throws XMLDAOException;
}
