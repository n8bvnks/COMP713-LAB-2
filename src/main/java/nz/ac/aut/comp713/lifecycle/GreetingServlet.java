package nz.ac.aut.comp713.lifecycle;
//Controller - this sends dopost to service

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


@WebServlet("/api/greeting")
public class GreetingServlet extends HttpServlet {


private GreetingService service =
        new GreetingService();



@Override
protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {


response.setContentType(
        "application/json"
);


String name = request.getParameter("name");


String message =
        service.createMessage(name);


response.getWriter()
.println(
        "{\"message\":\"" 
        + message 
        + "\"}"
);


}


}