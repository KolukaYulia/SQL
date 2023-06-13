package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();


    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection( "jdbc:mysql://185.119.57.64:3306/app", "app", "pass" );
    }

    public static DataHelper.VerificationCode getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try (var conn = getConn()){
            var code = runner.query(conn, codeSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM auth_codes");
        runner.execute(connection, "DELETE FROM card_transactions");
        runner.execute(connection, "DELETE FROM cards");
        runner.execute(connection, "DELETE FROM users");
    }
}
