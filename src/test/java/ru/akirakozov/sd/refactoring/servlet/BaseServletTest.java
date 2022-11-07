package ru.akirakozov.sd.refactoring.servlet;

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

public abstract class BaseServletTest {

    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;

    protected StringWriter stringWriter;
    protected PrintWriter printWriter;
    protected ProductDAO productDAO;
    protected HtmlPrinter htmlPrinter;

    private AutoCloseable closeable;

    @BeforeClass
    public void beforeClass() {
        htmlPrinter = new HtmlPrinter();
        productDAO = new ProductDAO();
        productDAO.createTable();
    }

    @BeforeMethod
    public final void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        productDAO.deleteAll();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        productDAO.deleteAll();
        printWriter.close();
        stringWriter.close();

        closeable.close();
    }

    @AfterClass
    public void afterClass() {
        productDAO.dropTable();
    }
}
