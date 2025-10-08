package src;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Server {

    private static final String SERVER_CONTEXT = "/MyProgrammingErrorTracker";

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8">
                  <title>My Programming Error Tracker</title>
                  <style>
                    body {
                      margin: 0;
                      font-family: Georgia, serif;
                      background-color: #fff3fd;
                      text-align: left;
                    }
                    header {
                      background-color: #9c7fa8;
                      color: #fff3fd;
                      text-align: left;
                      padding: 1rem 20px;
                      font-size: 1.5rem;
                      margin-bottom: 40px;
                    }
                    main {
                      max-width: 800px;
                      margin: 0 auto;
                      padding: 0 20px 40px 20px;
                      width: 100%;
                    }
                    footer {
                      background-color: #3a374f;
                      font-size: larger;
                      font-family: Papyrus, Fantasy;
                      margin: 0;
                      padding: 1.5rem 0;
                      text-align: center;
                      color: #fff3fd;
                    }
                    h2, h3 {
                      color: #3a374f;
                      font-family: Georgia, serif;
                    }
                    p {
                      margin-bottom: 1rem;
                    }
                    pre {
                      background-color: #f7ecfa;
                      padding: 10px;
                      border-radius: 8px;
                      overflow-x: auto;
                    }
                    .error {
                      margin-bottom: 3rem;
                      padding-bottom: 2rem;
                      border-bottom: 1.5px solid #b8a4c4;
                    }
                    .error:last-child {
                      border-bottom: none;
                    }
                    footer a {
                      color: #fff3fd;
                      text-decoration: none;
                    }
                    footer a:hover {
                      text-decoration: underline;
                    }
                  </style>
                </head>
                <body>
                  <header>
                    <dfn title="Code Kitchen Übung"></dfn> My Programming Error Tracker
                  </header>

                  <main>
                    <h2>Introduction</h2>
                    <p>
                      This web page helps me record, organize, and learn from the errors I encounter while programming in Java.<br>
                      By keeping track of the error messages, their causes, and solutions, I can improve my coding skills and avoid
                      repeating the same mistakes.
                    </p>

                    <h2>Common Java Errors</h2>

                    <section class="error">
                      <h3>NullPointerException</h3>
                      <p><strong>Cause:</strong> Trying to use an object that was never initialized.</p>
                      <p><strong>Solution:</strong> Initialize the object before using it.</p>
                      <p><strong>Note:</strong> Always check for null references before accessing object properties or methods.</p>
                      <p><strong>Before Fix:</strong></p>
                      <pre>
public class Main {
    public static void main(String[] args) {
        String text = null;
        System.out.println(text.length());
    }
}
                      </pre>
                      <p><strong>After Fix:</strong></p>
                      <pre>
public class Main {
    public static void main(String[] args) {
        String text = "Hello";
        System.out.println(text.length());
    }
}
                      </pre>
                    </section>

                    <section class="error">
                      <h3>ArrayIndexOutOfBoundsException</h3>
                      <p><strong>Cause:</strong> Trying to access an index outside the valid range of an array.</p>
                      <p><strong>Solution:</strong> Ensure the index is within the array’s valid range (0 to length - 1).</p>
                      <p><strong>Note:</strong> Check your loop conditions and array size before accessing elements.</p>
                      <p><strong>Before Fix:</strong></p>
                      <pre>
public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        System.out.println(numbers[3]); // Invalid index
    }
}
                      </pre>
                      <p><strong>After Fix:</strong></p>
                      <pre>
public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        System.out.println(numbers[2]); // Valid index
    }
}
                      </pre>
                    </section>
                  </main>

                  <footer>
                    <address>
                      @ Elham Sharifi,
                      <a href="mailto:elham.sharifi@students.fhnw.ch">elham.sharifi@students.fhnw.ch</a>
                    </address>
                  </footer>
                </body>
                </html>
                """;

            respond(exchange, response);
        }

        private void respond(HttpExchange exchange, String response) throws IOException {
            exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8081), 0);
        server.createContext(SERVER_CONTEXT, new MyHandler());
        server.start();
        System.out.println("Server started at: http://localhost:8080" + SERVER_CONTEXT);
    }
}
