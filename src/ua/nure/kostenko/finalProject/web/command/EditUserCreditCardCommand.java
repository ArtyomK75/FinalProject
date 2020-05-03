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
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Lists menu items.
 * 
 * @author A.Kostenko
 * 
 */
public class EditUserCreditCardCommand extends Command {

	private static final long serialVersionUID = 7675464314029473405L;

	private static final Logger LOG = Logger.getLogger(EditUserCreditCardCommand.class);

	private static final String ACTION = "editUserCreditCard";
	private static final String END_EDIT = "endEdit";
	private static final String ADD_CARD = "addCard";
	private static final String NAME_BUTTON_ADD = "button.add_credit_card";
	private static final String NAME_BUTTON_EDIT = "button.edit_credit_card";


	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		String stage = request.getParameter("stage");
		String forward = "";
		if (stage != null & END_EDIT.equals(stage)) {
			forward = endEditUserCreditCards(request, response);
		} else {
			forward = beginEditUserCreditCards(request, response);
		}
		LOG.debug("Command finished");
		return forward;
	}

	private String beginEditUserCreditCards(HttpServletRequest request,
											HttpServletResponse response) throws IOException, ServletException, AppException {


		String countId = request.getParameter("countId");
		String editCreditCard = request.getParameter("editCreditCard");
		User user = (User) request.getSession().getAttribute("user");

		String forward = Path.PAGE_ERROR_PAGE;

		Locale.setDefault(new Locale(user.getLocale()));
		ResourceBundle addCountBundle = ResourceBundle.getBundle("resources");

		DBManager manager = DBManager.getInstance();
		CreditCard creditCard = null;
		if (ADD_CARD.equals(editCreditCard)) {

			creditCard = new CreditCard();
			creditCard.setId(0);
			request.setAttribute("card", creditCard);
			request.setAttribute("countId", countId);
			request.setAttribute("pageAction", ACTION);
			request.setAttribute("nameButtonEdit", addCountBundle.getString(NAME_BUTTON_ADD));
			forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;


		} else if (editCreditCard != null) {

			creditCard = manager.findCreditCard(Integer.valueOf(editCreditCard), user.getLocale());

			request.setAttribute("card", creditCard);
			request.setAttribute("countId", countId);
			request.setAttribute("pageAction", ACTION);
			request.setAttribute("nameButtonEdit", addCountBundle.getString(NAME_BUTTON_EDIT));
			forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;

		}

		LOG.debug("Command finished");
		return forward;
	}
	private String endEditUserCreditCards(HttpServletRequest request,
									 HttpServletResponse response) throws IOException, ServletException, AppException {


		String editCreditCard = request.getParameter("editCreditCard");
		String count = request.getParameter("count");
		String expiration = request.getParameter("expiration");
		String cvv = request.getParameter("cvv");
		String countId = request.getParameter("countId");
		User user = (User) request.getSession().getAttribute("user");

		String forward = Path.PAGE_ERROR_PAGE;

		DBManager manager = DBManager.getInstance();

		if (editCreditCard != null && Integer.valueOf(editCreditCard) > 0) {

			CreditCard creditCard = manager.findCreditCard(Integer.valueOf(editCreditCard), user.getLocale());

			if (creditCard != null) {

				try{
					creditCard.setNumber(Long.valueOf(count));
					creditCard.setCvv(Integer.valueOf(cvv));
					creditCard.setExpiration(expiration);
				} catch (Exception ex) {
					throw new AppException("You enter incorrect data");
				}

			}


			if(manager.updateCreditCard(creditCard, user.getLocale())) {

				request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, user.getLocale()));
				request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_USER_CREDIT_CARD_SUCCESSFULLY_CHANGED, user.getLocale()));

				forward = Path.PAGE_INFORMATION_PAGE;


			}


		} else if (editCreditCard != null) {

			CreditCard creditCard = new CreditCard();

			try{
				creditCard.setNumber(Long.valueOf(count));
				creditCard.setCvv(Integer.valueOf(cvv));
				creditCard.setExpiration(expiration);
				creditCard.setCountId(Integer.valueOf(countId));
			} catch (Exception ex) {
				throw new AppException("You enter incorrect data");
			}

			if(manager.createCreditCard(creditCard, user.getLocale())) {

				request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, user.getLocale()));
				request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_USER_CREDIT_CARD_SUCCESSFULLY_CREATED, user.getLocale()));

				forward = Path.PAGE_INFORMATION_PAGE;

			}



		} else {
			throw new AppException("Unexpected edit command");
		}


		return forward;
	}

}