package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.DBManager;
import ua.nure.kostenko.finalProject.db.Role;
import ua.nure.kostenko.finalProject.db.UserStatus;
import ua.nure.kostenko.finalProject.db.entity.User;
import ua.nure.kostenko.finalProject.exception.AppException;
import ua.nure.kostenko.finalProject.exception.Messages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Lists menu items.
 * 
 * @author A.Kostenko
 * 
 */
public class EditUserCommand extends Command {

	private static final long serialVersionUID = 7675464314029473405L;

	private static final Logger LOG = Logger.getLogger(EditUserCommand.class);

	private static final String ACTION = "editUser";
	private static final String END_EDIT = "endEdit";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {

		LOG.debug("Command starts");

		String stage = request.getParameter("stage");
		String forward = "";
		if (stage != null & END_EDIT.equals(stage)) {
			forward = endEditUser(request, response);
		} else {
			forward = beginEditUser(request, response);
		}
		LOG.debug("Command finished");
		return forward;
	}

	private String beginEditUser(HttpServletRequest request,
						   HttpServletResponse response) throws IOException, ServletException, AppException {



		request.setAttribute("roleMap", Role.getMap().entrySet());
		request.setAttribute("statusMap", UserStatus.getMap().entrySet());

		request.setAttribute("pageAction", ACTION);

		User sysUser = (User) request.getSession().getAttribute("user");

		int editUserId = (int) request.getAttribute("userId");


		User user = DBManager.getInstance().findUser(editUserId, sysUser.getLocale());
		LOG.trace("Found in DB: user --> " + user);

		String forward = Path.PAGE_ERROR_PAGE;

		if (user != null) {

			forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;

		}


		LOG.trace("Set the request attribute: roleMap --> " + Role.getMap());
		LOG.trace("Set the request attribute: statusMap --> " + UserStatus.getMap());

		LOG.debug("Command finished");
		return forward;
	}
	private String endEditUser(HttpServletRequest request,
								 HttpServletResponse response) throws IOException, ServletException, AppException {


		int id = Integer.valueOf(request.getParameter("user_id"));
		LOG.trace("Request parameter: user_id --> " + id);
		String login = request.getParameter("login");
		LOG.trace("Request parameter: login --> " + login);
		String password = request.getParameter("password");
		String firstName = request.getParameter("first_name");
		LOG.trace("Request parameter: firstName --> " + firstName);
		String lastName = request.getParameter("last_name");
		LOG.trace("Request parameter: lastName --> " + lastName);
		String status = request.getParameter("status");
		LOG.trace("Request parameter: status --> " + status);
		String role = request.getParameter("role");
		LOG.trace("Request parameter: role --> " + role);



		if (!RegistrationCommand.testField(login) || !RegistrationCommand.testField(firstName) || !RegistrationCommand.testField(lastName)) {
			throw new AppException("Filling fields cannot be empty.");
		}

		User sysUser = (User) request.getSession().getAttribute("user");
		DBManager manager = DBManager.getInstance();
		User user = manager.findUser(id, sysUser.getLocale());
		LOG.trace("Found in DB: user --> " + user);

		if (user == null) {
			throw new AppException("Such user not found in db");
		}

		//edit user

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setLogin(login);
		user.setStatusId(UserStatus.valueOf(status).getId());
		user.setRoleId(Role.valueOf(role).getId());
		//if password is blank, then no need to change it
		if (!password.isEmpty()) {

			user.setPassword(password);

		}

		manager.updateUser(user, sysUser.getLocale());

		request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, sysUser.getLocale()));
		request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_USER_SUCCESSFULLY_CHANGED, sysUser.getLocale()));

		return Path.PAGE_INFORMATION_PAGE;
	}

}