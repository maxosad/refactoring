package ru.akirakozov.sd.refactoring.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.akirakozov.sd.refactoring.database.ProductConnectionSource;
import ru.akirakozov.sd.refactoring.entity.Product;

public class ProductDAO extends AbstractDAO<Product> {
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS PRODUCT";
        executeUpdate(sql);
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";
        executeUpdate(sql);
    }

    public void insert(Product product) {
        String sql = "INSERT INTO PRODUCT "
            + "(NAME, PRICE) VALUES (\"" + product.getName()
            + "\"," + product.getPrice() + ")";
        executeUpdate(sql);
    }

    public void deleteAll() {
        String sql = "DELETE FROM PRODUCT";
        executeUpdate(sql);
    }

    public List<Product> findAll() {
        return findProductsByQuery("SELECT * FROM PRODUCT");
    }

    public Product findMinByPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        List<Product> list = findProductsByQuery(sql);
        if (list.isEmpty()) {
            return null;
        }
        return list.stream().findFirst().get();
    }

    public Product findMaxByPrice() {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        List<Product> list = findProductsByQuery(sql);
        if (list.isEmpty()) {
            return null;
        }
        return list.stream().findFirst().get();
    }

    public Integer count() {
        String sql = "SELECT COUNT(*) FROM PRODUCT";
        return getFunctionResult(sql);
    }

    public Integer sum() {
        String sql = "SELECT SUM(price) FROM PRODUCT";
        return getFunctionResult(sql);
    }

    private List<Product> findProductsByQuery(String sql) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ProductConnectionSource.instance().createConnection();
             Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                products.add(new Product(name, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    private Integer getFunctionResult(String sql) {
        try (Connection connection = ProductConnectionSource.instance().createConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
