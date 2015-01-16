package com.epam.employees.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Tag for pagination.
 *
 */
public final class PaginatorTag extends SimpleTagSupport{

	private int firstRecord; // Number of first record to show
	private int numOfRecords; // The amount of records to show
	private int prevNumOfRecords; // Previous amount of records to show
	
	public void setFirstRecord(int firstRecord) {
		this.firstRecord = firstRecord;
	}
	public void setNumOfRecords(int numOfRecords) {
		this.numOfRecords = numOfRecords;
	}
	
	public void setPrevNumOfRecords(int prevNumOfRecords) {
		this.prevNumOfRecords = prevNumOfRecords;
	}
	
	public void doTag() {
		Writer out = getJspContext().getOut();
		try {
			out.write("<form action=\"emp\" method=\"POST\">");
				out.write("<input type=\"hidden\" name=\"first\" value=\"" + firstRecord + "\"/>");
				out.write("<input type=\"hidden\" name=\"prev_max\" value=\"" + prevNumOfRecords + "\"/>");
				out.write("<input type=\"number\" min=\"1\" max=\"1000\" required name=\"max\" value=\"" + numOfRecords + "\"/>");
				out.write("<input type=\"submit\" name=\"action\" value=\"prev\"/>");
				out.write("<input type=\"submit\" name=\"action\" value=\"next\"/>");
				out.write("<br>Record " + firstRecord + "-" + (firstRecord+numOfRecords));
			out.write("</form>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
