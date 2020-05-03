package ua.nure.kostenko.finalProject.db;

import org.apache.log4j.Logger;
import ua.nure.kostenko.finalProject.db.bean.CountPaymentBean;
import ua.nure.kostenko.finalProject.db.bean.UserAdminChangeBean;
import ua.nure.kostenko.finalProject.db.bean.UserCountAdminChangeBean;
import ua.nure.kostenko.finalProject.db.bean.UserCountClientChangeBean;
import ua.nure.kostenko.finalProject.db.entity.*;
import ua.nure.kostenko.finalProject.exception.DBException;
import ua.nure.kostenko.finalProject.exception.Messages;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DB manager.
 *
 * @author A.Kostenko
 */
public final class DBManager {

    private static final Logger LOG = Logger.getLogger(DBManager.class);

    // //////////////////////////////////////////////////////////
    // singleton
    // //////////////////////////////////////////////////////////

    private static DBManager instance;

    public static synchronized DBManager getInstance() throws DBException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private DBManager() throws DBException {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/CLBankDB");
            LOG.trace("Data source ==> " + ds);
        } catch (NamingException ex) {
            LOG.error(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, LOCALE_BY_DEFAULT), ex);
            ex.printStackTrace();
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, LOCALE_BY_DEFAULT), ex);
        }
    }

    private DataSource ds;

    // //////////////////////////////////////////////////////////
    // SQL queries
    // //////////////////////////////////////////////////////////


    private static final String LOCALE_BY_DEFAULT = "en";

    /**
     * Returns a DB connection from the Pool Connections. Before using this
     * method you must configure the Date Source and the Connections Pool in
     * your WEB_APP_ROOT/META-INF/context.xml file.
     *
     * @return DB connection.
     */
    public Connection getConnection() throws DBException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            LOG.error(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_CONNECTION, LOCALE_BY_DEFAULT), ex);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_CONNECTION, LOCALE_BY_DEFAULT), ex);
        }
        return con;
    }

    /**
     * Returns all blocked and new users.
     *
     * @return List of users.
     */
    public List<UserAdminChangeBean> getNewAndBlockedUsers(String userLocale) throws DBException {
        List<UserAdminChangeBean> beanList = new ArrayList<UserAdminChangeBean>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_NEW_AND_BLOCKED_USER);
            int k = 1;
            pstmt.setInt(k++, UserStatus.NEW.getId());
            pstmt.setInt(k, UserStatus.BLOCKED.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                beanList.add(extractUserAdminChangeBean(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_COUNTS, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return beanList;
    }


    /**
     * Returns list<CountPaymentBean>  by user id.
     *
     * @param userId
     * @return List of credit cards.
     */

    public List<CountPaymentBean> getCountPaymentBeanList(int userId, String userLocale) throws DBException {
        List<CountPaymentBean> countPaymentBeanList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_COUNT_PAYMENT_BEAN_BY_USER_ID);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                countPaymentBeanList.add(extractCountPaymentBean(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT_COUNT, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return countPaymentBeanList;
    }

    /**
     * Returns list<Payment> by user id.
     *
     * @param userId
     * @return List of credit cards.
     */

    public List<Payment> getPaymentList(int userId, String userLocale) throws DBException {
        List<Payment> paymentList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_PAYMENT_BY_USER_ID);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                paymentList.add(extractPayment(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_PAYMENT, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return paymentList;
    }

    /**
     * Returns credit cards by count id.
     *
     * @param id
     * @return List of credit cards.
     */

    public List<CreditCard> getUserCreditCards(int id, String userLocale) throws DBException {
        List<CreditCard> cardList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_CREDIT_CARDS_BY_COUNT);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                cardList.add(extractCreditCard(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARDS_BY_COUNT, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return cardList;
    }

    /**
     * Returns all counts of user.
     *
     * @param id - user id
     * @return List of counts.
     */

    public List<UserCountClientChangeBean> getUserCountClientList(int id, String userLocale) throws DBException {
        List<UserCountClientChangeBean> beanList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSet rsCC = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_COUNTS_BY_USER);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_ALL_CREDIT_CARDS_BY_USER);
            pstmt.setInt(1, id);
            rsCC = pstmt.executeQuery();

            Map<Long, Integer> creditCards = new HashMap<>();
            while (rsCC.next()) {

                creditCards.putIfAbsent(rsCC.getLong(Fields.CREDIT_CARD_NUMBER), rsCC.getInt(Fields.ENTITY_ID));

            }

            while (rs.next()) {

                beanList.add(extractUserCountClientChangeBean(rs, creditCards));

            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_COUNTS, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return beanList;
    }

    /**
     * Returns all counts of user.
     *
     * @param id user id
     * @return List of counts.
     */

    public List<UserCountAdminChangeBean> getUserCountList(int id, String userLocale) throws DBException {
        List<UserCountAdminChangeBean> beanList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_COUNTS_BY_USER);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                beanList.add(extractUserCountAdminChangeBean(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_COUNTS, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return beanList;
    }

    /**
     * Returns all unblocked counts of client.
     *
     * @param id
     * @return List of counts.
     */

    public List<Count> getClientCounts(int id, String userLocale) throws DBException {
        List<Count> countList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_COUNTS_BY_CLIENT);
            int k = 1;
            pstmt.setInt(k++, id);
            pstmt.setInt(k, Bool.NO.getId());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                countList.add(extractCount(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_COUNTS, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return countList;
    }


    /**
     * Returns a count with the given identifier.
     *
     * @param id Count identifier.
     * @return Count entity.
     * @throws DBException
     */
    public Count findCount(int id, String userLocale) throws DBException {
        Count count = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_COUNT_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = extractCount(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_COUNT_BY_ID, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return count;
    }

    /**
     * Returns a payment with the given identifier.
     *
     * @param id Payment identifier.
     * @return Payment entity.
     * @throws DBException
     */
    public Payment findPayment(int id, String userLocale) throws DBException {
        Payment payment = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_PAYMENT_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                payment = extractPayment(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_PAYMENT_BY_ID, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return payment;
    }


    /**
     * Returns a credit card with the given identifier.
     *
     * @param id CreditCard identifier.
     * @return CreditCard entity.
     * @throws DBException
     */
    public CreditCard findCreditCard(int id, String userLocale) throws DBException {
        CreditCard creditCard = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_CREDIT_CARD_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                creditCard = extractCreditCard(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_CREDIT_CARD_BY_ID, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return creditCard;
    }


    /**
     * Returns an unlock request with the given count identifier.
     *
     * @param id Count identifier.
     * @return UnlockRequest entity.
     * @throws DBException
     */
    public UnlockRequest findUnlockRequest(int id, String userLocale) throws DBException {
        UnlockRequest unlockRequest = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_UNLOCKED_REQUEST_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                unlockRequest = extractUnlockRequest(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_COUNT_BY_ID, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return unlockRequest;
    }


    /**
     * Returns a user with the given identifier.
     *
     * @param id User identifier.
     * @return User entity.
     * @throws DBException
     */
    public User findUser(int id, String userLocale) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_USER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_BY_ID, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return user;
    }

    /**
     * Returns a user with the given login.
     *
     * @param login User login.
     * @return User entity.
     * @throws DBException
     */
    public User findUserByLogin(String login, String userLocale) throws DBException {
        User user = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_USER_BY_LOGIN);
            pstmt.setString(1, login);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_USER_BY_LOGIN, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return user;
    }

    /**
     * Returns a user with the given login.
     *
     * @param firstName
     * @param lastName  User login.
     * @return User entity.
     * @throws DBException
     */

    public List<User> findUserByNames(String firstName, String lastName, String userLocale) throws DBException {

        List<User> userList = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_FIND_USER_BY_FIRST_NAME_AND_LAST_NAME);
            int k = 1;
            pstmt.setString(k++, firstName);
            pstmt.setString(k, lastName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userList.add(extractUser(rs));
            }
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_OBTAIN_A_USER_BY_ITS_FIRST_NAME_AND_LAST_NAME, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, rs, userLocale);
        }
        return userList;
    }


    /**
     * Create user.
     *
     * @param user user to create.
     * @throws DBException
     */
    public boolean createUser(User user, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_CREATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getLogin());
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setInt(k++, user.getRoleId());
            pstmt.setInt(k++, user.getStatusId());
            pstmt.setString(k, user.getLocale());

            int res = pstmt.executeUpdate();

            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_CREATE_USER, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, userLocale);
        }
        return ok;
    }

    /**
     * Update user status.
     *
     * @param map date for update user.
     * @param userLocale locale for messages.
     * @throws DBException
     */
    public boolean updateUserStatus(Map<Integer, Integer> map, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_STATUS_USER);
            int k = 1;
            int res = 0;
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                k = 1;
                pstmt.setInt(k++, entry.getValue());
                pstmt.setInt(k, entry.getKey());
                res = pstmt.executeUpdate();
            }
            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_UPDATE_USER, userLocale), ex);
        } finally {
            DBUtils.close(con, userLocale);
        }
        return ok;
    }


    /**
     * Update user.
     *
     * @param user user to update.
     * @throws DBException
     */
    public void updateUser(User user, String userLocale) throws DBException {
        Connection con = null;
        try {
            con = getConnection();
            updateUser(con, user, userLocale);
            con.commit();
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_UPDATE_USER, userLocale), ex);
        } finally {
            DBUtils.close(con, userLocale);
        }
    }

    /**
     * Create credit card.
     *
     * @param count user to create.
     * @throws DBException
     */
    public boolean createCount(Count count, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_CREATE_COUNT);
            int k = 1;
            pstmt.setString(k++, count.getCount());
            pstmt.setInt(k++, count.getUserId());
            pstmt.setInt(k, count.getIsBlockedId());

            int res = pstmt.executeUpdate();

            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_CREATE_USER_COUNT, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, userLocale);
        }
        return ok;
    }


    /**
     * Update count.
     *
     * @param count         Count entity.
     * @param unlockRequest UnlockRequest entity.
     * @throws DBException
     */
    public boolean updateCount(Count count, UnlockRequest unlockRequest, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_COUNT);
            int res = 0;
            int resU = 0;
            int k = 1;
            pstmt.setString(k++, count.getCount());
            pstmt.setInt(k++, count.getIsBlockedId());
            pstmt.setInt(k, count.getId());
            res = pstmt.executeUpdate();
            if (unlockRequest != null) {

                k = 1;
                pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_UNLOCK_REQUEST);
                pstmt.setInt(k++, unlockRequest.getActual());
                pstmt.setInt(k, unlockRequest.getCountId());
                resU = pstmt.executeUpdate();

            } else {
                resU = 1;
            }
            con.commit();
            ok = res != 0 && resU != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_UPDATE_COUNT, userLocale), ex);
        } finally {
            DBUtils.close(con, userLocale);
        }
        return ok;
    }

    /**
     * Create credit card.
     *
     * @param creditCard user to create.
     * @throws DBException
     */
    public boolean createCreditCard(CreditCard creditCard, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_CREATE_CREDIT_CARD);
            int k = 1;
            pstmt.setLong(k++, creditCard.getNumber());
            pstmt.setString(k++, creditCard.getExpiration() + " 00:00:00");
            pstmt.setInt(k++, creditCard.getCvv());
            pstmt.setInt(k++, creditCard.getCountId());

            int res = pstmt.executeUpdate();

            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_CREATE_USER_CREDIT_CARD, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, userLocale);
        }
        return ok;
    }

    /**
     * Create unlock request.
     *
     * @param unlockRequest unlockRequest to create.
     * @throws DBException
     */
    public boolean createUnlockRequest(UnlockRequest unlockRequest, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_CREATE_UNLOCK_REQUEST);
            int k = 1;
            pstmt.setInt(k++, unlockRequest.getCountId());
            pstmt.setInt(k++, unlockRequest.getActual());

            int res = pstmt.executeUpdate();

            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_CREATE_UNBLOCKED_REQUEST, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, userLocale);
        }
        return ok;
    }


    /**
     * Update credit card.
     *
     * @param creditCard CreditCard entity.
     * @throws DBException
     */
    public boolean updateCreditCard(CreditCard creditCard, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_CREDIT_CARD);
            int res = 0;
            int k = 1;
            pstmt.setLong(k++, creditCard.getNumber());
            pstmt.setString(k++, creditCard.getExpiration() + " 00:00:00");
            pstmt.setInt(k++, creditCard.getCvv());
            pstmt.setInt(k, creditCard.getId());
            res = pstmt.executeUpdate();
            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_UPDATE_USER_CREDIT_CARD, userLocale), ex);
        } finally {
            DBUtils.close(con, userLocale);
        }
        return ok;
    }

    /**
     * Create Payment.
     *
     * @param payment
     * @throws DBException
     */
    public boolean createPayment(Payment payment, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {

            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_CREATE_PAYMENT);
            int k = 1;

            pstmt.setInt(k++, payment.getUserId());
            pstmt.setInt(k++, payment.getCountId());
            pstmt.setString(k++, payment.getCountReceiver());
            pstmt.setFloat(k++, payment.getSum());
            pstmt.setInt(k++, payment.getStatusId());

            int res = pstmt.executeUpdate();

            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_CREATE_PAYMENT, userLocale), ex);
        } finally {
            DBUtils.close(con, pstmt, userLocale);
        }
        return ok;
    }


    /**
     * Update payment.
     *
     * @param payment Payment entity.
     * @throws DBException
     */
    public boolean updatePayment(Payment payment, String userLocale) throws DBException {
        Connection con = null;
        PreparedStatement pstmt = null;
        boolean ok = false;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_PAYMENT);
            int res = 0;
            int k = 1;
            pstmt.setInt(k++, payment.getCountId());
            pstmt.setString(k++, payment.getCountReceiver());
            pstmt.setFloat(k++, payment.getSum());
            pstmt.setInt(k++, payment.getStatusId());
            pstmt.setInt(k++, payment.getId());

            res = pstmt.executeUpdate();
            con.commit();
            ok = res != 0;
        } catch (SQLException ex) {
            DBUtils.rollback(con);
            throw new DBException(Messages.getI18nMessage(Messages.ERR_CANNOT_UPDATE_PAYMENT, userLocale), ex);
        } finally {
            DBUtils.close(con, userLocale);
        }
        return ok;
    }


    /**
     * Update user.
     *
     * @param user user to update.
     * @throws SQLException
     */
    private void updateUser(Connection con, User user, String userLocale) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(SQLQueries.SQL_UPDATE_USER);
            int k = 1;
            pstmt.setString(k++, user.getPassword());
            pstmt.setString(k++, user.getFirstName());
            pstmt.setString(k++, user.getLastName());
            pstmt.setInt(k++, user.getStatusId());
            pstmt.setString(k++, user.getLocale());
            pstmt.setInt(k, user.getId());

            pstmt.executeUpdate();
        } finally {
            DBUtils.close(pstmt, userLocale);
        }
    }

    // //////////////////////////////////////////////////////////
    // Other methods
    // //////////////////////////////////////////////////////////

    /**
     * Extracts a count payment bean from the result set.
     *
     * @param rs Result set from which a user order bean will be extracted.
     * @return CountPaymentBean object
     */

    private CountPaymentBean extractCountPaymentBean(ResultSet rs)
            throws SQLException {
        CountPaymentBean countPaymentBean = new CountPaymentBean();
        countPaymentBean.setId(rs.getInt(Fields.ENTITY_ID));
        countPaymentBean.setCount(rs.getString(Fields.PAYMENT_COUNT));
        countPaymentBean.setCountReceiver(rs.getString(Fields.PAYMENT_RECEIVER));
        countPaymentBean.setSum(rs.getFloat(Fields.PAYMENT_SUM));
        countPaymentBean.setStatusId(rs.getInt(Fields.PAYMENT_STATUS_ID));
        countPaymentBean.setUserId(rs.getInt(Fields.USER_PAYMENT_ID));
        countPaymentBean.setCount(rs.getString(Fields.PAYMENT_COUNT));
        countPaymentBean.setDate(convertDataToString(rs.getString(Fields.PAYMENT_DATE)));
        return countPaymentBean;
    }


    /**
     * Extracts a payment from the result set.
     *
     * @param rs Result set from which a user order bean will be extracted.
     * @return payment object
     */

    private Payment extractPayment(ResultSet rs)
            throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt(Fields.PAYMENT_ID));
        payment.setCountId(rs.getInt(Fields.PAYMENT_COUNT_ID));
        payment.setCountReceiver(rs.getString(Fields.PAYMENT_RECEIVER));
        payment.setSum(rs.getFloat(Fields.PAYMENT_SUM));
        payment.setStatusId(rs.getInt(Fields.PAYMENT_STATUS_ID));
        payment.setUserId(rs.getInt(Fields.USER_PAYMENT_ID));
        payment.setDate(convertDataToString(rs.getString(Fields.PAYMENT_DATE)));
        return payment;
    }

    /**
     * Extracts a user entity from the result set.
     *
     * @param rs Result set from which a user entity will be extracted.
     * @return User entity
     */
    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(Fields.ENTITY_ID));
        user.setLogin(rs.getString(Fields.USER_LOGIN));
        user.setPassword(rs.getString(Fields.USER_PASSWORD));
        user.setFirstName(rs.getString(Fields.USER_FIRST_NAME));
        user.setLastName(rs.getString(Fields.USER_LAST_NAME));
        user.setRoleId(rs.getInt(Fields.USER_ROLE_ID));
        user.setStatusId(rs.getInt(Fields.USER_STATUS_ID));
        user.setLocale(rs.getString(Fields.USER_LOCALE));

        return user;
    }

    private Count extractCount(ResultSet rs) throws SQLException {
        Count count = new Count();
        count.setId(rs.getInt(Fields.ENTITY_ID));
        count.setCount(rs.getString(Fields.COUNT_COUNT));
        count.setUserId(rs.getInt(Fields.USER_ID));
        count.setIsBlockedId(rs.getInt(Fields.COUNT_IS_BLOCKED));
        return count;
    }

    private UnlockRequest extractUnlockRequest(ResultSet rs) throws SQLException {
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setCountId(rs.getInt(Fields.COUNT_ID));
        unlockRequest.setActual(rs.getInt(Fields.UNLOCKED_REQUEST_ACTUAL));
        unlockRequest.setDate(rs.getString(Fields.UNLOCKED_REQUEST_DATE));
        return unlockRequest;
    }

    private CreditCard extractCreditCard(ResultSet rs) throws SQLException {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(rs.getInt(Fields.ENTITY_ID));
        creditCard.setCountId(rs.getInt(Fields.COUNT_ID));
        creditCard.setNumber(rs.getLong(Fields.CREDIT_CARD_NUMBER));
        creditCard.setExpiration(convertDataToString(rs.getString(Fields.CREDIT_CARD_EXPIRATION)));
        creditCard.setCvv(rs.getInt(Fields.CREDIT_CARD_CVV));
        return creditCard;
    }


    private UserAdminChangeBean extractUserAdminChangeBean(ResultSet rs) throws SQLException {
        UserAdminChangeBean userAdminChangeBean = new UserAdminChangeBean();
        userAdminChangeBean.setUserId(rs.getInt(Fields.ENTITY_ID));
        userAdminChangeBean.setUserFirstName(rs.getString(Fields.USER_FIRST_NAME));
        userAdminChangeBean.setUserLastName(rs.getString(Fields.USER_LAST_NAME));
        userAdminChangeBean.setUserLogin(rs.getString(Fields.USER_LOGIN));
        userAdminChangeBean.setStatusName(UserStatus.values()[(rs.getInt(Fields.USER_STATUS_ID))].getName());
        return userAdminChangeBean;
    }

    private UserCountAdminChangeBean extractUserCountAdminChangeBean(ResultSet rs) throws SQLException {
        UserCountAdminChangeBean userCountAdminChangeBean = new UserCountAdminChangeBean();
        userCountAdminChangeBean.setId(rs.getInt(Fields.COUNT_ID));
        userCountAdminChangeBean.setUserId(rs.getInt(Fields.USER_ID));
        userCountAdminChangeBean.setCount(rs.getString(Fields.COUNT_NO));
        userCountAdminChangeBean.setIsBlocked(Bool.values()[rs.getInt(Fields.COUNT_IS_BLOCKED)].getName());
        userCountAdminChangeBean.setUnblockedRequest(Bool.values()[rs.getInt(Fields.REQ_ACTUAL)].getName());
        return userCountAdminChangeBean;
    }

    private UserCountClientChangeBean extractUserCountClientChangeBean(ResultSet rs, Map<Long, Integer> creditCardMap) throws SQLException {
        UserCountClientChangeBean countClientChangeBean = new UserCountClientChangeBean();
        countClientChangeBean.setUserId(rs.getInt(Fields.USER_ID));
        countClientChangeBean.setCountId(rs.getInt(Fields.COUNT_ID));
        countClientChangeBean.setCount(rs.getString(Fields.COUNT_NO));
        countClientChangeBean.setIsBlocked(Bool.values()[rs.getInt(Fields.COUNT_IS_BLOCKED)].getName());
        countClientChangeBean.setUnblockedRequest(Bool.values()[rs.getInt(Fields.REQ_ACTUAL)].getName());

        if (rs.getInt(Fields.REQ_ACTUAL) == 0 && rs.getInt(Fields.COUNT_IS_BLOCKED) == 1) {

            //Client can set request
            countClientChangeBean.setCanUnblocked(1);

        } else {
            countClientChangeBean.setCanUnblocked(0);
        }

        List<Long> listCreditNumbers = new ArrayList<>();

        for (Map.Entry<Long, Integer> pair : creditCardMap.entrySet()) {
            if (pair.getValue() == countClientChangeBean.getCountId()) {
                listCreditNumbers.add(pair.getKey());
            }
        }
        countClientChangeBean.setCreditCardList(listCreditNumbers);

        return countClientChangeBean;
    }


    private static String convertDataToString(String date) {

        String res = date.replaceFirst(" 00:00:00", "");
        return res;
    }

}