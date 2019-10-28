package net.tools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Random;

public class SQLDBTools {
	
	private Connection connection = null;
	public SQLDBTools(){
	}
	public Connection SQLDBConnect(){
		String username = System.getenv("POSTGRESQL_USER");
		String password = System.getenv("PGPASSWORD");
		String databaseURL = "jdbc:postgresql://";
		databaseURL += System.getenv("POSTGRESQL_SERVICE_HOST");
		databaseURL += "/" + System.getenv("POSTGRESQL_DATABASE");
		try {
			connection = DriverManager.getConnection(databaseURL, username,password);
			if (connection != null) {
				return connection;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			return null;
		}
	}
	public String GetFromDB() {
		String outStr="";
		if (connection != null) {
			String SQL = "";
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					outStr+=rs.getString(1);
				}
				return outStr;
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				return outStr;
			}
		}
		return outStr;		
	}
	public String PostToDB(String yourName) {
		String outStr="";
		Random rand = new Random();
		int randomNum = rand.nextInt(38);
		
		String query="INSERT INTO MYTABLE (COL1, COL2) VALUES ("+
                randomNum+",'"+yourName+"')";
		if (connection != null) {
			String SQL = "";
			try {
				Statement stmt = connection.createStatement();
				outStr=query;
				stmt.executeQuery(query);
				
				return outStr;
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				return outStr;
			}
		}
		return outStr;		
	}
}
