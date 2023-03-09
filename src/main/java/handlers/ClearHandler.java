package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import com.google.gson.Gson;

import service.ClearService;
import requestresult.Result;

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //request null so not necessary?
                Headers reqHeaders = exchange.getRequestHeaders();

                ClearService service = new ClearService();
                Result result = service.clear();

                Gson gson = new Gson();
                String responseStr = gson.toJson(result);

                OutputStream respBody = exchange.getResponseBody();
                writeString(responseStr, respBody);

                exchange.getResponseBody().close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
