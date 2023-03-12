package handlers;

import com.sun.net.httpserver.HttpExchange;
import requestresult.FillResult;
import service.FillService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                String urlPath = exchange.getRequestURI().toString();

                //parse out info
                String noFill = urlPath.replace("/fill/", "");
                String generations;
                String username;
                if (noFill.contains("/")) {
                    int splitIndex = noFill.indexOf('/');
                    username = noFill.substring(0,splitIndex);
                    generations = noFill.replace(username + "/", "");
                }
                else {
                    username = noFill;
                    generations = "4";
                }
                System.out.println(urlPath);
                System.out.println(noFill);
                System.out.println(username);
                System.out.println(generations);

                FillService service = new FillService();
                FillResult result = service.fill(username, Integer.parseInt(generations)); //fill tree
                String resData = gson.toJson(result);

                //System.out.println("made it past fill");



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
