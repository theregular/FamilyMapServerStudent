package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import dataAccess.DataAccessException;
import requestresult.LoginRequest;
import requestresult.LoginResult;
import service.LoginService;

public class LoginHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);
                LoginService service = new LoginService();
                LoginResult result = service.login(request); //login user
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
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
