package ua.maven.homework;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;

import javax.swing.ImageIcon;

import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {
	
	private Alert alert;
	private static Stage primaryStage;
	private static Scene scene;
	private static Scene scene2;
	public static String driver;
	public static String url;
	public static DBManager db;
    private static final Logger log = LoggerFactory.getLogger(MainApp.class);
    
    public static void registrationScene()	{
    	
    	GridPane grid = new GridPane();
    	grid.setVgap(5);
    	grid.setHgap(5);
    	grid.setPadding(new Insets(20,20,20,20));
    	
    	Label usernameLabel = new Label("Login: ");   	
    	Label passwordLabel = new Label("Password:");
    	Label emailLabel = new Label("E-mail: ");
    	grid.add(usernameLabel,0,0);
    	grid.add(passwordLabel, 0, 1);
    	grid.add(emailLabel, 0, 2);
    	
    	TextField userField = new TextField();
    	PasswordField passwordField = new PasswordField();
    	TextField emailField = new TextField();
    	grid.add(userField,1,0);
    	grid.add(passwordField, 1, 1);
    	grid.add(emailField, 1, 2);
    	
    	Button registerButton = new Button();
    	registerButton.setText("Register");
    	registerButton.setMaxSize(150, 150);
    	registerButton.setAlignment(Pos.CENTER_RIGHT);
    	registerButton.setOnMouseClicked((e)->{
    		HelloController con = new HelloController();
        	if (userField.getText().length() > 0 && passwordField.getText().length() > 0 && emailField.getText().length() > 5 )	{
        		if (emailField.getText().contains("@") && emailField.getText().contains("."))	{
        			if (MainApp.db.uniqueCheck(userField.getText(), emailField.getText()))	{
        				MainApp.db.newUserCreation(userField.getText(), passwordField.getText(), emailField.getText());
        					if (MainApp.db.isSuccess())	{
        						con.showAlert("Info", "Registration successful!", AlertType.INFORMATION);
        						log.info("new user created");
        					}
        			}
        		}
        	} else {
        		con.showAlert("Error", "Wrong Inputs", AlertType.ERROR);
        	}
    	});
    	grid.add(registerButton, 2, 0, 3, 3);
    	
    	Tooltip loginTip = new Tooltip();
    	loginTip.setText("Please enter login name\n" +
    	"at least one char in length\n");
    	userField.setTooltip(loginTip);
    	
    	Tooltip passwordTip = new Tooltip();
    	passwordTip.setText("Please enter password\n" +
    	"at least one char in length\n");
    	passwordField.setTooltip(passwordTip);
    	
    	Tooltip emailTip = new Tooltip();
    	emailTip.setText("Please enter your email.\n" +
    	"Should contain \"@\" and \".\" char\n");
    	emailField.setTooltip(emailTip);
    	
    	Button buttonCancel = new Button("Cancel");
    	buttonCancel.setAlignment(Pos.TOP_CENTER);
    	grid.add(buttonCancel, 1, 4);
    	buttonCancel.setOnMouseClicked((e)->{
    		primaryStage.setScene(scene);
    	});
    	
    	try {
			// QUESTION !!!!
    		//File faylik = new File(MainApp.class.getResource("/images/java.png").toString());
    		File file = new File(MainApp.class.getResource("/images/java.png").getFile());
    		
    		Image image = new Image(new FileInputStream(file));
			ImageView imageView = new ImageView(image);
			FadeTransition fadeOut = new FadeTransition(Duration.millis(2000), imageView);
			fadeOut.setToValue(1.0);
			fadeOut.setFromValue(0.0);
			fadeOut.setAutoReverse(true);
			fadeOut.setCycleCount(Timeline.INDEFINITE);
			fadeOut.play();
			grid.add(imageView, 4, 4);
		} catch (FileNotFoundException e1) {
			log.warn("java.png in /images is not found");
			e1.printStackTrace();
		}
    	
    	scene2 = new Scene(grid, 400, 220);
    	scene2.getStylesheets().add("/styles/styles.css");
    	primaryStage.setScene(scene2);
    	primaryStage.show();
    	
    }



	@Override
    public void start(Stage primaryStage) throws Exception {
    	
    	this.primaryStage = primaryStage;
        log.info("Starting Hello JavaFX and Maven demonstration application");

        String fxmlFile = "/fxml/hello.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
  
        log.debug("Showing JFX scene");
        scene = new Scene(rootNode, 400, 220);
        scene.getStylesheets().add("/styles/styles.css");
       
        primaryStage.setTitle("JavaFx Homework 1.0");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
	
    
    public static void main(String[] args) throws Exception {
		FileInputStream fis;
		Properties properties = new Properties();
		try {
			fis = new FileInputStream("config.txt");
			properties.load(fis);
			driver = properties.getProperty("driver_mysql");
			url = properties.getProperty("url_mysql");
			System.out.println("Extracted from properties file:");
			System.out.println("******************************");
			System.out.println("DriverMySQL-> " + driver + "\n" + "URL_MySQL-> " + url);
			System.out.println("******************************");
		} catch (IOException e) {
			System.err.println("Property file does not exist");
		}
		
		try	{
    	String path = MainApp.class.getResource("/media/my.mp3").toString();
    	Media media = new Media(path);
    	MediaPlayer mediaplayer = new MediaPlayer(media);
    	mediaplayer.play();
		} catch (Exception e)	{
			log.error("File \"my.mp3\" was not found");
		}
    	
		db = DBManager.newInstance();
		
        launch(args);

    }

}
