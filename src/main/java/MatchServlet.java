import com.google.gson.Gson;
import dao.NumsDao;
import dao.SwipeDao;
import model.MatchListPOJO;
import model.NumsPOJO;
import model.SwipePOJO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "MatchServlet", value = "/MatchServlet")
public class MatchServlet extends HttpServlet {
    protected NumsDao numsDao;
    protected SwipeDao swipeDao;
    protected Gson gson;

    @Override
    public void init() throws ServletException {
        numsDao = new NumsDao();
        swipeDao = new SwipeDao();
        gson = new Gson();
    }

    public void rep(ResponseMsg message, HttpServletResponse response, String s) throws IOException {
        message.setMessage(s);
        response.getOutputStream().print(gson.toJson(message));
        if (s.equals("Not found") || s.equals("User Not found")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (s.equals("Invalid inputs")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }


    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");


        String urlPath = request.getRequestURI();

        if (urlPath == null) {
            rep(new ResponseMsg(), response, "Not found");
            return;
        }
        String[] urlParts = urlPath.split("/");
        ResponseMsg message = new ResponseMsg();



        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {

                sb.append(s);

            }

            if (urlParts.length < 4 ){

                rep(message, response, "Not found");
            }
            else if(urlParts[2].equals("matches") && urlParts[3].matches("\\d+")){

                List<Integer> matches = swipeDao.getSwipePOJOBySwiper(urlParts[3]);
                MatchListPOJO matchListPOJO = new MatchListPOJO(matches);
                if (matches.size() != 0){

                    response.getOutputStream().print(gson.toJson(matchListPOJO));


                }
                else{
                    rep(message, response, "User Not found");
                }
            }
            else if(urlParts[2].equals("stats") && urlParts[3].matches("\\d+")){

//                NumsPOJO numsPOJO = numsDao.getNumsPOJOBySwiper(Integer.parseInt(urlParts[3]));
                NumsPOJO numsPOJO = numsDao.getSwipeRecordySwiper(Integer.parseInt(urlParts[3]));
                if(numsPOJO != null){
                    response.getOutputStream().print(gson.toJson(numsPOJO));

                }
                else{
                    rep(message, response, "User Not found");
                }

            }
            else{
                rep(message, response, "Invalid inputs");

            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
