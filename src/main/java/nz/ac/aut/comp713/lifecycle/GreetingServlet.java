package nz.ac.aut.comp713.lifecycle;
import jakarta.json.Json;
import jakarta.json.JsonException;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/api/greeting")
public class GreetingServlet
extends HttpServlet {
private final GreetingService service =
new GreetingService();
@Override
protected void doPost(
HttpServletRequest request,
HttpServletResponse response)
throws IOException {
response.setContentType(
"application/json;charset=UTF-8"
);
System.out.println(
"3. Servlet handler selected"
);
try (
JsonReader reader =
Json.createReader(
request.getReader()
)
) {
JsonObject input =
reader.readObject();

// TODO 1:
// Read the "name" value,
// use an empty default,
// and remove surrounding spaces.
String name = input.getString("name","").trim();
System.out.println(
"4. JSON input read"
);

// TODO 2:
// Reject a missing or blank name.
// At present, validation is disabled.
if(name.isBlank()){
        writeError(response,
                        HttpServletResponse.SC_BAD_REQUEST,
                "Name is required");
                return;
}
// TODO 3:
// Call GreetingService.
String message =
service.createMessage(name);
int length = service.calculateLength(name);
JsonObject output =
Json.createObjectBuilder()
.add("message", message)
.add("length", length)
.build();


// TODO 4:
// Select the correct success status.
response.setStatus(
HttpServletResponse
.SC_OK
);
try (
JsonWriter writer =
Json.createWriter(
response.getWriter()
)
) {
writer.writeObject(output);
}
System.out.println(
"6. JSON response written"
);
} catch (JsonException error) {
writeError(
response,
HttpServletResponse
.SC_BAD_REQUEST,
"Request body must contain valid JSON."
);
}
}
private void writeError(
HttpServletResponse response,
int status,
String message)
throws IOException {
response.setStatus(status);
JsonObject error =
Json.createObjectBuilder()
.add("error", message)
.build();
try (
JsonWriter writer =
Json.createWriter(
response.getWriter()
)
) {
writer.writeObject(error);
}
}

}