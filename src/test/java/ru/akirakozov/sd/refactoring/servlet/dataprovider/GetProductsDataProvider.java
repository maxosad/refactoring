package ru.akirakozov.sd.refactoring.servlet.dataprovider;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.DataProvider;
import ru.akirakozov.sd.refactoring.entity.Product;

public class GetProductsDataProvider {
    @DataProvider
    public static Object[][] getProductsData() {
        Product product1 = new Product("productName1", 100);
        Product product2 = new Product("productName2", 200);
        Product product2_1 = new Product("productName2", 200);
        Product product3 = new Product("productName3", 300);
        Product product4 = new Product(null, 900);
        Product product4_1 = new Product(null, 900);
        Product product5 = new Product("productName0", 0);
        Product product5_1 = new Product("productName0", 0);
        Product product6 = new Product("productName6", -100);
        return new Object[][] {
            {List.of(product1, product2, product2_1, product3, product4, product4_1, product5, product5_1, product6),
                Stream
                    .of(product1, product2, product2_1, product3, product4, product4_1, product5, product5_1, product6)
                    .map(Product::toString).collect(Collectors.joining(" "))
            }
        };
    }
}
