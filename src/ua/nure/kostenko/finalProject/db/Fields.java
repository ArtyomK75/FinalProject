package ua.nure.kostenko.finalProject.db;

/**
 * Holder for fields names of DB tables and beans.
 * 
 * @author A.Kostenko
 * 
 */
public final class Fields {
	
	// entities
	public static final String ENTITY_ID = "id";
	public static final String USER_ID = "user_id";
	public static final String COUNT_ID = "count_id";
	public static final String COUNT_IS_BLOCKED = "is_blocked";
	public static final String COUNT_NO = "count";

	public static final String REQ_ACTUAL = "have_request";

	public static final String COUNT_COUNT = "count";

	public static final String UNLOCKED_REQUEST_ACTUAL = "actual";
	public static final String UNLOCKED_REQUEST_DATE = "date";

	public static final String CREDIT_CARD_NUMBER = "number";
	public static final String CREDIT_CARD_EXPIRATION = "expiration";
	public static final String CREDIT_CARD_CVV = "cvv";


	public static final String USER_LOGIN = "login";
	public static final String USER_PASSWORD = "password";
	public static final String USER_FIRST_NAME = "first_name";
	public static final String USER_LAST_NAME = "last_name";
	public static final String USER_ROLE_ID = "role_id";
	public static final String USER_STATUS_ID = "status_id";
	public static final String USER_LOCALE = "locale";

	
	//Payments
	public static final String PAYMENT_ID = "id";
	public static final String PAYMENT_COUNT_ID = "id";
	public static final String PAYMENT_SUM = "sum";
	public static final String PAYMENT_RECEIVER = "count_receiver";
	public static final String PAYMENT_STATUS_ID = "status_id";
	public static final String PAYMENT_DATE = "date";
	public static final String USER_PAYMENT_ID = "user_id";

    public static final String PAYMENT_COUNT = "count";
}
