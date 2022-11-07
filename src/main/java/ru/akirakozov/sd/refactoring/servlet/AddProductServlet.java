package ru.akirakozov.sd.refactoring.servlet;

import java.io.IOException;
import java.io.UncheckedIOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

/**
 * @author akirakozov
 */
public class AddProductServlet extends BaseServlet {
    public AddProductServlet(ProductDAO productDAO, HtmlPrinter htmlPrinter) {
        super(productDAO, htmlPrinter);
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        productDAO.insert(new Product(name, price));
        try {
            htmlPrinter.printText(response.getWriter(), "OK");
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
