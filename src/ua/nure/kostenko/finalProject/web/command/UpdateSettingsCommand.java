package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.DBManager;
import ua.nure.kostenko.finalProject.db.entity.User;
import ua.nure.kostenko.finalProject.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * View settings command.
 * 
 * @author A.Kostenko
 * 
 */
public class UpdateSettingsCommand extends Command {
	
	private static final long serialVersionUID = -3098656593627692473L;
	
	private static final Logger LOG = Logger.getLogger(UpdateSettingsCommand.class);
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {
		LOG.debug("Command starts");

		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {

			return Path.PAGE_ERROR_PAGE;

		}
		String newLocale = request.getParameter("locale");
		user.setLocale(newLocale);
		DBManager.getInstance().updateUser(user, newLocale);

		request.getSession().setAttribute("currentLocale", newLocale);

		LOG.debug("Command finished");
		return Path.PAGE_SETTINGS;
	}

}