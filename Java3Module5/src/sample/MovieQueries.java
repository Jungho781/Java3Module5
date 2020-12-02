package sample;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieQueries {

    private static final String URL = "jdbc:derby:Movies;create=true";

    private Connection connection;
    private PreparedStatement selectAllMovies;
    private PreparedStatement selectMovieByID;
    private PreparedStatement selectMovieByRating;
    private PreparedStatement insertNewMovie;

    public MovieQueries() {
        try {
            System.out.println("Connecting to database URL: " + URL);
            connection = DriverManager.getConnection(URL);

            selectAllMovies = connection.prepareStatement(
                    "Select * From Movies BY ID, Name");

            selectMovieByID=connection.prepareStatement(
                    "SELECT * FROM Movies WHERE ID LIKE ?"+"ORDER BY ID, Name");

            selectMovieByRating=connection.prepareStatement(
                    "SELECT * FROM Movies WHERE Rating LIKE ?"+"ORDER BY Rating, Name");

            insertNewMovie=connection.prepareStatement(
                    "INSERT INTO Movies "+"(ID, Name, Rating, Description)"+
                    "Values(?, ?, ?, ?)");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public List<Movie> getAllMovies() {
        try (ResultSet resultSet = selectAllMovies.executeQuery()) {
            List<Movie> results = new ArrayList<Movie>();

            while (resultSet.next()) {
                results.add(new Movie(
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Rating"),
                        resultSet.getString("Description")));
            }
            return results;
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public List<Movie> getMovieByID(Integer ID){
        try {
            selectMovieByID.setInt(1,ID);
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
        try (ResultSet resultSet=selectMovieByID.executeQuery()){
            List<Movie>results=new ArrayList<>();

            while (resultSet.next()){
                results.add(new Movie(
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Rating"),
                        resultSet.getString("Description")));
            }
            return results;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }

    public List<Movie> getMovieByRating(Integer Rating){
        try {
            selectMovieByRating.setInt(1,Rating);
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
        try (ResultSet resultSet=selectMovieByRating.executeQuery()){
            List<Movie> results=new ArrayList<>();

            while (resultSet.next()){
                results.add(new Movie(
                        resultSet.getInt("ID"),
                        resultSet.getString("Name"),
                        resultSet.getInt("Rating"),
                        resultSet.getString("Description")));
            }
            return results;
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return null;
    }

    public int addMovie(String name,Integer rating,String description){
        try {
            insertNewMovie.setString(1,name);
            insertNewMovie.setInt(2,rating);
            insertNewMovie.setString(3,description);

            return insertNewMovie.executeUpdate();
        }
        catch (SQLException sqlException){
            sqlException.printStackTrace();
            return 0;
        }
    }

    public void close() {
        try {
            connection.close();
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
