package m1_hello;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Server {

    private static final String SERVER_CONTEXT = "/myapp";

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {

            List<String> lines = new ArrayList<>(List.of(
                "Es ist nie zu spät zum unpünktlich sein.",
                "Ich komme noch früh genug zu spät.",
                "Morgenstund ist aller Laster Anfang.",
                "Den frühen Fänger wurmt der Vogel",
                "Was Du heute kannst besorgen hat sicher auch noch Zeit bis morgen."
            ));
            Collections.shuffle(lines);

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
                """
                + lines.getFirst()
                +
                """
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
