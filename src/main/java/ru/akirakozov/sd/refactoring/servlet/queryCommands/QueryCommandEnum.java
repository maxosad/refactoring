package ru.akirakozov.sd.refactoring.servlet.queryCommands;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.entity.Product;
import ru.akirakozov.sd.refactoring.html.HtmlPrinter;

public enum QueryCommandEnum {
    MAX() {
        @Override
        public void runCommand(HttpServletRequest request, HttpServletResponse response,
                               ProductDAO productDAO, HtmlPrinter htmlPrinter) {
            try {
                Product product = productDAO.findMaxByPrice();
                String header = "Product with max price: ";
                String text = product == null ? "" : htmlPrinter.getProductsHtml(List.of(product));
                htmlPrinter.printHtml(response.getWriter(), header, text);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    },
    MIN {
        @Override
        public void runCommand(HttpServletRequest request, HttpServletResponse response,
                               ProductDAO productDAO, HtmlPrinter htmlPrinter) {
            try {
                Product product = productDAO.findMinByPrice();
                String header = "Product with min price: ";
                String text = product == null ? "" : htmlPrinter.getProductsHtml(List.of(product));
                htmlPrinter.printHtml(response.getWriter(), header, text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    },
    SUM {
        @Override
        public void runCommand(HttpServletRequest request, HttpServletResponse response,
                               ProductDAO productDAO, HtmlPrinter htmlPrinter) {
            try {
                Integer sum = productDAO.sum();
                String text = "Summary price: " + (sum == null ? "" : sum);
                htmlPrinter.printHtml(response.getWriter(), text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    },
    COUNT {
        @Override
        public void runCommand(HttpServletRequest request, HttpServletResponse response,
                               ProductDAO productDAO, HtmlPrinter htmlPrinter) {
            try {
                Integer count = productDAO.count();
                String text = "Number of products: " + (count == null ? "" : count);
                htmlPrinter.printHtml(response.getWriter(), text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    },
    DEFAULT {
        @Override
        public void runCommand(HttpServletRequest request, HttpServletResponse response,
                               ProductDAO productDAO, HtmlPrinter htmlPrinter) {
            try {
                htmlPrinter.printText(response.getWriter(),
                    "Unknown command: " + request.getParameter("command"));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    };

    public abstract void runCommand(HttpServletRequest request, HttpServletResponse response,
                                    ProductDAO productDAO, HtmlPrinter htmlPrinter);
}
