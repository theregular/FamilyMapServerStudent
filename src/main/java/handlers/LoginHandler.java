package handlers;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

public class LoginHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        //try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();

            }
        //}
        /*
        catch (IOException e) {

        }
         */
    }
}
