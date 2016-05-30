package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.SplitPane;


public class BenutzerMenueController implements Initializable {
	
	public LoginModel loginModel = new LoginModel();
    @FXML
    private Label isConnected;
	
	 private static Connection con;
	 private PreparedStatement prep;
	 private static Statement stat;
	
	@FXML
	private Label userLbl;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {

			if(loginModel.isDbConnected()){
	            isConnected.setText("Datenbank verbunden");
	        }else{
	            isConnected.setText("Datenbank-Fehler");
	        }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
		
	}
	
	
	public void GetUser(String user) {
		// TODO Auto-generated method stub
		userLbl.setText(user);
		}
	
	public void SignOut(ActionEvent event) {
		
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			// BenutzerMenue.fxml oefnnen
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			Pane root = loader.load(getClass().getResource("/application/Login.fxml").openStream());
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e){
			// TODO: handle exception
		}		
	}
	
	public void ShowKuenstler (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			SplitPane root = loader.load(getClass().getResource("/application/Kuenstler.fxml").openStream());
			KuenstlerController kuenstlerController = (KuenstlerController)loader.getController();
			kuenstlerController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void ShowKunstwerk (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			SplitPane root = loader.load(getClass().getResource("/application/Kunstwerk.fxml").openStream());
			KunstwerkController kunstwerkController = (KunstwerkController)loader.getController();
			kunstwerkController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void ShowSammlung (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			SplitPane root = loader.load(getClass().getResource("/application/Sammlung.fxml").openStream());
			SammlungController sammlungController = (SammlungController)loader.getController();
			sammlungController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void ShowMuseum (ActionEvent event) {
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader();
			
			SplitPane root = loader.load(getClass().getResource("/application/Museum.fxml").openStream());
			MuseumController museumController = (MuseumController)loader.getController();
			museumController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
