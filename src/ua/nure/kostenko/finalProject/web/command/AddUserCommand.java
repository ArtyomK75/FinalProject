package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.Role;
import ua.nure.kostenko.finalProject.db.UserStatus;
import ua.nure.kostenko.finalProject.exception.AppException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Lists menu items.
 * 
 * @author A.Kostenko
 * 
 */
public class AddUserCommand extends Command {

	private static final long serialVersionUID = 7732286983499478505L;

	private static final Logger LOG = Logger.getLogger(AddUserCommand.class);

	private static final String ACTION = "newUser";
	private static final String END_ADD = "endAdd";

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException, AppException {
		
		LOG.debug("Command starts");

		String stage = request.getParameter("stage");
		String forward = "";
		if (stage != null & END_ADD.equals(stage)) {
			forward = endAddUser(request, response);
		} else {
			forward = beginAddUser(request, response);
		}
		LOG.debug("Command finished");
		return forward;


	}

	private String beginAddUser(HttpServletRequest request, HttpServletResponse response) {

		Map<Integer, String> roleMap = Role.getMap();
		Map<Integer, String> statusMap = UserStatus.getMap();

		request.setAttribute("roleMap", roleMap.entrySet());
		request.setAttribute("statusMap", statusMap.entrySet());
		request.setAttribute("pageAction", ACTION);

		LOG.trace("Set the request attribute: statusMap --> " + statusMap);
		LOG.trace("Set the request attribute: roleMap --> " + roleMap);

		LOG.debug("Command finished");
		return Path.PAGE_EDIT_USER_ADMIN_PAGE;

	}

	private String endAddUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, AppException {

		return RegistrationCommand.registerNewUser(request, response, true);

	}

}