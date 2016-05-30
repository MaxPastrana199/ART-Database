package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.KuenstlerController.KuenstlerData;
import application.KunstwerkController.KommentarData;
import application.SammlungController.SammlungData;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MuseumController implements Initializable{
	
	private static Connection con;
	private static Statement stat;
    private PreparedStatement prep;
	private ObservableList<MuseumData> data;
	private ObservableList<KunstwerkData> kunstwerkdata;
	private ObservableList<SammlungData> sammlungdata;
	private ObservableList<KommentarData> kommentardata;

    
    @FXML 
    private TableView table;
	@FXML
	private TableColumn column1;
	@FXML
	private TableColumn column2;
	@FXML
	private TableColumn column3;
	@FXML
	private TableColumn column4;
	@FXML
	private TableColumn column5;
	@FXML
	private TableColumn column6;
	@FXML
	private TableColumn column7;
	@FXML
	private TableColumn column8;
	@FXML
	private TableColumn column9;
	
    @FXML 
    private TableView kunstwerktable;
	@FXML
	private TableColumn kw1;
	@FXML
	private TableColumn kw2;
	@FXML
	private TableColumn kw3;
	
    @FXML 
    private TableView sammlungtable;
	@FXML
	private TableColumn sam1;
	@FXML
	private TableColumn sam2;
	@FXML
	private TableColumn sam3;
	
	@FXML
	private TableView kommentartable;
	@FXML
	private TableColumn kom1;

	
	@FXML
	private TextArea kommentar;
	
	@FXML
	private TextField suche;
	@FXML
	private Label userLbl;
	@FXML
	private Label museumid;
	@FXML
	private TextField filter;
	
	
	
	
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
	
	public void searchMuseum(ActionEvent event){

   	 	try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            prep =con.prepareStatement("select Gebaeude.land,Gebaeude.stadt,Gebaeude.strasse,Gebaeude.hausnummer,Gebaeude.name,Oeffnungszeit.wochentag,Oeffnungszeit.von,Oeffnungszeit.bis,Gebaeude.gebaeude_id FROM Gebaeude JOIN Museum ON Gebaeude.gebaeude_id=Museum.museum_id JOIN Hat_Museum ON Gebaeude.gebaeude_id=Hat_Museum.museum_id JOIN Oeffnungszeit ON Hat_Museum.oeffnungszeit_id=Oeffnungszeit.oeffnungszeit_id where Gebaeude.name = ?");
	        prep.setString(1, suche.getText());
	        ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                data.add(new MuseumData(rs.getString("land"), rs.getString("stadt"), rs.getString("strasse"), rs.getString("hausnummer"), rs.getString("name"), rs.getString("wochentag"), rs.getString("von"), rs.getString("bis"), rs.getString("gebaeude_id")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("land"));
            column2.setCellValueFactory(new PropertyValueFactory("stadt"));
            column3.setCellValueFactory(new PropertyValueFactory("strasse"));
            column4.setCellValueFactory(new PropertyValueFactory("hausnummer"));
            column5.setCellValueFactory(new PropertyValueFactory("name"));
            column6.setCellValueFactory(new PropertyValueFactory("wochentag"));
            column7.setCellValueFactory(new PropertyValueFactory("von"));
            column8.setCellValueFactory(new PropertyValueFactory("bis"));
            column9.setCellValueFactory(new PropertyValueFactory("gebaeude_id"));
            
            table.setItems(null);
            table.setItems(data);
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   }
	
	public void filterStaedte(ActionEvent event){

   	 	try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            prep =con.prepareStatement("select Gebaeude.land,Gebaeude.stadt,Gebaeude.strasse,Gebaeude.hausnummer,Gebaeude.name,Oeffnungszeit.wochentag,Oeffnungszeit.von,Oeffnungszeit.bis,Gebaeude.gebaeude_id FROM Gebaeude JOIN Museum ON Gebaeude.gebaeude_id=Museum.museum_id JOIN Hat_Museum ON Gebaeude.gebaeude_id=Hat_Museum.museum_id JOIN Oeffnungszeit ON Hat_Museum.oeffnungszeit_id=Oeffnungszeit.oeffnungszeit_id where Gebaeude.stadt = ?");
	        prep.setString(1, filter.getText());
	        ResultSet rs = prep.executeQuery();

            while (rs.next()) {
                data.add(new MuseumData(rs.getString("land"), rs.getString("stadt"), rs.getString("strasse"), rs.getString("hausnummer"), rs.getString("name"), rs.getString("wochentag"), rs.getString("von"), rs.getString("bis"), rs.getString("gebaeude_id")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("land"));
            column2.setCellValueFactory(new PropertyValueFactory("stadt"));
            column3.setCellValueFactory(new PropertyValueFactory("strasse"));
            column4.setCellValueFactory(new PropertyValueFactory("hausnummer"));
            column5.setCellValueFactory(new PropertyValueFactory("name"));
            column6.setCellValueFactory(new PropertyValueFactory("wochentag"));
            column7.setCellValueFactory(new PropertyValueFactory("von"));
            column8.setCellValueFactory(new PropertyValueFactory("bis"));
            column9.setCellValueFactory(new PropertyValueFactory("gebaeude_id"));
            
            table.setItems(null);
            table.setItems(data);
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   }

	public void detailsMuseum(ActionEvent event){

   	 try {
        	kunstwerktable.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            kunstwerkdata = FXCollections.observableArrayList();
            prep =con.prepareStatement("Select Kunstwerk.kunstwerkname, Zeitraum_ausgestellt.anfangsdatum, Zeitraum_ausgestellt.enddatum FROM Kunstwerk JOIN Zeitraum_ausgestellt ON Kunstwerk.kunstwerk_id=Zeitraum_ausgestellt.kunstwerk_id JOIN Gebaeude ON Zeitraum_ausgestellt.museum_id=Gebaeude.gebaeude_id WHERE Gebaeude.name=?");
	        prep.setString(1, suche.getText());
	        ResultSet rs = prep.executeQuery();

            while (rs.next()) {
            	kunstwerkdata.add(new KunstwerkData(rs.getString("kunstwerkname"),rs.getString("anfangsdatum"),rs.getString("enddatum")));
            }
            kw1.setCellValueFactory(new PropertyValueFactory("kunstwerkname"));
            kw2.setCellValueFactory(new PropertyValueFactory("anfangsdatum"));
            kw3.setCellValueFactory(new PropertyValueFactory("enddatum"));

            kunstwerktable.setItems(null);
            kunstwerktable.setItems(kunstwerkdata);
  
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
   	 
   	 try {
     	sammlungtable.setEditable(true);

         con = SqliteConnection.Connector();
         stat = con.createStatement();

         sammlungdata = FXCollections.observableArrayList();
         prep =con.prepareStatement("SELECT Sammlung.name, Zeitraum_Sammlung.anfangsdatum, Zeitraum_Sammlung.enddatum FROM Sammlung JOIN Zeitraum_Sammlung ON Sammlung.sammlung_id=Zeitraum_Sammlung.sammlung_id JOIN Gebaeude ON Zeitraum_Sammlung.gebaeude_id=Gebaeude.gebaeude_id JOIN Museum ON Gebaeude.gebaeude_id=Museum.museum_id WHERE Gebaeude.name=?");
	        prep.setString(1, suche.getText());
	        ResultSet rs = prep.executeQuery();

         while (rs.next()) {
        	 sammlungdata.add(new SammlungData(rs.getString("name"),rs.getString("anfangsdatum"),rs.getString("enddatum")));
         }
         sam1.setCellValueFactory(new PropertyValueFactory("name"));
         sam2.setCellValueFactory(new PropertyValueFactory("anfangsdatum"));
         sam3.setCellValueFactory(new PropertyValueFactory("enddatum"));

         sammlungtable.setItems(null);
         sammlungtable.setItems(sammlungdata);

         
     } catch (Exception e) {
         e.printStackTrace();
         System.out.println("Error on Building Data");
     }
   	 
 	try {
    	kommentartable.setEditable(true);

        con = SqliteConnection.Connector();
        stat = con.createStatement();

        kommentardata = FXCollections.observableArrayList();
        prep =con.prepareStatement("SELECT Kommentar_Museum.text FROM Kommentar_Museum JOIN Gebaeude ON Kommentar_Museum.gebaeude_id=Gebaeude.gebaeude_id WHERE Gebaeude.name=?");
        prep.setString(1, suche.getText());
        ResultSet rs = prep.executeQuery();

        while (rs.next()) {
        	kommentardata.add(new KommentarData(rs.getString("text")));
        }
        kom1.setCellValueFactory(new PropertyValueFactory("text"));

        kommentartable.setItems(null);
        kommentartable.setItems(kommentardata);
        
        
        kommentartable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 1){

                        kommentar.setText((String) kom1.getCellData(kommentartable.getSelectionModel().getSelectedIndex()));                            
                    }
                }
            }
        }); 

        
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error on Building Data");
    }
   	 
   	 
   }
	
	public void kommentarSpeichern(ActionEvent event) throws InterruptedException{

			try {

		            prep = con.prepareStatement("insert into Kommentar_Museum(text,gebaeude_id,benutzer_id) values(?,?,(SELECT benutzer_id FROM Benutzer WHERE benutzername=?));");
		            prep.setString(1, kommentar.getText());
		            prep.setString(2, museumid.getText());
		            prep.setString(3, userLbl.getText());
		            prep.execute();
		            kommentar.setText(null);

		        } catch (SQLException ex) {
		        }
       
	}

	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		try {
        	table.setEditable(true);

            con = SqliteConnection.Connector();
            stat = con.createStatement();

            data = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("select Gebaeude.land,Gebaeude.stadt,Gebaeude.strasse,Gebaeude.hausnummer,Gebaeude.name,Oeffnungszeit.wochentag,Oeffnungszeit.von,Oeffnungszeit.bis, Gebaeude.gebaeude_id FROM Gebaeude JOIN Museum ON Gebaeude.gebaeude_id=Museum.museum_id JOIN Hat_Museum ON Gebaeude.gebaeude_id=Hat_Museum.museum_id JOIN Oeffnungszeit ON Hat_Museum.oeffnungszeit_id=Oeffnungszeit.oeffnungszeit_id");
            while (rs.next()) {
                data.add(new MuseumData(rs.getString("land"), rs.getString("stadt"), rs.getString("strasse"), rs.getString("hausnummer"), rs.getString("name"), rs.getString("wochentag"), rs.getString("von"), rs.getString("bis"), rs.getString("gebaeude_id")));
            }
            column1.setCellValueFactory(new PropertyValueFactory("land"));
            column2.setCellValueFactory(new PropertyValueFactory("stadt"));
            column3.setCellValueFactory(new PropertyValueFactory("strasse"));
            column4.setCellValueFactory(new PropertyValueFactory("hausnummer"));
            column5.setCellValueFactory(new PropertyValueFactory("name"));
            column6.setCellValueFactory(new PropertyValueFactory("wochentag"));
            column7.setCellValueFactory(new PropertyValueFactory("von"));
            column8.setCellValueFactory(new PropertyValueFactory("bis"));
            column9.setCellValueFactory(new PropertyValueFactory("gebaeude_id"));

            table.setItems(null);
            table.setItems(data);
            
            table.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                        if(mouseEvent.getClickCount() == 1){
                            suche.setText((String) column5.getCellData(table.getSelectionModel().getSelectedIndex()));
                            museumid.setText((String) column9.getCellData(table.getSelectionModel().getSelectedIndex()));

                        }
                    }
                }
            });
                
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fehler bei tabelle einlesen");
        }
		
	}
	
	
	
	public static class MuseumData {

        private StringProperty land;
        private StringProperty stadt;
        private StringProperty strasse;
        private StringProperty hausnummer;
        private StringProperty name;
        private StringProperty wochentag;
        private StringProperty von;
        private StringProperty bis;
        private StringProperty gebaeude_id;


        private MuseumData(String land, String stadt,String strasse, String hausnummer, String name, String wochentag, String von, String bis, String gebaeude_id) {
            this.land = new SimpleStringProperty(land);
            this.stadt = new SimpleStringProperty(stadt);
            this.strasse = new SimpleStringProperty(strasse);
            this.hausnummer = new SimpleStringProperty(hausnummer);
            this.name = new SimpleStringProperty(name);
            this.wochentag = new SimpleStringProperty(wochentag);
            this.von = new SimpleStringProperty(von);
            this.bis = new SimpleStringProperty(bis);
            this.gebaeude_id = new SimpleStringProperty(gebaeude_id);

        }

        public StringProperty landProperty() {
            return land;
        }
        public StringProperty stadtProperty() {
            return stadt;
        }
        public StringProperty strasseProperty() {
            return strasse;
        }
        public StringProperty hausnummerProperty() {
            return hausnummer;
        }
        public StringProperty nameProperty() {
            return name;
        }
        public StringProperty wochentagProperty() {
            return wochentag;
        }
        public StringProperty vonProperty() {
            return von;
        }
        public StringProperty bisProperty() {
            return bis;
        }
        public StringProperty gebaeude_idProperty() {
            return gebaeude_id;
        }
    }
	
	public static class KunstwerkData {

        private StringProperty kunstwerkname;
        private StringProperty anfangsdatum;
        private StringProperty enddatum;
        


        private KunstwerkData(String kunstwerkname,String anfangsdatum,String enddatum) {
            this.kunstwerkname = new SimpleStringProperty(kunstwerkname);
            this.anfangsdatum = new SimpleStringProperty(anfangsdatum);
            this.enddatum = new SimpleStringProperty(enddatum);
            
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
    }
	
	public static class SammlungData {

        private StringProperty name;
        private StringProperty anfangsdatum;
        private StringProperty enddatum;


        private SammlungData(String name,String anfangsdatum,String enddatum) {
            this.name = new SimpleStringProperty(name);
            this.anfangsdatum = new SimpleStringProperty(anfangsdatum);
            this.enddatum = new SimpleStringProperty(enddatum);
            
        }

        public StringProperty nameProperty() {
            return name;
        }
        public StringProperty anfangsdatumProperty() {
            return anfangsdatum;
        }
        public StringProperty enddatumProperty() {
            return enddatum;
        }
    }
	
	public static class KommentarData {
        private StringProperty text;

        private KommentarData(String text) {
            this.text = new SimpleStringProperty(text);
        }
       
        public StringProperty textProperty() {
            return text;
        }
    }
	
	

}
