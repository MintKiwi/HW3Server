import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "index", value = "/ErrorServlet")
public class ErrorServlet extends HttpServlet {




    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ResponseMsg message = new ResponseMsg();


        Gson gson = new Gson();
        JsonParserServlet js = new JsonParserServlet();
        js.rep(message, response, "Not found");
        return;

    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request,response);

    }
}
