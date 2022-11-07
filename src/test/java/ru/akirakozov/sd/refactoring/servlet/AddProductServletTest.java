package ru.akirakozov.sd.refactoring.servlet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.servlet.dataprovider.AddProductDataProvider;

public class AddProductServletTest extends BaseServletTest {

    private AddProductServlet addProductServlet;

    @BeforeMethod
    public void initServlet() {
        addProductServlet = new AddProductServlet(productDAO, htmlPrinter);
    }

    @Test(dataProvider = "addProductPositiveData", dataProviderClass = AddProductDataProvider.class)
    public void addProductPositiveTest(String name, Integer price) throws IOException {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("price")).thenReturn(String.valueOf(price));
        doThrow(new AssertionError("bad status code"))
            .when(response).setStatus(not(eq(HttpServletResponse.SC_OK)));
        when(response.getWriter()).thenReturn(printWriter);
        addProductServlet.doGet(request, response);
        List<Product> products = productDAO.findAll();
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(products).hasSize(1).first().asString().isEqualTo(name + " " + price);

        String html = stringWriter.toString()
                                  .trim()
                                  .replaceAll("\\s+", " ");
        softAssertions.assertThat(html).containsIgnoringCase("OK");
        softAssertions.assertAll();
    }

    @Test(dataProvider = "addProductNegativeData", dataProviderClass = AddProductDataProvider.class)
    public void addProductNegativePriceNegativeTest(String name, String price) throws IOException {
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("price")).thenReturn(price);
        when(response.getWriter()).thenReturn(printWriter);
        assertThat(catchThrowable(() -> addProductServlet.doGet(request, response))).isInstanceOf(Exception.class);
    }

    @AfterMethod
    public void releaseServlet() {
        addProductServlet = null;
    }
}
