package main.db.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyMySQLConnection {

	private static String host;
	private static String port;
	private static String database;
	private static String user;
	private static String pwd;
	
	private static Connection connection = null;

	String propertiesPath = "/main/resources/db.properties";
	
	public MyMySQLConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
//			Class.forName("com.mysql.jdbc.Driver");
	
			InputStream inputStream = getClass().getResourceAsStream(propertiesPath);
			Properties properties = new Properties();
			
			properties.load(inputStream);
			
			host = properties.getProperty("mysql.host");
			port = properties.getProperty("mysql.port");
			database = properties.getProperty("mysql.database");
			user = properties.getProperty("mysql.user");
			pwd = properties.getProperty("mysql.pwd");
	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(propertiesPath + " 파일 읽기에 실패하였습니다.");
			e.printStackTrace();
		}
		
		try {
			//mysql-connector-java 버전 5.1.X 이후 버전부터 timezone을 인식하지 못하는 경우 있음
			//mysql에 default_time_zone 를 세팅해주어도 됨. (ex. default_time_zone='+03:00')
			//CLIENT_PLUGIN_AUTH is required : SSL 미사용 에러가 날 경우, mysql-connector-java 버전을 낮추거나 mysql 버전 업그레이드 진행 필요
			System.out.println("jdbc:mysql://"+host+":"+port+"/"+database+"?autoReconnect=true&useSSL=false&serverTimezone=UTC");
			connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database+"?autoReconnect=true&useSSL=false&serverTimezone=UTC", user, pwd);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	public static String getHost() {
		return host;
	}

	@Override
	protected void finalize() throws Throwable {
		connection.close();
		super.finalize();
	}
}

