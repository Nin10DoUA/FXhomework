package ua.maven.homework;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloController	{

    private static final Logger log = LoggerFactory.getLogger(HelloController.class);
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    


    public void logIn() {
    	log.info("Trying to log in ...");
        String login = loginField.getText();
        String password = passwordField.getText();
        Alert alert = new Alert(AlertType.INFORMATION);        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog"); 
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText("Successfully logged in ...");
        alert.show();
    }
    
    public void registerUser()	{
    	log.info("Proceeding to register user page");
    	MainApp.registrationScene();
    	
    }

}
