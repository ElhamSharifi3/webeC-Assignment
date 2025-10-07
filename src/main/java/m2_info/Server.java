package m2_info;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.LinkedHashMap;
import java.util.Map;

public class Server {

    private static final String SERVER_CONTEXT = "/myapp";

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {

            Headers headers = exchange.getRequestHeaders();

            Map<String, String> core = new LinkedHashMap<>(16);
            core.put("Protocol",    exchange.getProtocol());
            core.put("Method",      exchange.getRequestMethod());
            String uri = exchange.getRequestURI().toString();
            core.put("URI",         uri);

            String response =
                """
                <!DOCTYPE html>
                <html>
                <head>
                <meta charset="UTF-8">
                <title>Server Spoof</title>
                <style>
                    body {
                        margin-left:           2em;
                    }
                    .grid {
                        display:               grid;
                        grid-template-columns: repeat(2,max-content);
                        gap:                   .3em 2em;
                    }
                </style>
                </head>
                <body>
                <h1>Spoofing HTTP Information</h1>
                """
                + "<h2>Protocol Info</h2>"
                + makeGrid(core)
                + "<h2>Http Request Headers</h2>"
                + makeGrid(headers)
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

        private static StringBuffer makeGrid(Map headers) {
            StringBuffer result = new StringBuffer();
            result.append("<div class='grid'>");
            for ( Object key : headers.keySet()) {
                result.append("<div>");
                result.append(key);
                result.append("</div>");
                result.append("<div>");
                result.append(headers.get(key).toString());
                result.append("</div>");
            }
            result.append("</div>");
            return result;
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
