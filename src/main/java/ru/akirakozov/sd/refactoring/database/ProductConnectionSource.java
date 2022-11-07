package ru.akirakozov.sd.refactoring.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ProductConnectionSource {
    private static final ProductConnectionSource instance = new ProductConnectionSource();

    public static ProductConnectionSource instance() {
        return instance;
    }

    public Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ProductConnectionSource() {

    }
}
