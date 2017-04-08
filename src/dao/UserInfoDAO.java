package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserInfo;

public class UserInfoDAO {
	//插入一条数据到用户表
	public void insert(UserInfo u) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("insert into userInfo(username,password) values(?,?)");
		
		pstmt.setString(1, u.getUsername());
		pstmt.setString(2, u.getPassword());
		pstmt.executeUpdate();
		
		DBUtil.closeDB(conn, pstmt, rs);
	}
	
	//查询数据库，根据用户名
	public UserInfo selectByUsername(UserInfo u) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		UserInfo n = null;
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("select * from userInfo where username=?");
		pstmt.setString(1, u.getUsername());
		
		rs = pstmt.executeQuery();
		
		if(rs.next())
		{
			n = new UserInfo();
			n.setUsername(rs.getString("username"));
			n.setPassword(rs.getString("password"));
			n.setShiTiNum(rs.getInt("shiTiNum"));
			n.setShiJuanNum(rs.getInt("shiJuanNum"));
			n.setRightNum(rs.getInt("rightNum"));
			
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
		return n;
	}
	
	//更新用户表,根据用户名，更新做题数目，试卷数目，正确题目个数
	public void update(UserInfo u) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("update userInfo set shiTiNum=?,shiJuanNum=?,rightNum=? where username=?");
		pstmt.setInt(1, u.getShiTiNum());
		pstmt.setInt(2, u.getShiJuanNum());
		pstmt.setInt(3, u.getRightNum());
		pstmt.setString(4, u.getUsername());
		pstmt.executeUpdate();
		
		DBUtil.closeDB(conn, pstmt, rs);
	}
	
	
	
	
	
	
}
