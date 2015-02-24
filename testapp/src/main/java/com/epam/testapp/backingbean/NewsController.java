package com.epam.testapp.backingbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.ArrayUtils;

import com.epam.testapp.exception.FetchByIdException;
import com.epam.testapp.exception.ServiceException;
import com.epam.testapp.model.News;
import com.epam.testapp.service.INewsService;
import com.epam.testapp.util.TypeChecker;

/**
 * Action form for the whole project.
 */
@ManagedBean
@ViewScoped
public class NewsController implements Serializable{
	private static final long serialVersionUID = 1L;

	// Request parameter name

	private static final String ID_PARAM = "id";
	private static final String LANG_PARAM = "language";

	// Session attribute name

	private static final String LOCALE_ATTR = "locale";

	// Page name

	private static final String NEWS_LIST_PAGE = "index.xhtml";
	private static final String NEWS_VIEW_PAGE = "viewNews.xhtml";
	private static final String NEWS_EDIT_PAGE = "editNews.xhtml";
	private static final String REDIRECT_ATTR = "?faces-redirect=true";
	private static final String ID_ATTR = "?id=";

	// News bean where information about currently chosen news stored
	private News newsMessage = new News();
	// List of news ID's used for deleting
	private List<Integer> deleteList = new ArrayList<Integer>();
	// List of News to show
	private List<News> newsList = new ArrayList<News>();

	@ManagedProperty(value="#{newsService}")
	private INewsService newsService;

	public NewsController() {
	}

	/**
	 * Change language and redirect to the same page.
	 * This method saves locale to the session and accept it
	 * at prerendering.
	 */
	public String doChangeLanguage() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		// Get language request parameter
		String language = facesContext.getExternalContext()
				.getRequestParameterMap().get(LANG_PARAM);
		// Get locale according to language parameter
		Locale newLocale = Locale.forLanguageTag(language);
		// Put locale in session
		facesContext.getExternalContext().getSessionMap().put(LOCALE_ATTR, newLocale);

		return NEWS_LIST_PAGE;
	}

	/**
	 * Forwards to news edit page.
	 * @return
	 */
	public String doEditNews() {
		Integer id = getIdRequestParameter();
		return NEWS_EDIT_PAGE + ID_ATTR + id;
	}

	/**
	 * Saves current news bean.
	 * @return
	 */
	public String doSaveNews() {
		Integer id = getIdRequestParameter();
		try {
			newsMessage.setId(id);
			id = newsService.save(newsMessage);
		} catch (ServiceException e) {
			e.printStackTrace();
			id = 0;
		}
		// Put current news id to the session
		FacesContext.getCurrentInstance().getExternalContext()
								.getSessionMap().put(ID_PARAM, id);
		// Redirect for preventing repeat saving
		return NEWS_VIEW_PAGE + REDIRECT_ATTR;
	}

	/**
	 * Delete checked news, if invoked from index page
	 * or delete current news bean, if invoked from view page.
	 */
	public String doDeleteNews() {
		Integer id = getIdRequestParameter();
		// If id request equals 0, then method has been invoked
		// from view page
		if (id != 0) {
			deleteList.add(id);
		} else {
			// Build list containing IDs of news to delete
			deleteList = buildDeleteList(newsList);
		}

		try {
			// Transform list to array of ints
			Integer[] deleteArray = deleteList.toArray(new Integer[deleteList.size()]);
			int [] newsToDelete = ArrayUtils.toPrimitive(deleteArray);

			newsService.remove(newsToDelete);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		// Redirect to the index page for preventing repeat invoking
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return NEWS_LIST_PAGE;
	}

	/**
	 * Build list of IDs of news to delete.
	 * @param newsList - list of news
	 * @return list of IDs
	 */
	private List<Integer> buildDeleteList(List<News> newsList) {
		List<Integer> deleteList = new ArrayList<Integer>();
		for (News currNews: newsList) {
			if (currNews.getMarkedForDeletion()) {
				deleteList.add(currNews.getId());
			}
		}
		return deleteList;
	}

	/**
	 * Cancel editing news and forwarding to the index page
	 * @return
	 */
	public String doCancelEditing() {
		return NEWS_LIST_PAGE;
	}

	/**
	 * Initialize list of news
	 */
	public void initNewsList() {
		try {
			newsList = newsService.getList();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize news bean for showing on view page
	 */
	public void initNewsView() {
		Integer id = getIdRequestParameter();
		// If there is no request ID parameter
		// try to find it in session
		if (id == 0) {
			id = (Integer)FacesContext.getCurrentInstance()
					.getExternalContext().getSessionMap().get(ID_PARAM);
		}

		// Get news object
		try {
			newsMessage = newsService.fetchByID(id);
		} catch (FetchByIdException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize news bean for editing.
	 * If there is no ID parameter in request
	 * then news bean will be created,
	 * else existing news will be edited.
	 */
	public void initNewsEdit() {
		Integer id = getIdRequestParameter();
		try {
			if (id != 0) {
				newsMessage = newsService.fetchByID(id);
			}
		} catch (FetchByIdException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get ID parameter from request
	 * @return id parameter
	 * @throws NumberFormatException
	 */
	private Integer getIdRequestParameter() {
		String idString = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap().get(ID_PARAM);
		Integer id;
		if (TypeChecker.isInteger(idString)) {
			id = Integer.valueOf(idString);
		} else {
			id = 0;
		}
		return id;
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}

	public INewsService getNewsService() {
		return newsService;
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
	public String toString() {
		return getClass().getSimpleName() + " [newsMessage=" + newsMessage
				+ ", newsList=" + newsList + "]";
	}
}
