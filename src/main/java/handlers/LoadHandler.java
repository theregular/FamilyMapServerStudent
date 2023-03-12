package handlers;

import com.sun.net.httpserver.HttpExchange;
import requestresult.ClearResult;
import requestresult.LoadRequest;
import requestresult.LoadResult;
import service.ClearService;
import service.LoadService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                //fills request with info from the Json input
                LoadRequest request = gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);
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
