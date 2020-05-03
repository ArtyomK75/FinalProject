package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.Bool;
import ua.nure.kostenko.finalProject.db.DBManager;
import ua.nure.kostenko.finalProject.db.entity.Count;
import ua.nure.kostenko.finalProject.db.entity.CreditCard;
import ua.nure.kostenko.finalProject.db.entity.UnlockRequest;
import ua.nure.kostenko.finalProject.db.entity.User;
import ua.nure.kostenko.finalProject.exception.AppException;
import ua.nure.kostenko.finalProject.exception.Messages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Lists menu items.
 * 
 * @author A.Kostenko
 * 
 */
public class EditUserCountsCommand extends Command {

	private static final long serialVersionUID = 7675464314029473405L;

	private static final Logger LOG = Logger.getLogger(EditUserCountsCommand.class);

	private static final String ACTION = "editUserCounts";
	private static final String ACTION_CREDIT_CARD = "editUserCreditCardBegin";
	private static final String ADD_COUNT = "addCount";
	private static final String END_EDIT = "endEdit";
	private static final String FISH_COUNT = "UA000000000000000000000000000";
	private static final String NAME_BUTTON_ADD = "button.add_count";
	private static final String NAME_BUTTON_EDIT = "button.edit_count";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		String stage = request.getParameter("stage");
		String forward = "";
		if (stage != null & END_EDIT.equals(stage)) {
			forward = endEditUserCounts(request, response);
		} else {
			forward = beginEditUserCounts(request, response);
		}
		LOG.debug("Command finished");
		return forward;
	}

	private String beginEditUserCounts(HttpServletRequest request,
									   HttpServletResponse response) throws IOException, ServletException, AppException {

		String userId = request.getParameter("userId");
		String editCount = request.getParameter("editCount");
		String editCreditCard = request.getParameter("editCreditCard");
		User user = (User) request.getSession().getAttribute("user");
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();
		Locale.setDefault(new Locale(user.getLocale()));
		ResourceBundle addCountBundle = ResourceBundle.getBundle("resources");

		if (editCreditCard == null && editCount != null) {

			UnlockRequest unlockedRequest = null;
			Count countObj = null;

			if (ADD_COUNT.equals(editCount)) {
				countObj = new Count();
				countObj.setIsBlockedId(0);
				countObj.setCount(FISH_COUNT);
				countObj.setUserId(Integer.valueOf(userId));
				countObj.setId(-1);
				request.setAttribute("nameButtonEdit", addCountBundle.getString(NAME_BUTTON_ADD));
			} else {
				countObj = manager.findCount(Integer.valueOf(editCount), user.getLocale());
				unlockedRequest = manager.findUnlockRequest(countObj.getId(), user.getLocale());
				request.setAttribute("nameButtonEdit", addCountBundle.getString(NAME_BUTTON_EDIT));

			}

			request.setAttribute("boolMap", Bool.getMap().entrySet());
			request.setAttribute("count", countObj);
			if (unlockedRequest == null) {
				request.setAttribute("unlockedRequest", Bool.NO.getId());
			} else {
				request.setAttribute("unlockedRequest", unlockedRequest.getActual());
			}
			request.setAttribute("pageAction", ACTION);

			forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;
		} else if (editCreditCard != null && editCount == null) {

			List<CreditCard> cardList = manager.getUserCreditCards(Integer.valueOf(editCreditCard), user.getLocale());

			request.setAttribute("cardList", cardList);
			request.setAttribute("countId", editCreditCard);
			request.setAttribute("pageAction", ACTION_CREDIT_CARD);

			forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;

		}

		LOG.debug("Command finished");
		return forward;
	}
	private String endEditUserCounts(HttpServletRequest request,
									 HttpServletResponse response) throws IOException, ServletException, AppException {


		String editCount = request.getParameter("editCount");
		String count = request.getParameter("count");
		User user = (User) request.getSession().getAttribute("user");

		String isBlocked = request.getParameter("is_blocked");
		boolean isCountBlocked = Bool.getBoolName(isBlocked);
		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();

		if (editCount != null && Integer.valueOf(editCount) >=0) {

			String unlockedRequest = request.getParameter("unlocked_request");
			Count countObj = manager.findCount(Integer.valueOf(editCount), user.getLocale());
			countObj.setCount(count);
			countObj.setIsBlockedId(Bool.valueOf(isBlocked).getId());

			UnlockRequest unlockRequest = manager.findUnlockRequest(countObj.getId(), user.getLocale());
			if (unlockRequest != null) {
				if (!isCountBlocked) {
					unlockRequest.setActual(Bool.NO.getId());

				} else {
					unlockRequest.setActual(Bool.valueOf(unlockedRequest).getId());
				}
			}

			if (manager.updateCount(countObj, unlockRequest, user.getLocale())) {

				request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, user.getLocale()));
				request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_USER_COUNT_SUCCESSFULLY_CHANGED, user.getLocale()));

				forward = Path.PAGE_INFORMATION_PAGE;
			} else {
				throw new AppException("Can't update count");
			}
		} else if (editCount != null){

			String userId = request.getParameter("userId");

			Count countObj = new Count();
			countObj.setCount(count);
			countObj.setIsBlockedId(Bool.valueOf(isBlocked).getId());
			countObj.setUserId(Integer.valueOf(userId));

			if (manager.createCount(countObj, user.getLocale())) {

				request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, user.getLocale()));
				request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_USER_COUNT_SUCCESSFULLY_CREATED, user.getLocale()));

				forward = Path.PAGE_INFORMATION_PAGE;
			} else {
				throw new AppException("Can't create count");
			}

		} else {
			throw new AppException("Unexpected edit command");
		}


		return forward;
	}

}