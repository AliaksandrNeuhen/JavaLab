package com.epam.testapp.presentation.action;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.epam.testapp.exception.FetchByIdException;
import com.epam.testapp.exception.NewsNotFoundException;
import com.epam.testapp.exception.ProjectException;
import com.epam.testapp.exception.ServiceException;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.NewsForm;
import com.epam.testapp.service.INewsService;

public final class NewsAction extends DispatchAction {

	private static final String LANGUAGE = "language";

	// Forwards

	private static final String FORWARD_LIST = "news_list";
	private static final String FORWARD_VIEW = "news_view";
	private static final String FORWARD_EDIT = "news_edit";

	// Session attributes

	private static final String ATTR_CURR_PAGE = "current_page";
	private static final String ATTR_PREV_PAGE = "previous_page";

	// Exception messages

	private static final String EXC_LIST = "Can't get list of news";
	private static final String EXC_VIEW = "Can't get news";
	private static final String EXC_DELETE = "Can't delete news";
	private static final String EXC_SAVE = "Can't save news";

	private static final Logger log = Logger.getLogger(NewsAction.class);
	private INewsService newsService;

	/**
	 * Changes language according to request attribute "language".
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward changeLanguage(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		// Get language tag from request
		String language = request.getParameter(LANGUAGE);
		HttpSession session = request.getSession();
		// Set the locale to session locale attribute according to language tag
		session.setAttribute(Globals.LOCALE_KEY,
				Locale.forLanguageTag(language));

		// Get current page from session
		String currPage = (String) session.getAttribute(ATTR_CURR_PAGE);
		return actionMapping.findForward(currPage);
	}

	/**
	 * Method for displaying NewsList page. Reloads list of news in the action
	 * form.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ProjectException
	 *             if something is wrong with Service
	 */

	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ProjectException {

		NewsForm form = (NewsForm) actionForm;
		try {
			// Get list of news
			List<News> newsList = newsService.getList();
			form.setNewsList(newsList);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new ProjectException(EXC_LIST, e);
		}

		// Saves current and previous page in session
		addSessionPageAttribute(request, FORWARD_LIST);

		return actionMapping.findForward(FORWARD_LIST);
	}

	/**
	 * Method for displaying NewsView page Gets news by request parameter "id"
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ProjectException
	 *             if something is wrong with Service
	 * @throws NewsNotFoundException
	 *             if news with corresponding ID not found
	 */

