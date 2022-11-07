package ru.akirakozov.sd.refactoring.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import ru.akirakozov.sd.refactoring.database.ProductConnectionSource;

public class AbstractDAO<T> {

    protected void executeUpdate(String sql) {
        try (Connection connection = ProductConnectionSource.instance().createConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
