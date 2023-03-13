package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import service.AllPersonsService;
import requestresult.AllPersonsResult;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class AllPersonsHandler extends Handler {
    public void handle(HttpExchange exchange) throws IOException {
        success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    AllPersonsService service = new AllPersonsService();
                    AllPersonsResult result = service.getAllPersons(authToken);
                    if (result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    String resData = gson.toJson(result);
                    OutputStream resBody = exchange.getResponseBody();
                    writeString(resData, resBody);
                    resBody.close();
                    success = true;
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
