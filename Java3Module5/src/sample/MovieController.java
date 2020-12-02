package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class MovieController {
    @FXML private ListView<Movie> listView;
    @FXML private TextField nameTextField;
    @FXML private TextField ratingTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField findByRatingTextField;

    private final MovieQueries movieQueries=new MovieQueries();

    private final ObservableList<Movie>movieList= FXCollections.observableArrayList();

    public void initialize(){
        listView.setItems(movieList);
        getAllEntries();

        listView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue,oldValue,newValue)->{
                    displayMovie(newValue);
                }
        );
    }

    private void getAllEntries(){
        movieList.setAll(movieQueries.getAllMovies());
        selectFirstEntry();
    }

    private void selectFirstEntry(){listView.getSelectionModel().selectFirst();}

    private void displayMovie(Movie movie){
        if(movie!=null){
            nameTextField.setText(movie.getName());
            ratingTextField.setText(String.valueOf(movie.getRating()));
            descriptionTextField.setText(movie.getDescription());
        }
        else{
            nameTextField.clear();
            ratingTextField.clear();
            descriptionTextField.clear();
        }
    }

    @FXML
    void addEntryButtonPressed(javafx.event.ActionEvent event){
        int result=movieQueries.addMovie(
                nameTextField.getText(),
                Integer.valueOf(ratingTextField.getText()),descriptionTextField.getText());
        if(result==1){
            displayAlert(Alert.AlertType.INFORMATION,"Entry added",
                    "New entry successfully added.");
        } else {
            displayAlert(Alert.AlertType.ERROR,"Entry Not Added",
                    "Unable to add entry.");
        }
        getAllEntries();
    }

    @FXML
    void findButtonPressed(javafx.event.ActionEvent event){
        List<Movie>movies=movieQueries.getMovieByRating(
                Integer.valueOf(findByRatingTextField.getText()+"%"));
        if(movies.size()>0){
            movieList.setAll(movies);
            selectFirstEntry();
        }
        else {
            displayAlert(Alert.AlertType.INFORMATION,"Rating Not Found",
                    "There are no entries with specified rating.");
        }
    }

    @FXML
    void browseAllButtonPressed(javafx.event.ActionEvent event){getAllEntries();}

    private void displayAlert(
            Alert.AlertType type,String title,String message){
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
