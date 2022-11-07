package ru.akirakozov.sd.refactoring.servlet.dataprovider;

import org.testng.annotations.DataProvider;

public class AddProductDataProvider {
    @DataProvider
    public static Object[][] addProductPositiveData() {
        return new Object[][] {
            {null, 200},
            {"productName", 200},
            {"productName", -200}
        };
    }

    @DataProvider
    public static Object[][] addProductNegativeData() {
        return new Object[][] {
            {"productName", null},
            {"productName", "rub200"}
        };
    }
}
