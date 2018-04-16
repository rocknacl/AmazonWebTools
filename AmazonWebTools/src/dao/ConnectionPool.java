package dao;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionPool {
	String driver = "com.mysql.jdbc.Driver";
	String url;
	String user;
	String password;
	Connection conn;
	Properties prop = new Properties();
	
	private static ConnectionPool pool;
	
	
	
	private ConnectionPool(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		
	}
	private ConnectionPool() {
		try {
//			prop.load(new FileInputStream("mysqlConnection.properties"));
//			this.url = prop.getProperty("url");
//			this.user = prop.getProperty("user");
//			this.password = prop.getProperty("password");
			
			this.url = "jdbc:mysql://localhost:3306/send_email?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
			this.user = "ksserver";
			this.password = "ksserver";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static ConnectionPool getConnectionPool(){
		if(pool==null){
			pool = new ConnectionPool();
		}
		return pool;
	}
	public Connection getConnection() throws SQLException{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("connecting "+this.url+ " using username "+this.user);
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	public Connection getConnection(String url,String user,String password) throws SQLException{
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("connecting "+this.url+ " using username "+this.user);
		conn = DriverManager.getConnection(url, user, password);
		return conn;
	}
	
	

}
