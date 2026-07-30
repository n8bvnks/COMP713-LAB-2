package nz.ac.aut.comp713.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
public class HttpInspectorServer {
public static void main(String[] args) throws IOException {
int port = 8081;
try (ServerSocket serverSocket = new ServerSocket(port)) {
System.out.println(
"HTTP inspector listening at http://localhost:" + port
);
while (true) {
try (
Socket client = serverSocket.accept();
BufferedReader input = new BufferedReader(
new InputStreamReader(
client.getInputStream(),
StandardCharsets.UTF_8
)
);
BufferedWriter output = new BufferedWriter(
new OutputStreamWriter(
client.getOutputStream(),
StandardCharsets.UTF_8
)
)
) {
System.out.println("\n--- HTTP request received ---");
String line;
while ((line = input.readLine()) != null
&& !line.isEmpty()) {
System.out.println(line);
}
String body = """
<!doctype html>
<html>
<body>
<h1>Request received</h1>
COMP713 Distributed and Mobile Systems
Lab 3-4 | End-to-End Web Request Lifecycle
<p>Check the Java terminal for the raw HTTP request.</p>
</body>
</html>
""";
byte[] bodyBytes =
body.getBytes(StandardCharsets.UTF_8);
output.write("HTTP/1.1 200 OK\r\n");
output.write(
"Content-Type: text/html; charset=UTF-8\r\n"
);
output.write(
"Content-Length: " + bodyBytes.length + "\r\n"
);
output.write("Connection: close\r\n");
output.write("\r\n");
output.write(body);
output.flush();
}
}
}
}
}