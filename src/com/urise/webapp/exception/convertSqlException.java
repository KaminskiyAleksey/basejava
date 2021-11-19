package com.urise.webapp.exception;

import java.sql.SQLException;

public class convertSqlException extends RuntimeException  {

    public convertSqlException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            throw new ExistStorageException(null);
        }
        else{
            throw new StorageException(e);
        }
    }
}
