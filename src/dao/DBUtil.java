package dao;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
	
	private static String driverName;
	private static String username;
	private static String password;
	private static String url;
	
	
//	static 
//	{
//		driverName = "com.mysql.jdbc.Driver";
//		url = "jdbc:mysql://localhost:3306/siZeWeb?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
//		username = "root";
//		password = "490272";
//	}
	
	//从属性文件加载参数（URL 用户名 密码）
	static
	{
		Properties prop = new Properties();
		try {
			prop.load(DBUtil.class.getResourceAsStream("/db.properties"));
			driverName = prop.getProperty("driverName");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	public static void main(String[] args) {
		Connection conn = getDBConnection();
		closeDB(conn, null, null);
	}
	
	//获取数据库连接
	public static Connection getDBConnection()
	{
		Connection conn = null;
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(url,username,password);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	//释放资源
	public static void closeDB(Connection conn,PreparedStatement pstmt,ResultSet rs)
	{
		try {
			if(rs != null)
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
