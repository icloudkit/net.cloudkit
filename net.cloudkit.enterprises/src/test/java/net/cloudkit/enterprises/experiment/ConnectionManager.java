package net.cloudkit.enterprises.experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 采用ThreadLocal封装Connection
 */
public class ConnectionManager {

    //定义ThreadLocal静态变量，确定存取类型为Connection
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    /**
     * 得到Connection
     * @return
     */
    public static Connection getConnection() {
        Connection conn = connectionHolder.get();
        //如果在当前线程中没有绑定相应的Connection
        if (conn == null) {
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                String url = "jdbc:oracle:thin:@localhost:1521:bjpowern";
                String username = "drp1";
                String password = "drp1";
                conn = DriverManager.getConnection(url, username, password);
                //将Connection设置到ThreadLocal
                connectionHolder.set(conn);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接方法
     * @return
     */
    public static void closeConnection() {
        Connection conn = connectionHolder.get();
        if (conn != null) {
            try {
                conn.close();
                //从ThreadLocal中清除Connection
                connectionHolder.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭数据库连接方法
     * @return
     */
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs ) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 事务开启
     * @return
     */
    public static void beginTransaction(Connection conn) {
        try {
            if (conn != null) {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false); //手动提交
                }
            }
        }catch(SQLException e) {}
    }

    /**
     * 事务提交
     * @return
     */
    public static void commitTransaction(Connection conn) {
        try {
            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        }catch(SQLException e) {}
    }

    /**
     * 事务回滚
     * @return
     */
    public static void rollbackTransaction(Connection conn) {
        try {
            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            }
        }catch(SQLException e) {}
    }
}
