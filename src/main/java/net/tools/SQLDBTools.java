package net.tools;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Random;

public class SQLDBTools {
	
	private Connection connection = null;
	private String obState = "NONE";
	public SQLDBTools(){
	}
	public String GetState() {
		return obState;
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
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            obState = sw.toString();          
		}
		return null;  
	}
	public String GetFromDB() {
		String outStr="GET ---> ";
		if (connection != null) {
			String SQL = "";
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					outStr+=rs.getString(1);
				}
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            outStr = sw.toString();
	            return outStr;
			}
		}
		return outStr;		
	}
	public String PostToDB(String yourName) {
		String outStr="POST ---> ";
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
				
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            outStr = sw.toString();
	            return outStr;
			}		}
		return outStr;		
	}
}
