package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.DBManager;
import ua.nure.kostenko.finalProject.db.Role;
import ua.nure.kostenko.finalProject.db.UserStatus;
import ua.nure.kostenko.finalProject.db.entity.User;
import ua.nure.kostenko.finalProject.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Lists menu items.
 * 
 * @author A.Kostenko
 * 
 */
public class BeginEditUserCommand extends Command {

	private static final long serialVersionUID = 7842862938529478505L;

	private static final Logger LOG = Logger.getLogger(BeginEditUserCommand.class);

	private static final String ACTION = "beginEditUser";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {

		LOG.debug("Command starts");
		request.setAttribute("pageAction", ACTION);

		LOG.debug("Command finished");
		return Path.PAGE_EDIT_USER_ADMIN_PAGE;
	}

}