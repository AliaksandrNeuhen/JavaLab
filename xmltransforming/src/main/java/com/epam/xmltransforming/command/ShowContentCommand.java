package com.epam.xmltransforming.command;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epam.xmltransforming.dao.IXMLDAO;
import com.epam.xmltransforming.dao.XMLDAOFactory;
import com.epam.xmltransforming.entity.ProductCategory;
import com.epam.xmltransforming.exception.CommandException;
import com.epam.xmltransforming.exception.XMLDAOException;

public class ShowContentCommand implements ICommand {
	private static final String EXC_GET_DAO = "Can't get DAO";
	private static final String JSP_PAGE_PRODUCTS = "products.jsp";
	private static final String JSP_PAGE_ERROR = "error.jsp";
	private static final String REQUEST_PARAMETER_PARSERTYPE = "parsertype";
	private static final String REQUEST_ATTR_CATEGORIES = "categories";
	
	public String execute(HttpServletRequest request) throws CommandException {
		String forward = JSP_PAGE_PRODUCTS;
		List<ProductCategory> productCategories;
		try {
			String daoType = request.getParameter(REQUEST_PARAMETER_PARSERTYPE);
			IXMLDAO dao = XMLDAOFactory.getInstance().getDao(daoType);
			if (dao == null) {
				return JSP_PAGE_ERROR;
			}
			productCategories = dao.getCategories();
			request.setAttribute(REQUEST_ATTR_CATEGORIES, productCategories);
		} catch (XMLDAOException e) {
			throw new CommandException(EXC_GET_DAO, e);
		}
		return forward;
	}
}
