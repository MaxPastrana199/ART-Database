package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.KunstwerkController.KunstwerkData;
import application.MuseumController.MuseumData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SammlungController implements Initializable{
	
	private static Connection con;
	private static Statement stat;
    private PreparedStatement prep;
	private ObservableList<SammlungData> data;
	private ObservableList<DetailData> detaildata;

    
    @FXML 
    private TableView table;
	@FXML
	private TableColumn column1;
	@FXML
	private TableColumn column2;
	
    @FXML 
    private TableView detailtable;
	@FXML
	private TableColumn det1;
	@FXML
	private TableColumn det2;
	@FXML
	private TableColumn det3;
	@FXML
	private TableColumn det4;
	
	@FXML
	private TextField suche;
	@FXML
	private Label userLbl;
	
	
	
	public void GetUser(String user) {
		// TODO Auto-generated method stub
		userLbl.setText(user);
		
	}
	
	
	public void BackToBenutzerMenue(ActionEvent event) {
		
		try {
			((Node)event.getSource()).getScene().getWindow().hide();
			
			Stage primaryStage = new Stage();
			FXMLLoader loader = new FXMLLoader();			
			Pane root = loader.load(getClass().getResource("/application/BenutzerMenue.fxml").openStream());
			BenutzerMenueController menueController = (BenutzerMenueController)loader.getController();
			menueController.GetUser(userLbl.getText());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e){
			// TODO: handle exception
		}		
	}
	
	public void sucheAbbrechen(ActionEvent event){
        suche.setText(null);
		initialize(null,null);
	}
	
	public void searchSammlung(ActionEvent event){

   	 	try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            prep =con.prepareStatement("select * from Sammlung where name = ?");
	        prep.setString(1, suche.getText());
	        ResultSet rs = prep.executeQuery();

	        while (rs.next()) {
                data.add(new SammlungData(rs.getString("sammlung_id"), rs.getString("name")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("sammlung_id"));
            column2.setCellValueFactory(new PropertyValueFactory("name"));
            
            table.setItems(null);
            table.setItems(data);
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   }
	
	public void detailsSammlung(ActionEvent event){

   	 	try {
   	 		detailtable.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            detaildata = FXCollections.observableArrayList();
            prep =con.prepareStatement("SELECT Kunstwerk.kunstwerkname, Zeitraum_Sammlung.anfangsdatum,Zeitraum_Sammlung.enddatum, Gebaeude.name FROM Kunstwerk JOIN Gehoert_zu_Sammlung ON Kunstwerk.kunstwerk_id=Gehoert_zu_Sammlung.kunstwerk_id JOIN Sammlung ON Gehoert_zu_Sammlung.sammlung_id=Sammlung.sammlung_id JOIN Zeitraum_Sammlung  ON Sammlung.sammlung_id=Zeitraum_Sammlung.sammlung_id JOIN Gebaeude ON Zeitraum_Sammlung.gebaeude_id=Gebaeude.gebaeude_id WHERE Sammlung.name=?");
	        prep.setString(1, suche.getText());
	        ResultSet rs = prep.executeQuery();

            while (rs.next()) {
            	detaildata.add(new DetailData(rs.getString("kunstwerkname"), rs.getString("anfangsdatum"), rs.getString("enddatum"), rs.getString("name")));
            }
            det1.setCellValueFactory(new PropertyValueFactory("kunstwerkname"));
            det2.setCellValueFactory(new PropertyValueFactory("anfangsdatum"));
            det3.setCellValueFactory(new PropertyValueFactory("enddatum"));
            det4.setCellValueFactory(new PropertyValueFactory("name"));

            
            detailtable.setItems(null);
            detailtable.setItems(detaildata);
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("select * from Sammlung");
            while (rs.next()) {
                data.add(new SammlungData(rs.getString("sammlung_id"), rs.getString("name")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("sammlung_id"));
            column2.setCellValueFactory(new PropertyValueFactory("name"));


            table.setItems(null);
            table.setItems(data);
            
            table.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            suche.setText((String) column2.getCellData(table.getSelectionModel().getSelectedIndex()));

                        }
                    }
                }
            });
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
        }
		
	}
	
	
	
	public static class SammlungData {

        private StringProperty sammlung_id;
        private StringProperty name;



        private SammlungData(String sammlung_id, String name) {
            this.sammlung_id = new SimpleStringProperty(sammlung_id);
            this.name = new SimpleStringProperty(name);

        }

        public StringProperty sammlung_idProperty() {
            return sammlung_id;
        }
        public StringProperty nameProperty() {
            return name;
        }
    }
	
	public static class DetailData {

        private StringProperty kunstwerkname;
        private StringProperty anfangsdatum;
        private StringProperty enddatum;
        private StringProperty name;
        


        private DetailData(String kunstwerkname,String anfangsdatum,String enddatum,String name) {
            this.kunstwerkname = new SimpleStringProperty(kunstwerkname);
            this.anfangsdatum = new SimpleStringProperty(anfangsdatum);
            this.enddatum = new SimpleStringProperty(enddatum);
            this.name = new SimpleStringProperty(name);
            
        }

        public StringProperty kunstwerknameProperty() {
            return kunstwerkname;
        }
        public StringProperty anfangsdatumProperty() {
            return anfangsdatum;
        }
        public StringProperty enddatumProperty() {
            return enddatum;
        }
        public StringProperty nameProperty() {
            return name;
        }
    }
	
	
	
	
	

}
