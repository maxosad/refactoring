package ru.akirakozov.sd.refactoring.servlet;

import com.google.common.base.Enums;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;
import ru.akirakozov.sd.refactoring.servlet.queryCommands.QueryCommandEnum;

/**
 * @author akirakozov
 */
public class QueryServlet extends BaseServlet {
    public QueryServlet(ProductDAO productDAO, HtmlPrinter htmlPrinter) {
        super(productDAO, htmlPrinter);
    }

    @Override
    protected void doGetImpl(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");
        QueryCommandEnum commandEnum =
            Enums.getIfPresent(QueryCommandEnum.class, command.toUpperCase(Locale.ROOT)).or(QueryCommandEnum.DEFAULT);
        commandEnum.runCommand(request, response, productDAO, htmlPrinter);
    }
}
