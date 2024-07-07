package ru.javawebinar.basejava.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlExecutor<T, R> {
    R execute(T t) throws SQLException;
}
