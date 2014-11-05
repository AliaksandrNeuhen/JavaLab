package com.epam.testapp.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.FieldChecks;

/**
 * Class containing custom validation methods. Methods check if current action
 * is "save", and if it is true, delegate validation to
 * org.apache.struts.validator.FieldChecks class.
 *
 */
public final class CustomRequiredFieldChecker {

	/**
	 * Validates if required field is filled.
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @param validator
	 * @param request
	 * @return true if required field is filled, false if not.
	 */
	public static boolean validateRequired(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, Validator validator,
			HttpServletRequest request) {

		String methodName = request.getParameter("method");
		if (!"save".equals(methodName)) {
			return true;
		}

		return FieldChecks.validateRequired(bean, va, field, errors, validator,
				request);
	}

	/**
	 * Validates if field is matching the corresponding mask.
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @param validator
	 * @param request
	 * @return true if field is matching the mask, false if not.
	 */
	public static boolean validateMask(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, Validator validator,
			HttpServletRequest request) {

		String methodName = request.getParameter("method");
		if (!"save".equals(methodName)) {
			return true;
		}

		return FieldChecks.validateMask(bean, va, field, errors, validator,
				request);
	}

	/**
	 * Validates if field isn't exceed it's maximum length
	 * 
	 * @param bean
	 * @param va
	 * @param field
	 * @param errors
	 * @param validator
	 * @param request
	 * @return true if field isn't exceed it's maximum length, false if it is.
	 */
	public static boolean validateMaxLength(Object bean, ValidatorAction va,
			Field field, ActionMessages errors, Validator validator,
			HttpServletRequest request) {

		String methodName = request.getParameter("method");
		if (!"save".equals(methodName)) {
			return true;
		}

		return FieldChecks.validateMaxLength(bean, va, field, errors,
				validator, request);
	}
}
