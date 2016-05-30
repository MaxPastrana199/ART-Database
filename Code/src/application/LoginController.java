package application;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.MuseumController.MuseumData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController implements Initializable{
	public LoginModel loginModel = new LoginModel();
	
	  @FXML
	  private Label isConnected;
	  @FXML
	  private Label benutzerid;
	  
	  @FXML
	  private TextField txtBenutzername;
	  
	  @FXML
	  private TextField txtPasswort;
	  
	  @FXML
	  private TextField txtKuenstlerBenutzername;
	  
	  @FXML
	  private TextField txtKuenstlerPasswort;
	  
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		if(loginModel.isDbConnected()) {
			isConnected.setText("Verbunden");
		} else{
			isConnected.setText("Datenbank-Fehler");
		}
	}
	
	// Login Benutzer
	// Ueberprueft Benutzername und Passwort
	public void LoginBenutzer (ActionEvent event) {
		try {
			if (loginModel.isLoginBenutzer(txtBenutzername.getText(), txtPasswort.getText())){
				isConnected.setText("Login korrekt");
				
				// versteckt Login-Fenster
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				
				Pane root = loader.load(getClass().getResource("/application/BenutzerMenue.fxml").openStream());
				BenutzerMenueController menueController = (BenutzerMenueController)loader.getController();
				menueController.GetUser(txtBenutzername.getText());
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
				
				
				
			} else {
				isConnected.setText("Falsches Passwort");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			// catch fuer Menue.fxml
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Login Kuenstler
	public void LoginKuenstler (ActionEvent event) {
		try {
			if (loginModel.isLoginKuenstler(txtKuenstlerBenutzername.getText(), txtKuenstlerPasswort.getText())){
				isConnected.setText("Login korrekt");	
				
				// versteckt Login-Fenster
				((Node)event.getSource()).getScene().getWindow().hide();
				
				Stage primaryStage = new Stage();
				FXMLLoader loader = new FXMLLoader();
				
				Pane root = loader.load(getClass().getResource("/application/KuenstlerMenue.fxml").openStream());
				KuenstlerMenueController kuenstlermenueController = (KuenstlerMenueController)loader.getController();
				kuenstlermenueController.GetUser(txtKuenstlerBenutzername.getText());
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
				
			} else {
				isConnected.setText("Falsches Passwort");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void RegisterBenutzer (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/RegisterBenutzer.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void RegisterKuenstler (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/RegisterKuenstler.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
