package com.urise.webapp.sql;

import com.urise.webapp.exception.convertSqlException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String sql, executeSql<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            throw new convertSqlException(e);
        }
    }

    @FunctionalInterface
    public interface executeSql<T> {
        T execute(PreparedStatement st) throws SQLException;
    }
}