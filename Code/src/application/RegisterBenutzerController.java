package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegisterBenutzerController implements Initializable {
	
	 private static Connection con;
	 private static Statement stat;
	 private PreparedStatement prep;


	 @FXML
	 private Label nullValidationLabel;
	 @FXML
	 private TextField id;
	 @FXML
	 private TextField benutzername;
	 @FXML
	 private TextField vorname;
	 @FXML
	 private TextField name;
	 @FXML
	 private TextField land;
	 @FXML
	 private TextField stadt;
	 @FXML
	 private TextField strasse;
	 @FXML
	 private TextField nummer;
	 @FXML
	 private TextField mail;
	 @FXML
	 private TextField passwort;
	 


	public void SignOut(ActionEvent event) {
		
		try {
			// versteckt Login-Fenster
			((Node)event.getSource()).getScene().getWindow().hide();
			
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
	
	public void sucheAbbrechen(ActionEvent event){

        benutzername.setText(null);
        vorname.setText(null);
        name.setText(null);
        land.setText(null);
        stadt.setText(null);
        strasse.setText(null);
        nummer.setText(null);
        mail.setText(null);
        passwort.setText(null);
	}
	
	public void saveData(ActionEvent event){
		if(benutzername.getText().equals("")==true||vorname.getText().equals("")==true||name.getText().equals("")==true||land.getText().equals("")==true||stadt.getText().equals("")==true||strasse.getText().equals("")==true||nummer.getText().equals("")==true||mail.getText().equals("")==true||passwort.getText().equals("")==true){
			nullValidationLabel.setText("Bitte alles ausfuellen");

        }else{
        	try {
        		String query = "INSERT INTO Benutzer (benutzername,vorname,name,land,stadt,strasse,hausnummer,email,passwort) VALUES(?,?,?,?,?,?,?,?,?)";
    			prep = con.prepareStatement(query);	
    			nullValidationLabel.setText("");
                prep.setString(1, benutzername.getText());
                prep.setString(2, vorname.getText());
                prep.setString(3, name.getText());
                prep.setString(4, land.getText());
                prep.setString(5, stadt.getText());
                prep.setString(6, strasse.getText());
                prep.setString(7, nummer.getText());
                prep.setString(8, mail.getText());
                prep.setString(9, passwort.getText());
                prep.execute();
    		} catch (SQLException e) {
    		    
    		        }
        	}
	
       
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        try {

            con = SqliteConnection.Connector();
            stat = con.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
		// TODO Auto-generated method stub
		
	}
	
	// Test SQL Querys
	
	
	/*
	String query = "INSERT INTO Benutzer (id,benutzername,vorname,name,land,stadt,strasse,hausnummer,email,passwort) VALUES(?,?,?,?,?,?,?,?,?,?);";
	prep = con.prepareStatement(query);
    
	prep.setString(1, benutzername.getText());
    prep.setString(2, vorname.getText());
    prep.setString(3, name.getText());
    prep.setString(4, land.getText());
    prep.setString(5, stadt.getText());
    prep.setString(6, strasse.getText());
    prep.setString(7, nummer.getText());
    prep.setString(8, mail.getText());
    prep.setString(9, passwort.getText());
    prep.execute();
    prep.close();
	
    prep.setString(1, id.getText());
    prep.setString(2, benutzername.getText());
    prep.setString(3, vorname.getText());
    prep.setString(4, name.getText());
    prep.setString(5, land.getText());
    prep.setString(6, stadt.getText());
    prep.setString(7, strasse.getText());
    prep.setString(8, nummer.getText());
    prep.setString(9, mail.getText());
    prep.setString(10, passwort.getText());
    prep.execute();
    prep.close();
	*/

}
