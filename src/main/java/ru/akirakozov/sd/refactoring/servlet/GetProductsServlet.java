package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseServlet {
    public GetProductsServlet(ProductDAO productDAO, HtmlPrinter htmlPrinter) {
        super(productDAO, htmlPrinter);
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) {
        List<Product> products = productDAO.findAll();
        try {
            String text = htmlPrinter.getProductsHtml(products);
            htmlPrinter.printHtml(response.getWriter(), text);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
