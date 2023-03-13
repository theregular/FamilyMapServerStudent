package handlers;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.FileSystems;

import com.sun.net.httpserver.*;

public class FileHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                    String urlPath = exchange.getRequestURI().toString();
                File file;
                if (urlPath.equals(null) || urlPath.equals("/")) {
                    urlPath = "web/index.html";
                }
                else {
                    urlPath = "web/" + urlPath;
                }
                file = new File(urlPath);

                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(), respBody);
                    respBody.close();
                }
                else {
                    //404 not found error
                    Path path = FileSystems.getDefault().getPath("web/HTML/404.html");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(path, respBody);
                    respBody.close();
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
