package net.tools;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
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
			obState="Try to connect user: "+username+" password: "+password+" conString: "+databaseURL+" \n";
			connection = DriverManager.getConnection(databaseURL, username,password);
			if (connection != null) {
				return connection;
			}
		}catch (Exception e) {
            		StringWriter sw = new StringWriter();
            		e.printStackTrace(new PrintWriter(sw));
            		obState += sw.toString();          
		}
		return null;  
	}
        private String oneRow(ResultSet resultSet){
                String template = "<tr><td>{0}</td><td>{1}</td></tr>";
                try {
                        return MessageFormat.format(template, resultSet.getString(1),resultSet.getString(2));
                } catch (Exception e) {
                        return template;
                }
        }
	public String GetFromDB() {
		String outStr="GET ---> ";
		if (connection != null) {
			String SQL = "Select * from mytable";
                        StringBuffer sb = new StringBuffer();
			sb.append("<table style=\"width=500px;\" border=\"1\">");
                        sb.append("<tr><td>Col 1</td><td>Col 2</td></tr>");
			int size=0;
			try {
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next()) {
					sb.append(oneRow(rs));
					size++;
					//outStr+=rs.getString(1);
				}
				if(size > 0)
					outStr=sb.toString();
				else
					outStr="No Record Found !";
                        	outStr+="</table>";
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            		e.printStackTrace(new PrintWriter(sw));
	            		outStr = "GET ERROR -> "+sw.toString();
	            		return outStr;
			}
		}
		return outStr;		
	}
	public String PostToDB(String yourName) {
		String outStr="POST ---> ";
		Random rand = new Random();
		int randomNum = rand.nextInt(38);
		String query="INSERT INTO MYTABLE (COL1, COL2) VALUES (?, ?)";
		if (connection != null) {
			String SQL = "";
			try {
				PreparedStatement pst = connection.prepareStatement(query);
            			pst.setInt(1, randomNum);
            			pst.setString(2, yourName);
            			int aR=pst.executeUpdate();
				pst.close();
				outStr=query+"..."+aR;
				
			}catch (Exception e) {
				StringWriter sw = new StringWriter();
	            		e.printStackTrace(new PrintWriter(sw));
	            		outStr = "POST ERROR -> "+sw.toString();
	            		return outStr;
			}		
			String allList=GetFromDB();
			outStr+=" ..done";		
			outStr+="<p>List</p>";		
                        outStr+=allList;
		}
		return outStr;		
	}
}
