package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

public abstract class BaseServlet extends HttpServlet {
    protected final ProductDAO productDAO;
    protected final HtmlPrinter htmlPrinter;

    protected BaseServlet(ProductDAO productDAO, HtmlPrinter htmlPrinter) {
        this.productDAO = productDAO;
        this.htmlPrinter = htmlPrinter;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        doGetImpl(request, response);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected abstract void doGetImpl(HttpServletRequest request, HttpServletResponse response);
}
