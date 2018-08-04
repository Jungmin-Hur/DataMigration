package main.db.oracle;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyOracleConnection {

//	private static String SERVER = "localhost";
//	private static String SID = "orcl";
//	private static String USER = "jason";
//	private static String PWD = "1234";
	private static String server;
	private static String sid;
	private static String user;
	private static String pwd;
	
	private static Connection connection = null;
	
	String propertiesPath = "/main/resources/db.properties";


	public MyOracleConnection() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			InputStream inputStream = getClass().getResourceAsStream(propertiesPath);
			Properties properties = new Properties();
			
			properties.load(inputStream);
			
			server = properties.getProperty("oracle.server");
			sid = properties.getProperty("oracle.sid");
			user = properties.getProperty("oracle.user");
			pwd = properties.getProperty("oracle.pwd");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Properties파일 읽기에 실패하였습니다.");
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@"+server+":1521:"+sid,user,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	public static String getServer() {
		return server;
	}
	
	@Override
	protected void finalize() throws Throwable {
		connection.close();
		super.finalize();
	}
}
