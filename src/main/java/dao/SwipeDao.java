package dao;

import model.SwipePOJO;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SwipeDao {
    private static BasicDataSource dataSource;
    // Single pattern: instantiation is limited to one object.
    private static SwipeDao instance = null;

    public SwipeDao() {
        dataSource = DBCPDataSource.getDataSource();
    }

    public static SwipeDao getInstance() {
        if (instance == null) {
            instance = new SwipeDao();
        }
        return instance;
    }

    //create a SwipePOJO record
    public void createSwipe(SwipePOJO swipePOJO) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        String insertQueryStatement = "INSERT INTO Swipe (Swipee, Swiper) " +
                "VALUES (?,?,?)";
        try {
            conn = dataSource.getConnection();
            preparedStatement = conn.prepareStatement(insertQueryStatement);
            preparedStatement.setInt(1, swipePOJO.getSwipee());
            preparedStatement.setInt(2, swipePOJO.getSwiper());

            
            // execute insert SQL statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    /**
     * Get the SwipePOJO records by fetching them from your MySQL instance.
     * This runs a SELECT statement and returns a single SwipePOJO instance.
     */
    public List<Integer> getSwipePOJOBySwiper(String swiper) throws SQLException {
        List<Integer> swipePOJOs = new ArrayList<Integer>();
        String selectSwipePOJO = "SELECT Id, Swipee, Swiper FROM Swipe WHERE Swiper=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = dataSource.getConnection();
            selectStmt = connection.prepareStatement(selectSwipePOJO);
            selectStmt.setString(1, swiper);

            results = selectStmt.executeQuery();

            while (results.next()) {

                int swipee = results.getInt("Swipee");

                swipePOJOs.add(swipee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (results != null) {
                results.close();
            }
        }
        return swipePOJOs;
    }
}
