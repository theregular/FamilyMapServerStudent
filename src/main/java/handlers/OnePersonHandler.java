package handlers;

import com.sun.net.httpserver.HttpExchange;
import requestresult.OnePersonResult;
import service.OnePersonService;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class OnePersonHandler extends Handler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                String urlPath = exchange.getRequestURI().toString();
                String personID = urlPath.replace("/person/", ""); //delete /person/ from file path to leave personID

                OnePersonService service = new OnePersonService();
                OnePersonResult result = service.getPerson(personID);
                String resData = gson.toJson(result);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                OutputStream resBody = exchange.getResponseBody();
                writeString(resData, resBody);

                resBody.close();
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
