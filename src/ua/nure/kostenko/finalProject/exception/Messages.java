package ua.nure.kostenko.finalProject.exception;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Holder for messages of exceptions.
 * 
 * @author A.Kostenko
 *
 */
public class Messages {


    private Messages() {
		// no op
	}

	public static final String ERR_CANNOT_OBTAIN_USER_PAYMENT = "msg.err_cannot_obtain_user_payment";

	public static final String ERR_CANNOT_OBTAIN_USER_PAYMENT_COUNT = "msg.err_cannot_obtain_user_payment_count";

	public static final String ERR_CANNOT_OBTAIN_CONNECTION = "msg.err_cannot_obtain_connection";

	public static final String ERR_CANNOT_OBTAIN_USER_COUNTS = "msg.err_cannot_obtain_user_counts";

	public static final String ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_COUNT = "msg.err_cannot_obtain_credit_cards_by_count";

	public static final String ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID = "msg.err_cannot_obtain_credit_card_by_id";

	public static final String ERR_CANNOT_OBTAIN_PAYMENT_BY_ID = "msg.err_cannot_obtain_payment_by_id";

	public static final String ERR_CANNOT_OBTAIN_USER_COUNT_BY_ID = "msg.err_cannot_obtain_user_count_by_id";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_ID = "msg.err_cannot_obtain_user_by_id";

	public static final String ERR_CANNOT_OBTAIN_USER_BY_LOGIN = "msg.err_cannot_obtain_user_by_login";

	public static final String ERR_CANNOT_OBTAIN_A_USER_BY_ITS_FIRST_NAME_AND_LAST_NAME = "msg.err_cannot_obtain_a_user_by_its_first_name_and_last_name";

	public static final String ERR_FIND_MORE_THEN_ONE_USER = "msg.err_find_more_then_one_user";

	public static final String ERR_CANNOT_CREATE_USER = "msg.err_cannot_create_user";

	public static final String ERR_CANNOT_UPDATE_USER = "msg.err_cannot_update_user";

	public static final String ERR_CANNOT_UPDATE_USER_CREDIT_CARD = "msg.err_cannot_update_user_credit_card";

	public static final String ERR_CANNOT_UPDATE_PAYMENT = "msg.err_cannot_update_payment";

	public static final String ERR_CANNOT_CREATE_USER_CREDIT_CARD = "msg.err_cannot_create_user_credit_card";

	public static final String ERR_CANNOT_CREATE_UNBLOCKED_REQUEST = "msg.err_cannot_create_unblocked_request";

	public static final String ERR_CANNOT_CREATE_PAYMENT = "msg.err_cannot_create_payment";

	public static final String ERR_CANNOT_CREATE_USER_COUNT = "msg.err_cannot_create_user_count";

	public static final String ERR_CANNOT_CLOSE_CONNECTION = "msg.err_cannot_close_connection";

	public static final String ERR_CANNOT_CLOSE_RESULT_SET = "msg.err_cannot_close_result_set";

	public static final String ERR_CANNOT_CLOSE_STATEMENT = "msg.err_cannot_close_statement";

	public static final String ERR_CANNOT_OBTAIN_DATA_SOURCE = "msg.err_cannot_obtain_data_source";

	public static final String ERR_CANNOT_UPDATE_COUNT = "msg.err_cannot_update_user_count";

	public static final String MSG_CONGRATULATION = "msg.msg_congratulation";

	public static final String MSG_ATTENTION = "msg.msg_attention";

	public static final String MSG_HEADER_INFORMATION = "msg.msg_header_information";

	public static final String MSG_USER_SUCCESSFULLY_CREATED_AND_WAITING_MODERATION = "msg.msg_user_successfully_created_and_waiting_moderation";

	public static final String MSG_USER_WAITING_MODERATION = "msg.msg_user_waiting_moderation";

	public static final String MSG_USER_BLOCKED = "msg.msg_user_blocked";

	public static final String MSG_STATUSES_USERS_SUCCESSFULLY_CHANGED = "msg.msg_statuses_users_successfully_changed";

	public static final String MSG_USER_SUCCESSFULLY_CHANGED = "msg.msg_user_successfully_changed";

	public static final String MSG_USER_CREDIT_CARD_SUCCESSFULLY_CHANGED = "msg.msg_user_credit_card_successfully_changed";

	public static final String MSG_USER_CREDIT_CARD_SUCCESSFULLY_CREATED = "msg.msg_user_credit_card_successfully_created";

	public static final String MSG_USER_COUNT_SUCCESSFULLY_CHANGED = "msg.msg_user_count_successfully_changed";

	public static final String MSG_USER_COUNT_SUCCESSFULLY_CREATED = "msg.msg_user_count_successfully_created";

	public static final String MSG_PAYMENT_SUCCESSFULLY_CHANGED = "msg.msg_payment_successfully_changed";

	public static final String MSG_PAYMENT_SUCCESSFULLY_CREATED = "msg.msg_payment_successfully_created";

	public static final String MSG_USER_SUCCESSFULLY_ADDED = "msg.msg_user_successfully_added";



	public static String getI18nMessage(String param, String strLocale) {

		Locale.setDefault(new Locale(strLocale));
		ResourceBundle resourceBundle = ResourceBundle.getBundle("resources");

		return resourceBundle.getString(param);

	}
}