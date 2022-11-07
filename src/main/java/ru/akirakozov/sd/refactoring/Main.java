package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        HtmlPrinter htmlPrinter = new HtmlPrinter();
        ProductDAO productDAO = new ProductDAO();
        productDAO.createTable();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(productDAO, htmlPrinter)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productDAO, htmlPrinter)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productDAO, htmlPrinter)), "/query");

        server.start();
        server.join();
        productDAO.dropTable();
    }
}
