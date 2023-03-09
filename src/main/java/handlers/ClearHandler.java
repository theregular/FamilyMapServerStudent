package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import com.google.gson.Gson;

import requestresult.ClearResult;
import service.ClearService;
import requestresult.Result;

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        ClearService service = new ClearService();
        Result result;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                //request null so not necessary?
                //Headers reqHeaders = exchange.getRequestHeaders();

                result = service.clear();

                //Gson gson = new Gson();
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
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
