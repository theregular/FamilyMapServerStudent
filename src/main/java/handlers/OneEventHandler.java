package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import com.google.gson.Gson;
import requestresult.OneEventResult;
import service.OneEventService;


public class OneEventHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String urlPath = exchange.getRequestURI().toString();
                    String eventID = urlPath.replace("/event/", ""); //delete /event/ from file path to leave eventID

                    OneEventService service = new OneEventService();
                    OneEventResult result = service.getEvent(authToken, eventID);
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
