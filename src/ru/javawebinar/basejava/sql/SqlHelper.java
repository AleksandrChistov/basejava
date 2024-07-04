package ru.javawebinar.basejava.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeQuery(String sql) {
        executeQuery(sql, PreparedStatement::execute);
    }

    public <R> R executeQuery(String sql, SqlExecutor<PreparedStatement, R> executor) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            return executor.execute(ps);
        } catch (SQLException e) {
            throw SqlExceptionUtil.convertException(e);
        }
    }

    @FunctionalInterface
    public interface SqlExecutor<T, R> {
        R execute(T t) throws SQLException;
    }
}
