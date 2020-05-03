package ua.nure.kostenko.finalProject.db;

/**
 * A SQL queries for DBManager class.
 *
 * @author A.Kostenko
 *
 */

public class SQLQueries {

    private SQLQueries() {
        // no op
    }

    public static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM ref_users WHERE login=?";

    public static final String SQL_FIND_USER_BY_ID = "SELECT * FROM ref_users WHERE id=?";

    public static final String SQL_FIND_USER_BY_FIRST_NAME_AND_LAST_NAME = "SELECT * FROM ref_users WHERE first_name=? AND last_name=?";

    public static final String SQL_FIND_COUNTS_BY_USER = "SELECT " +
            "  c.id AS count_id," +
            "  c.user_id," +
            "  c.count," +
            "  c.is_blocked," +
            "  IFNULL(u.actual, 0) AS have_request" +
            " FROM ref_counts AS c " +
            "  LEFT JOIN reg_unlock_requests AS u" +
            "  ON c.id = u.count_id" +
            "  AND u.actual = 1" +
            " WHERE c.user_id = ?";

    public static final String SQL_FIND_COUNTS_BY_CLIENT = "SELECT * FROM ref_counts WHERE user_id = ? AND is_blocked = ?";


    public static final String SQL_FIND_CREDIT_CARDS_BY_COUNT = "SELECT * FROM ref_credit_cards WHERE count_id = ?";

    public static final String SQL_FIND_CREDIT_CARD_BY_ID = "SELECT * FROM ref_credit_cards WHERE id = ?";

    public static final String SQL_FIND_PAYMENT_BY_ID = "SELECT * FROM doc_payments WHERE id = ?";

    public static final String SQL_FIND_COUNT_BY_ID = "SELECT * FROM ref_counts WHERE id = ?";

    public static final String SQL_FIND_UNLOCKED_REQUEST_BY_ID = "SELECT * FROM reg_unlock_requests WHERE count_id = ?";

    public static final String SQL_FIND_COUNT_PAYMENT_BEAN_BY_USER_ID = "SELECT " +
            "  p.id," +
            "  p.user_id," +
            "  c.count," +
            "  p.count_receiver," +
            "  p.sum," +
            "  p.status_id," +
            "  p.date" +
            " FROM doc_payments AS p," +
            "  ref_counts AS c" +
            " WHERE" +
            "  p.count_id = c.id" +
            "  AND p.user_id =?";


    public static final String SQL_FIND_ALL_CREDIT_CARDS_BY_USER = "SELECT " +
            "  c.id," +
            "  cc.number" +
            " FROM ref_counts AS c" +
            "  INNER JOIN ref_credit_cards AS cc" +
            "  ON c.id = cc.count_id" +
            " WHERE" +
            "  c.user_id = ?";

    public static final String SQL_FIND_PAYMENTS_BY_STATUS_AND_USER = "SELECT * FROM doc_payments WHERE status_id=? AND user_id=?";

    public static final String SQL_FIND_PAYMENT_BY_USER_ID = "SELECT * FROM doc_payments WHERE user_id=?";

    public static final String SQL_CREATE_USER = "INSERT INTO ref_users VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_CREATE_CREDIT_CARD = "INSERT INTO ref_credit_cards VALUES(DEFAULT, ?, ?, ?, ?)";

    public static final String SQL_CREATE_COUNT = "INSERT INTO ref_counts VALUES(DEFAULT, ?, ?, ?)";

    public static final String SQL_CREATE_PAYMENT = "INSERT INTO doc_payments VALUES(DEFAULT, DEFAULT, ?, ?, ?, ?, ?)";

    public static final String SQL_FIND_NEW_AND_BLOCKED_USER = "SELECT * FROM ref_users WHERE status_id=? OR status_id=?";

    public static final String SQL_STATUS_USER = "UPDATE ref_users SET status_id = ? WHERE id=?";

    public static final String SQL_UPDATE_USER = "UPDATE ref_users SET password=?, first_name=?, last_name=?, status_id = ?, locale = ?"
            + "	WHERE id=?";
    public static final String SQL_UPDATE_COUNT = "UPDATE ref_counts SET count=?, is_blocked=? WHERE id=?";

    public static final String SQL_UPDATE_PAYMENT = "UPDATE doc_payments SET date = NOW(), count_id=?, count_receiver=?, sum=?, status_id=?" +
            "  WHERE id=?";

    public static final String SQL_UPDATE_CREDIT_CARD = "UPDATE ref_credit_cards SET number=?, expiration=?, cvv=?  WHERE id=?";

    public static final String SQL_UPDATE_UNLOCK_REQUEST = "UPDATE reg_unlock_requests SET actual=?, date=NOW() WHERE count_id=?";

    public static final String SQL_CREATE_UNLOCK_REQUEST = "INSERT INTO reg_unlock_requests VALUES(?, ?, DEFAULT)";
    

}
