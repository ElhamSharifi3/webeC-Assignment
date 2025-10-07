package m1_hello;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {

    private static final String SERVER_CONTEXT = "/myapp";

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {

            String response =
                """
                <!DOCTYPE html>
                <html>
                <head>
                <meta charset="UTF-8">
                <title>Hello</title>
                </head>
                <body>
                <h1>Hello, Server World!</h1>
                </body>
                </html>
                """;

            respond(exchange, response);
        }

        private static void respond(HttpExchange exchange, String response) throws IOException {
            try (OutputStream os = exchange.getResponseBody()) {
                exchange.sendResponseHeaders(200, response.length());
                os.write(response.getBytes());
            } catch (IOException e) {
                exchange.sendResponseHeaders(404, 0);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8080), 0);
        server.createContext(SERVER_CONTEXT, new MyHandler());
        server.start();
        System.out.println("http://localhost:8080"+SERVER_CONTEXT);
    }
}