	public ActionForward view(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ProjectException {

		NewsForm form = (NewsForm) actionForm;
		try {
			int id = form.getNewsMessage().getId();
			News news = newsService.fetchByID(id);
			form.setNewsMessage(news);
		} catch (FetchByIdException e) {
			log.error(e.getMessage(), e);
			throw new NewsNotFoundException(EXC_VIEW, e);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new ProjectException(EXC_VIEW, e);
		}

		// Saves current and previous page in session
		addSessionPageAttribute(request, FORWARD_VIEW);

		return actionMapping.findForward(FORWARD_VIEW);
	}

	/**
	 * Method for deleting news from list. This method gets array of IDs from
	 * action form and deletes news corresponding to this IDs
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ProjectException
	 *             if something is wrong with Service
	 */

	public ActionForward delete(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ProjectException {

		NewsForm form = (NewsForm) actionForm;
		int[] newsToDelete = null;
		try {
			// if array of IDs in the form is not null,
			// then delete news with ID from array
			// else delete news in NewsMessage
			if (form.getNewsToDelete() != null) {
				newsToDelete = form.getNewsToDelete();
			} else {
				newsToDelete = new int[1];
				int id = form.getNewsMessage().getId();
				newsToDelete[0] = Integer
						.valueOf(id);
			}

			newsService.remove(newsToDelete);

			// Reload list of news
			List<News> newsList = newsService.getList();
			form.setNewsList(newsList);

			// reset array of IDs
			int[] newArray = null;
			form.setNewsToDelete(newArray);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new ProjectException(EXC_DELETE, e);
		}

		// Saves current and previous page in session
		addSessionPageAttribute(request, FORWARD_LIST);

		return actionMapping.findForward(FORWARD_LIST);
	}

	/**
	 * Method for saving news
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ProjectException
	 *             if something is wrong with Service
	 */

	public ActionForward save(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ProjectException {

		NewsForm form = (NewsForm) actionForm;
		News newsToSave = form.getNewsMessage();

		try {
			// Update existing news or add a new one
			newsService.save(newsToSave);

			// Reload list of news
			List<News> newsList = newsService.getList();
			form.setNewsList(newsList);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new ProjectException(EXC_SAVE, e);
		}

		// Saves current and previous page in session
		addSessionPageAttribute(request, FORWARD_VIEW);

		return actionMapping.findForward(FORWARD_VIEW);
	}

	/**
	 * Method for showing NewsEdit page for editing existing news Saves news
	 * with ID corresponding to request parameter in action form.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ProjectException
	 *             if something is wrong with Service
	 */
	public ActionForward edit(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ProjectException {

		NewsForm form = (NewsForm) actionForm;

		try {
			// Get news ID from request
			int id = form.getNewsMessage().getId();
			// Get news to edit
			News fetchedNews = newsService.fetchByID(id);
			form.setNewsMessage(fetchedNews);
		} catch (FetchByIdException e) {
			log.error(e.getMessage(), e);
			throw new NewsNotFoundException(EXC_VIEW, e);
		} catch (ServiceException e) {
			log.error(e.getMessage(), e);
			throw new ProjectException(EXC_VIEW, e);
		}

		// Saves current and previous page in session
		addSessionPageAttribute(request, FORWARD_EDIT);

		return actionMapping.findForward(FORWARD_EDIT);
	}

	/**
	 * Method for showing NewsEdit page for adding news
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 */

	public ActionForward add(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {

		NewsForm form = (NewsForm) actionForm;

		form.setNewsMessage(new News());

		// Saves current and previous page in session
		addSessionPageAttribute(request, FORWARD_EDIT);

		return actionMapping.findForward(FORWARD_EDIT);
	}

	/**
	 * Method for cancelling editing news. If news bean in action form is empty,
	 * returns to news list page. If news bean in action form is not empty,
	 * returns to previous page.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws ProjectException
	 *             if something is wrong with service
	 */

	public ActionForward cancel(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws ProjectException {

		HttpSession session = request.getSession();
		String prevPage;
		NewsForm form = (NewsForm) actionForm;
		int id = form.getNewsMessage().getId();
		if (id == 0) {
			prevPage = FORWARD_LIST;
		} else {
			prevPage = (String) session.getAttribute(ATTR_PREV_PAGE);
			try {
				News news = newsService.fetchByID(id);
				form.setNewsMessage(news);
			} catch (ServiceException e) {
				log.error(e.getMessage(), e);
				throw new ProjectException(EXC_VIEW, e);
			} catch (FetchByIdException e) {
				log.error(e.getMessage(), e);
				throw new ProjectException(EXC_VIEW, e);
			}
		}

		String currPage = (String) session.getAttribute(ATTR_CURR_PAGE);
		session.setAttribute(ATTR_CURR_PAGE, prevPage);
		session.setAttribute(ATTR_PREV_PAGE, currPage);

		return actionMapping.findForward(prevPage);
	}

	/**
	 * Manipulates session currPage and prevPage attributes for correct page
	 * directing after changing language or pressing "Cancel" button.
	 * 
	 * @param request
	 * @param currPage
	 */
	private static void addSessionPageAttribute(HttpServletRequest request,
			String currPage) {
		HttpSession session = request.getSession();
		String currPageInSession = (String) session
				.getAttribute(ATTR_CURR_PAGE);

		// If page wasn't changed, session attributes aren't changing
		if (!currPage.equals(currPageInSession)) {
			session.setAttribute(ATTR_PREV_PAGE, currPageInSession);
			session.setAttribute(ATTR_CURR_PAGE, currPage);
		}
	}

	public void setNewsService(INewsService newsService) {
		this.newsService = newsService;
	}
}