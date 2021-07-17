package utility;

import java.sql.Connection; // all sql modulle are in java.sql package
import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionUtility {
	
	public Connection conn;
	
	public void connect(String url, String userName, String password) throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("mysql driver loaded successfully");
			this.conn = (Connection) DriverManager.getConnection(url, userName, password);
			
		} catch (Exception e) {
			System.out.println("Exception : " +e.getMessage());
		}
	}
	
} 
