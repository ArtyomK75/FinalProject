package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.Bool;
import ua.nure.kostenko.finalProject.db.DBManager;
import ua.nure.kostenko.finalProject.db.Role;
import ua.nure.kostenko.finalProject.db.UserStatus;
import ua.nure.kostenko.finalProject.db.bean.UserCountAdminChangeBean;
import ua.nure.kostenko.finalProject.db.entity.User;
import ua.nure.kostenko.finalProject.exception.AppException;
import ua.nure.kostenko.finalProject.exception.Messages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Lists menu items.
 *
 * @author A.Kostenko
 */
public class FindUserCommand extends Command {

    private static final long serialVersionUID = 7732286214029098505L;

    private static final Logger LOG = Logger.getLogger(FindUserCommand.class);

    private static final String ACTION_EDIT_USER = "editUser";
	private static final String ACTION_EDIT_USER_COUNTS_BEGIN = "editUserCountsBegin";

    private static final String TYPE_SEARCH_USER = "Find user";
    private static final String TYPE_SEARCH_COUNT = "Find user counts";

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, AppException {

        LOG.debug("Command starts");


        String login = request.getParameter("login");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String typeSearch = request.getParameter("typeSearch");
        User sysUser = (User) request.getSession().getAttribute("user");

        String forward = "";

        DBManager manager = DBManager.getInstance();

        User user = manager.findUserByLogin(login, sysUser.getLocale());
        if (user == null) {
            List<User> userList = manager.findUserByNames(firstName, lastName, sysUser.getLocale());

            if (userList.size() == 1) {
                user = userList.get(0);
            } else if (userList.size() == 0) {

                request.setAttribute("infoHeader", Messages.MSG_HEADER_INFORMATION);
                request.setAttribute("message", Messages.ERR_CANNOT_OBTAIN_A_USER_BY_ITS_FIRST_NAME_AND_LAST_NAME);

                return Path.PAGE_INFORMATION_PAGE;


            } else {
                request.setAttribute("infoHeader", Messages.MSG_HEADER_INFORMATION);
                request.setAttribute("message", Messages.ERR_FIND_MORE_THEN_ONE_USER);

                return Path.PAGE_INFORMATION_PAGE;
            }
        }


        if (TYPE_SEARCH_USER.equals(typeSearch)) {
            forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;

            request.setAttribute("roleMap", Role.getMap().entrySet());
            request.setAttribute("statusMap", UserStatus.getMap().entrySet());
            request.setAttribute("pageAction", ACTION_EDIT_USER);
            request.setAttribute("editedUser", user);


        } else if (TYPE_SEARCH_COUNT.equals(typeSearch)) {
            forward = Path.PAGE_EDIT_USER_ADMIN_PAGE;
			List<UserCountAdminChangeBean> countList = manager.getUserCountList(user.getId(), sysUser.getLocale());

            request.setAttribute("boolMap", Bool.getMap().entrySet());
            request.setAttribute("countList", countList);
            request.setAttribute("pageAction", ACTION_EDIT_USER_COUNTS_BEGIN);
            request.setAttribute("editedUser", user);

        } else {
            throw new AppException("Unexpected type of search");
        }


        LOG.debug("Command finished");
        return forward;
    }

}