package application;
import java.sql.*;
public class LoginModel {
	Connection conection;
  public LoginModel() {
	  conection = SqliteConnection.Connector();
	  if (conection == null) System.exit(1);
  }
  
  public boolean isDbConnected(){
	  try {
		return !conection.isClosed();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	  
}
 
  // Benutzer Login
  public boolean isLoginBenutzer(String user, String pass) throws SQLException {
	  PreparedStatement preparedStatement = null;
	  ResultSet resultSet = null;
	  String query = "select * from Benutzer where benutzername = ? and passwort = ?";
	  try {
		  preparedStatement = conection.prepareStatement(query);	
		  preparedStatement.setString(1, user);
		  preparedStatement.setString(2, pass);
		  
		  
		  resultSet = preparedStatement.executeQuery();
		  if (resultSet.next()) {
			  return true;
		  }
		  else {
			  return false;
		  }
		  
	} catch (Exception e) {
		return false;
		// TODO:handle exception
	} finally {
		preparedStatement.close();
		resultSet.close();
	}
  }
  // Kuenstler Login
  public boolean isLoginKuenstler(String user, String pass) throws SQLException {
	  PreparedStatement preparedStatement = null;
	  ResultSet resultSet = null;
	  String query = "select * from Benutzer INNER JOIN Kuenstler ON Benutzer.benutzer_id=Kuenstler.benutzer_id where benutzername = ? and passwort = ?";
	  try {
		  preparedStatement = conection.prepareStatement(query);	
		  preparedStatement.setString(1, user);
		  preparedStatement.setString(2, pass);
		  
		  resultSet = preparedStatement.executeQuery();
		  if (resultSet.next()) {
			  return true;
		  }
		  else {
			  return false;
		  }
		  
	} catch (Exception e) {
		return false;
		// TODO:handle exception
	} finally {
		preparedStatement.close();
		resultSet.close();
	}
  }
}
