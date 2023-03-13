package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import requestresult.RegisterRequest;
import requestresult.RegisterResult;
import requestresult.Result;
import service.ClearService;
import service.RegisterService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                //fills request with info from the Json input
                RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request); //registers user
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
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch(IOException e){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
