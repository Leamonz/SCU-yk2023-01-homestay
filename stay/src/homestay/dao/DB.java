package homestay.dao;

import homestay.utils.ConfigMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据库，CRUD
 */
public class DB {
    private Connection connection;
    private Statement statement;

    public DB(String dbName) {
        try {
            Class.forName(ConfigMysql.getInstance().getString("db.driverClassName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("mysql driver load success");
        try {
            System.out.println(ConfigMysql.getInstance().getString("db.url") + dbName +
                    ConfigMysql.getInstance().getString("db.username") +
                    ConfigMysql.getInstance().getString("db.password"));
            connection = DriverManager.getConnection(
                    ConfigMysql.getInstance().getString("db.url") + dbName,
                    ConfigMysql.getInstance().getString("db.username"),
                    ConfigMysql.getInstance().getString("db.password")
            );
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("mysql link success");
    }

    public void close() {
        try {
            statement.close();
            connection.close();
            System.out.println("mysql close");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet resultset = null;
        try {
            resultset = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultset;
    }
    public void executeUpdate(String sql){
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
