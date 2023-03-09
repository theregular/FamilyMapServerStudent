package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import requestresult.Result;
import service.AllEventsService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class AllEventsHandler extends Handler  {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                AllEventsService events = new AllEventsService();
                Result result = events.getAllEvents();

                Gson gson = new Gson();
                String responseStr = gson.toJson(result);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                OutputStream respBody = exchange.getResponseBody();
                writeString(responseStr, respBody);

                exchange.getResponseBody().close();
                success = true;


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
