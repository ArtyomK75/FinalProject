package ua.nure.kostenko.finalProject;

/**
 * Path holder (jsp pages, controller commands).
 *
 * @author A.Kostenko
 *
 */
public final class Path {

    // pages
    public static final String PAGE_LOGIN = "/login.jsp";
    public static final String PAGE_ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
    public static final String PAGE_INFORMATION_PAGE = "/WEB-INF/jsp/information_page.jsp";
    public static final String PAGE_EDIT_USER_ADMIN_PAGE = "/WEB-INF/jsp/admin/edit_user.jsp";
    public static final String PAGE_EDIT_CLIENT_DATA_PAGE = "/WEB-INF/jsp/client/edit_client_data.jsp";

    public static final String PAGE_MAIN_CLIENT_PAGE = "/WEB-INF/jsp/client/main_client_page.jsp";
    public static final String PAGE_MAIN_ADMIN_PAGE = "/WEB-INF/jsp/admin/main_admin_page.jsp";
    public static final String PAGE_SETTINGS = "/WEB-INF/jsp/settings.jsp";



    // commands
    public static final String COMMAND_MAIN_ADMIN_PAGE = "/controller?command=mainAdminPage";

    public static final String COMMAND_MAIN_CLIENT_PAGE = "/controller?command=mainPage";
    public static final String COMMAND_EDIT_USER_FIND_ADMIN_PAGE = "/controller?command=mainPage";

}