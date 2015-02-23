package com.epam.testappservice.util;

public final class QueryGenerator {
	private static final String SQL_DELETE_NEWS_QUERY = "DELETE FROM NEWS WHERE news_id in ";
	public static String generateDeleteNewsQuery(int[] ids) {
		StringBuilder deleteQuery = new StringBuilder();
		deleteQuery.append(SQL_DELETE_NEWS_QUERY);
		deleteQuery.append("(");
		for (int id: ids) {
			deleteQuery.append(id);
			deleteQuery.append(",");
		}
		deleteQuery.deleteCharAt(deleteQuery.length()-1);
		deleteQuery.append(")");
		return deleteQuery.toString();
	}
}
