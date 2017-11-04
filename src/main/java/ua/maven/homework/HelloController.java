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
	
	private Alert alert;
    private static final Logger log = LoggerFactory.getLogger(HelloController.class);
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    


    public void logIn() {
    	log.info("Trying to log in ...");
        String login = loginField.getText();
        String password = passwordField.getText();
        if(login.length() > 0 && password.length() > 0)	{
        	if(MainApp.db.authCheck(login, password))	{
        		showAlert("Info", "Successfull login!", AlertType.INFORMATION);
        		log.info("Log in success!");
        	} else {
        		showAlert("Warning","login and password do not match", AlertType.WARNING);
        		log.warn("wrong login or password");
        	}
        } else {
        	showAlert("Error","Please enter your credentials", AlertType.ERROR);
        	log.error("please specify all fields");
        }
        
        
    }
    
    public void registerUser()	{
    	log.info("Proceeding to register user page");
    	MainApp.registrationScene();
    	
    }
    
    public void showAlert (String title, String content, Alert.AlertType alertType)	{
        alert = new Alert(alertType);        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/styles.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog"); 
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }

}
