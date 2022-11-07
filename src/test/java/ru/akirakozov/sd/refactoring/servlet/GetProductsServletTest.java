package ru.akirakozov.sd.refactoring.servlet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.servlet.dataprovider.GetProductsDataProvider;

public class GetProductsServletTest extends BaseServletTest {

    private GetProductsServlet getProductsServlet;

    @BeforeMethod
    public void initServlet() {
        getProductsServlet = new GetProductsServlet(productDAO, htmlPrinter);
    }

    @Test(dataProvider = "getProductsData", dataProviderClass = GetProductsDataProvider.class)
    public void getProductsTest(List<Product> products, String expected) throws IOException {
        products.forEach(product -> productDAO.insert(product));

        doThrow(new AssertionError("bad status code"))
            .when(response).setStatus(not(eq(HttpServletResponse.SC_OK)));
        when(response.getWriter()).thenReturn(printWriter);

        getProductsServlet.doGet(request, response);

        String html = stringWriter.toString()
                                  .trim()
                                  .replaceAll("\\s+", " ");
        assertThat(html).startsWith("<html>")
                        .endsWith("</html>");
        Document document = Jsoup.parse(html, Parser.xmlParser());
        String bodyText = document.body().text().trim();
        assertThat(bodyText)
            .containsIgnoringCase(expected);
    }

    @AfterMethod
    public void releaseServlet() {
        getProductsServlet = null;
    }
}
