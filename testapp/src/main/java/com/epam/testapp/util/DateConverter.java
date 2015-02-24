package com.epam.testapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;
import org.apache.log4j.Logger;

/**
 * Custom date converter using MM/dd/yyyy pattern.
 */
public final class DateConverter implements Converter {
	private static final Logger log = Logger.getLogger(DateConverter.class);
	private static final String DATE_PATTERN = "MM/dd/yyyy";

	@Override
	@SuppressWarnings("rawtypes")
	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);

		if (arg1.getClass() == String.class) {
			String inputString = (String) arg1;
			Date outputDate = null;
			try {
				outputDate = dateFormat.parse(inputString);
			} catch (ParseException e) {
				log.error(e.getMessage(), e);
			}
			return outputDate;
		}

		return null;
	}

}
