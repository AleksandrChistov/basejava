package ru.javawebinar.basejava.utils;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeQuery(String sql, Writable<PreparedStatement> writer) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            writer.write(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <R> R executeQuery(String sql, Readable<PreparedStatement, R> reader) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            return reader.read(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface Writable<T> {
        void write(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface Readable<T, R> {
        R read(T t) throws SQLException;
    }
}