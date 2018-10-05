package application.scene;

import application.database.Content;
import application.database.DB;
import application.database.Login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginScreen {

	/**
	 * This is the name of the website when logging in. This will show up in the adressbar.
	 */
	static String websiteLogin = "          https://www.contentmanagementsystem.com/login";
	
	/**
	 * This is the name of the website when registering. This will show up in the adressbar.
	 */
	static String websiteRegister = "          https://www.contentmanagementsystem.com/register";
	
	/**
	 * This is the height of which the content of this scene will be adjusted by.
	 */
	static short y = 300;
	
	/**
	 * Contains all the buttons and panes you can see in the login screen,
	 * and this is where a user would log in to the site or register a new user.
	 * 
	 * @param stage is the primaryStage passed along from Main
	 * @param w is the width of the maximised stage
	 * @param h is the height of the maximised stage
	 * 
	 * @author Niklas Sølvberg
	 */
	public static void showLoginScreen(Stage stage, double w, double h) {
		
		Pane root = new Pane();
		
		Pane topPane = new Pane();
		root.getChildren().add(topPane);
		
		TextField adressField = new TextField(websiteLogin);
		topPane.getChildren().add(adressField);
		adressField.setFocusTraversable(false);
		adressField.setEditable(false);
		
		
		
		Pane loginPane = new Pane();
		root.getChildren().add(loginPane);
		
		TextField loginUsernameField = new TextField();
		loginPane.getChildren().add(loginUsernameField);
		loginUsernameField.setPromptText("Username");
		
		PasswordField loginPasswordField = new PasswordField();
		loginPane.getChildren().add(loginPasswordField);
		loginPasswordField.setPromptText("Password");
		
		Label invalidLoginLabel = new Label("Invalid login!");
		loginPane.getChildren().add(invalidLoginLabel);
		invalidLoginLabel.setTextFill(Color.web("#ff0000"));
		invalidLoginLabel.setVisible(false);
		
		Button loginButton = new Button("Login");
		loginPane.getChildren().add(loginButton);
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if (Login.isValidLogin(loginUsernameField.getText(), loginPasswordField.getText())) {
					Login.login(loginUsernameField.getText());
					Home.showHome(stage, w, h);
				}
				else
					invalidLoginLabel.setVisible(true);
			}
		});
		loginButton.requestFocus();
		
		
		
		Pane registerPane = new Pane();
		root.getChildren().add(registerPane);
		
		TextField registerUsernameField = new TextField();
		registerPane.getChildren().add(registerUsernameField);
		registerUsernameField.setPromptText("Username");
		
		PasswordField registerPasswordField = new PasswordField();
		registerPane.getChildren().add(registerPasswordField);
		registerPasswordField.setPromptText("Password");
		
		PasswordField registerConfirmField = new PasswordField();
		registerPane.getChildren().add(registerConfirmField);
		registerConfirmField.setPromptText("Confirm password");
		
		Label invalidRegisterLabel = new Label("Invalid registration!");
		registerPane.getChildren().add(invalidRegisterLabel);
		invalidRegisterLabel.setTextFill(Color.web("#ff0000"));
		invalidRegisterLabel.setVisible(false);
		
		Button registerButton = new Button("Register");
		registerPane.getChildren().add(registerButton);
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent ae) {
				if (registerPasswordField.getText().equals(registerConfirmField.getText()) && registerPasswordField.getText().length() > 5 && registerUsernameField.getText().length() > 5) {
					try {
						Content.createUser(registerUsernameField.getText(), registerPasswordField.getText());
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle(null);
						alert.setHeaderText(null);
						alert.setContentText("User registration was successful!");
						alert.showAndWait();
						root.getChildren().remove(registerPane);
						root.getChildren().add(loginPane);
					}
					catch (IllegalArgumentException e) {
						invalidRegisterLabel.setVisible(true);
					}
				}
				else
					invalidRegisterLabel.setVisible(true);
			}
		});
		
		
		Hyperlink registerHyperlink = new Hyperlink("Don't have a user? Register here");
		loginPane.getChildren().add(registerHyperlink);
		registerHyperlink.setTextFill(Color.web("#0000ff"));
		registerHyperlink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				loginUsernameField.setText("");
				loginPasswordField.setText("");
				invalidLoginLabel.setVisible(false);
				registerHyperlink.setTextFill(Color.web("#0000ff"));
				root.getChildren().remove(loginPane);
				root.getChildren().add(registerPane);
				registerButton.requestFocus();
			}
		});
		registerHyperlink.setFocusTraversable(false);
		
		Hyperlink loginHyperlink = new Hyperlink("Already have a user? Log in here");
		registerPane.getChildren().add(loginHyperlink);
		loginHyperlink.setTextFill(Color.web("#0000ff"));
		loginHyperlink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				registerUsernameField.setText("");
				registerPasswordField.setText("");
				registerConfirmField.setText("");
				invalidRegisterLabel.setVisible(false);
				loginHyperlink.setTextFill(Color.web("#0000ff"));
				root.getChildren().remove(registerPane);
				root.getChildren().add(loginPane);
				loginButton.requestFocus();
				loginHyperlink.setLayoutX((w-loginHyperlink.getWidth())/2);
				loginHyperlink.setLayoutY(y+130);
			}
		});
		loginHyperlink.setFocusTraversable(false);
		
		
		
		
		
		Scene scene = new Scene(root, w, h);
		scene.getStylesheets().add("application/library/stylesheets/basic.css");
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					stage.close();
					DB.disconnect();
				}
			}
		});
		
		stage.setScene(scene);
		stage.show();
		
		
		
		
		
		loginPane.setLayoutX(0);
		loginPane.setLayoutY(h/12);
		loginPane.setPrefSize(2, h-(h/12));
		
		loginUsernameField.setLayoutX((w-160)/2);
		loginUsernameField.setLayoutY(y);
		loginUsernameField.setPrefSize(160, 25);
		
		loginPasswordField.setLayoutX((w-160)/2);
		loginPasswordField.setLayoutY(y+50);
		loginPasswordField.setPrefSize(160, 25);
		
		loginButton.setLayoutX((w-160)/2);
		loginButton.setLayoutY(y+100);
		loginButton.setPrefSize(160, 25);
		
		registerHyperlink.setLayoutX((w-registerHyperlink.getWidth())/2);
		registerHyperlink.setLayoutY(y+130);
		
		invalidLoginLabel.setLayoutX((w-invalidLoginLabel.getWidth())/2);
		invalidLoginLabel.setLayoutY(y+230);
		
		
		
		root.getChildren().remove(registerPane);
		
		registerPane.setLayoutX(0);
		registerPane.setLayoutY(h/12);
		registerPane.setPrefSize(2, h-(h/12));
		
		registerUsernameField.setLayoutX((w-160)/2);
		registerUsernameField.setLayoutY(y-50);
		registerUsernameField.setPrefSize(160, 25);
		
		registerPasswordField.setLayoutX((w-160)/2);
		registerPasswordField.setLayoutY(y);
		registerPasswordField.setPrefSize(160, 25);
		
		registerConfirmField.setLayoutX((w-160)/2);
		registerConfirmField.setLayoutY(y+50);
		registerConfirmField.setPrefSize(160, 25);
		
		registerButton.setLayoutX((w-160)/2);
		registerButton.setLayoutY(y+100);
		registerButton.setPrefSize(160, 25);
		
		loginHyperlink.setLayoutX((w-loginHyperlink.getWidth())/2);
		loginHyperlink.setLayoutY(y+130);
		
		invalidRegisterLabel.setLayoutX((w-invalidRegisterLabel.getWidth())/2);
		invalidRegisterLabel.setLayoutY(y+230);
		
		
		
		topPane.setLayoutX(0);
		topPane.setLayoutY(0);
		topPane.setPrefSize(w, h/12);
		
		adressField.setLayoutX(w/14);
		adressField.setLayoutY(((h/12)-adressField.getHeight())/2);
		adressField.setPrefWidth(w-(w/7));
		
		
		
	}
	
}
