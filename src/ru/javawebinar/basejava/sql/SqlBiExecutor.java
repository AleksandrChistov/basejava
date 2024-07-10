package ru.javawebinar.basejava.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlBiExecutor<T, R> {
    void execute(T t, R r) throws SQLException;
}
