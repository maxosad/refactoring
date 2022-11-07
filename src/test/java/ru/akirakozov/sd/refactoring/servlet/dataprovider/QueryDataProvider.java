package ru.akirakozov.sd.refactoring.servlet.dataprovider;

import java.util.List;
import org.testng.annotations.DataProvider;
import ru.akirakozov.sd.refactoring.entity.Product;

public class QueryDataProvider {
    @DataProvider
    public static Object[][] queryData() {
        Product product1 = new Product("productName1", 100);
        Product product2 = new Product("productName2", 200);
        Product product3 = new Product("productName3", 300);
        Product product4 = new Product(null, 900);
        Product product5 = new Product("productName0", 0);
        return new Object[][] {
            {"max", List.of(
                product1,
                product2,
                product3,
                product4,
                product4,
                product5,
                product5
            ), "Product with max price: " + product4},
            {"min", List.of(
                product1,
                product2,
                product3,
                product4,
                product4,
                product5,
                product5
            ), "Product with min price: " + product5},
            {"count", List.of(
                product1,
                product2,
                product3,
                product4,
                product4,
                product5,
                product5
            ), "Number of products: " + 7},
            {"sum", List.of(
                product1,
                product2,
                product3,
                product4,
                product4,
                product5,
                product5
            ), "Summary price: " + 2400},
            {"sum", List.of(), "Summary price: 0"},
            {"count", List.of(), "Number of products: 0"}
        };
    }

    @DataProvider
    public static Object[][] queryUnknownCommandData() {
        return new Object[][] {
            {"abc", "Unknown command: abc"}
        };
    }
}
