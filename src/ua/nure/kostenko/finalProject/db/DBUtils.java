package ua.nure.kostenko.finalProject.db;

import ua.nure.kostenko.finalProject.exception.Messages;

import org.apache.log4j.Logger;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {

    private static final Logger LOG = Logger.getLogger(DBUtils.class);    
    
    private DBUtils() {

    }


    /**
     * Closes a connection.
     *
     * @param con
     *            Connection to be closed.
     */
    public static void close(Connection con, String userLocale) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, ex);
            }
        }
    }

    /**
     * Closes a statement object.
     */
    public static void close(Statement stmt, String userLocale) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
            }
        }
    }

    /**
     * Closes a result set object.
     */
    public static void close(ResultSet rs, String userLocale) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                LOG.error(Messages.ERR_CANNOT_CLOSE_RESULT_SET, ex);
            }
        }
    }

    /**
     * Closes resources.
     */
    public static void close(Connection con, Statement stmt, String userLocale) {
        close(stmt, userLocale);
        close(con, userLocale);
    }


    /**
     * Closes resources.
     */
    public static void close(Connection con, Statement stmt, ResultSet rs, String userLocale) {
        close(rs, userLocale);
        close(stmt, userLocale);
        close(con, userLocale);
    }

    /**
     * Rollbacks a connection.
     *
     * @param con
     *            Connection to be rollbacked.
     */
    public static void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                LOG.error("Cannot rollback transaction", ex);
            }
        }
    }


}
