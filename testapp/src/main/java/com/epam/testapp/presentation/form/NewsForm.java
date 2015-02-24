package com.epam.testapp.presentation.form;

import java.util.List;

import org.apache.struts.validator.ValidatorForm;

import com.epam.testapp.model.News;

/**
 * Action form for the whole project.
 */
public final class NewsForm extends ValidatorForm {
	private static final long serialVersionUID = 1L;

	// News bean where information about currently chosen news is stored
	private News newsMessage;
	// Array of news ID's used for deleting
	private int[] newsToDelete;
	// List of News to show
	private List<News> newsList;

	public NewsForm() {
		super();
		newsMessage = new News();
	}

	public int[] getNewsToDelete() {
		return newsToDelete;
	}

	public void setNewsToDelete(int[] newsToDelete) {
		this.newsToDelete = newsToDelete;
	}

	public News getNewsMessage() {
		return newsMessage;
	}

	public void setNewsMessage(News newsMessage) {
		this.newsMessage = newsMessage;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((newsList == null) ? 0 : newsList.hashCode());
		result = prime * result
				+ ((newsMessage == null) ? 0 : newsMessage.hashCode());
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
		NewsForm other = (NewsForm) obj;
		if (newsList == null) {
			if (other.newsList != null)
				return false;
		} else if (!newsList.equals(other.newsList))
			return false;
		if (newsMessage == null) {
			if (other.newsMessage != null)
				return false;
		} else if (!newsMessage.equals(other.newsMessage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [newsMessage=" + newsMessage
				+ ", newsList=" + newsList + "]";
	}
}
