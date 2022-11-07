package ru.akirakozov.sd.refactoring.html;

import java.io.PrintWriter;
import java.util.List;
import ru.akirakozov.sd.refactoring.entity.Product;

public class HtmlPrinter {

    private String getBody() {
        return "<html><body>%s%s</body></html>";
    }

    private String getHeader() {
        return "<h1>%s</h1>";
    }

    public String getProductsHtml(List<Product> products) {
        StringBuilder stringBuilder = new StringBuilder();
        products.forEach(product -> {
            stringBuilder.append(product.getName()).append("\t").append(product.getPrice()).append(" </br>");
        });

        return stringBuilder.toString();
    }

    public void printText(PrintWriter printWriter, String text) {
        printWriter.println(text);
    }

    public void printHtml(PrintWriter printWriter,
                          String header,
                          String text) {
        String h1 = header == null ? "" : String.format(getHeader(), header);
        printWriter.println(String.format(getBody(), h1, text));
    }

    public void printHtml(PrintWriter printWriter,
                          String text) {
        printHtml(printWriter, null, text);
    }
}