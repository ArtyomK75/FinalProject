package ua.nure.kostenko.finalProject.web.command;

import org.apache.log4j.Logger;

import ua.nure.kostenko.finalProject.Path;
import ua.nure.kostenko.finalProject.db.DBManager;
import ua.nure.kostenko.finalProject.db.PaymentStatus;
import ua.nure.kostenko.finalProject.db.bean.CountPaymentBean;
import ua.nure.kostenko.finalProject.db.entity.Payment;
import ua.nure.kostenko.finalProject.db.entity.User;
import ua.nure.kostenko.finalProject.exception.AppException;
import ua.nure.kostenko.finalProject.exception.Messages;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Lists menu items.
 *
 * @author A.Kostenko
 */
public class EditPaymentsCommand extends Command {

    private static final long serialVersionUID = 7239843229851178505L;

    private static final Logger LOG = Logger.getLogger(EditPaymentsCommand.class);
    private static final String SORT_BY_DATE_ASC = "byDateAsc";
    private static final String SORT_BY_DATE_DESC = "byDateDesc";
    private static final String SORT_BY_NUMBER = "byNumber";
    private static final String ADD_PAYMENT = "addPayment";
    private static final String ACTION_BEGIN_EDIT = "beginEditPayment";
    private static final String END_EDIT = "endEdit";
    private static final String NAME_BUTTON_ADD = "button.add_payment";
    private static final String NAME_BUTTON_EDIT = "button.edit_payment";




    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException, AppException {

        LOG.debug("Command starts");

        String stage = request.getParameter("stage");
        String sortBy = request.getParameter("sortBy");
        String forward = Path.PAGE_ERROR_PAGE;

        if (END_EDIT.equals(stage)) {

            forward = endEditPayment(request);

        } else if (sortBy == null) {

            forward = beginEditPayment(request);

        } else {

            forward = sortPayments(request, sortBy);

        }

        LOG.debug("Command finished");
        return forward;
    }

    private String endEditPayment(HttpServletRequest request) throws IOException, ServletException, AppException {


        Payment payment = new Payment();
        User user = (User) request.getSession().getAttribute("user");
        int paymentId = Integer.valueOf(request.getParameter("paymentId"));
        int countId = Integer.valueOf(request.getParameter("countId"));
        int paymentStatus = Integer.valueOf(request.getParameter("paymentStatus"));
        String countReceiver = request.getParameter("countReceiver");
        float sum = Float.valueOf(request.getParameter("sum"));

        payment.setStatusId(paymentStatus);
        payment.setUserId(user.getId());
        payment.setSum(sum);
        payment.setCountReceiver(countReceiver);
        payment.setCountId(countId);

        Locale.setDefault(new Locale(user.getLocale()));
        ResourceBundle addCountBundle = ResourceBundle.getBundle("resources");

        String forward = Path.PAGE_ERROR_PAGE;
        DBManager manager = DBManager.getInstance();

        if (paymentId == -1) {
            //it's a new payment
            if (manager.createPayment(payment, user.getLocale())) {
                request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, user.getLocale()));
                request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_PAYMENT_SUCCESSFULLY_CREATED, user.getLocale()));

                forward = Path.PAGE_INFORMATION_PAGE;
            }

        } else {

            payment.setId(paymentId);
            if (manager.updatePayment(payment, user.getLocale())) {
                request.setAttribute("infoHeader", Messages.getI18nMessage(Messages.MSG_CONGRATULATION, user.getLocale()));
                request.setAttribute("message", Messages.getI18nMessage(Messages.MSG_PAYMENT_SUCCESSFULLY_CHANGED, user.getLocale()));

                forward = Path.PAGE_INFORMATION_PAGE;
            }
        }

        return forward;
    }
    private String beginEditPayment(HttpServletRequest request) throws IOException, ServletException, AppException {


        String editPayment = request.getParameter("editPayment");
        User user = (User) request.getSession().getAttribute("user");

        DBManager manager = DBManager.getInstance();

        Locale.setDefault(new Locale(user.getLocale()));
        ResourceBundle addCountBundle = ResourceBundle.getBundle("resources");

        Payment payment = null;

        if (ADD_PAYMENT.equals(editPayment)) {

            payment = new Payment();
            payment.setId(-1);
            payment.setUserId(user.getId());
            payment.setStatusId(PaymentStatus.PREPARED.getId());

            request.setAttribute("nameButtonEdit", addCountBundle.getString(NAME_BUTTON_ADD));

        } else {

            payment = manager.findPayment(Integer.valueOf(editPayment), user.getLocale());

            request.setAttribute("nameButtonEdit", addCountBundle.getString(NAME_BUTTON_EDIT));

        }

        String forward = Path.PAGE_EDIT_CLIENT_DATA_PAGE;
        request.setAttribute("payment", payment);
        request.setAttribute("pageAction", ACTION_BEGIN_EDIT);
        request.setAttribute("clientCounts", manager.getClientCounts(user.getId(), user.getLocale()));
        request.setAttribute("statusList", PaymentStatus.getMap());

        return forward;

    }


    private String sortPayments(HttpServletRequest request, String sortBy) throws IOException, ServletException, AppException {

        User user = (User) request.getSession().getAttribute("user");
        List<CountPaymentBean> countPaymentBeanList = DBManager.getInstance().getCountPaymentBeanList(user.getId(), user.getLocale());;

        String forward = Path.PAGE_ERROR_PAGE;

        if (countPaymentBeanList != null) {

            forward = Path.PAGE_MAIN_CLIENT_PAGE;

            request.getSession().setAttribute("orderPayments", sortBy);

            switch (sortBy) {
                case SORT_BY_DATE_ASC:
                    Collections.sort(countPaymentBeanList, (o1, o2) -> o1.getDate().compareTo(o2.getDate()));
                    break;
                case SORT_BY_DATE_DESC:
                    Collections.sort(countPaymentBeanList, (o1, o2) -> o2.getDate().compareTo(o1.getDate()));
                    break;
                default:
                    Collections.sort(countPaymentBeanList, (o1, o2) -> o1.getId() - o2.getId());
            }
            //
            request.setAttribute("countPaymentBeanList", countPaymentBeanList);
            request.setAttribute("statusList", PaymentStatus.getMap());

            LOG.trace("Set the request attribute: paymentList --> " + countPaymentBeanList);

        }

        return forward;

    }
}