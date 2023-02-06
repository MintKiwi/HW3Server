
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "JsonParserServlet", value = "/JsonParserServlet")
public class JsonParserServlet extends HttpServlet {
    private Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");


        String urlPath = request.getPathInfo();

        if (urlPath == null) {
            rep(new ResponseMsg(), response, "Not found");
            return;
        }
        String[] urlParts = urlPath.split("/");




        try {
            StringBuilder sb = new StringBuilder();
            String s;
            while ((s = request.getReader().readLine()) != null) {

                sb.append(s);

            }
            SwipeDetails swipeDetails = (SwipeDetails) gson.fromJson(sb.toString(), SwipeDetails.class);

//            SwipeDetails swipeDetails = JSON.parseObject(sb.toString(), SwipeDetails.class);
            ResponseMsg message = new ResponseMsg();

            String swiper = swipeDetails.getSwiper();
            String swipee = swipeDetails.getSwipee();
            String comment = swipeDetails.getComment();
            System.out.println(swipee);
            System.out.println(swiper);
            System.out.println(comment);

            if (urlParts.length < 3 || (!urlParts[2].equals("left") && !urlParts[2].equals("right"))) {
                rep(message, response, "Not found");

                return;

            } else if (!swiper.matches("\\d+") || !swipee.matches("\\d+") || comment.length() != 256) {

                rep(message, response, "Invalid inputs");
                return;

            } else if (!isValid(swiper, swipee)) {
                rep(message, response, "Invalid inputs");
                return;
            } else if (swipeDetails.getSwipee() != null && swipeDetails.getComment() != null && swipeDetails.getSwiper() != null) {

                response.getOutputStream().print(gson.toJson(swipeDetails));


                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getOutputStream().flush();
                return;


            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ResponseMsg message = new ResponseMsg();
            rep(message, response, "Error");


            response.getOutputStream().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void rep(ResponseMsg message, HttpServletResponse response, String s) throws IOException {
        message.setMessage(s);
        response.getOutputStream().print(gson.toJson(message));
        if (s.equals("Not found")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else if (s.equals("Invalid inputs")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }


    }

    protected boolean isValid(String swiper, String swipee) {
        if(swiper.length() > 5 || swipee.length() > 7) return false;
        long swiperInt = Integer.valueOf(swiper);
        long swipeeInt = Integer.valueOf(swipee);
        return (swiperInt >= 1 && swiperInt <= 5000 && swipeeInt >= 1 && swipeeInt <= 1000000);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.getWriter().write("It works!");

    }
}
