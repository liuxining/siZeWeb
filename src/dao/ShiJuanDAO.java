package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beans.ShiJuan;

public class ShiJuanDAO {
	
	
	
	//插入一条试卷记录到数据库
	public void insert(ShiJuan sj) throws SQLException
	{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getDBConnection();
	//	System.out.println(sj.getShiTiNum() + " " + sj.getUsername() + " " + sj.getDate() + " " + sj.getUserCurrectSum());
		pstmt = conn.prepareStatement("insert into shiJuanKu(shiTiNum,username,date,shiJuanID) values(?,?,?,?)");
		pstmt.setInt(1, sj.getShiTiNum());
		pstmt.setString(2, sj.getUsername());
		pstmt.setTimestamp(3, new Timestamp(sj.getDate().getTime()));
		pstmt.setInt(4, sj.getShiJuanID());
		pstmt.executeUpdate();
		
		DBUtil.closeDB(conn, pstmt, rs);
		
	}
	
	//根据用户名查询试卷信息，返回值类型为list
	public List<ShiJuan> selectByUsername(String username) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ShiJuan> list = new ArrayList<ShiJuan>();
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("select * from shiJuanKu where username=?");
		pstmt.setString(1, username);
		
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			ShiJuan sj = new ShiJuan();
			sj.setId(rs.getInt("id"));
			sj.setShiTiNum(rs.getInt("shiTiNum"));
			sj.setUsername(rs.getString("username"));
			sj.setGrade(rs.getInt("grade"));
			sj.setDate(rs.getTimestamp("date"));
			sj.setShiJuanID(rs.getInt("shiJuanID"));
			list.add(sj);
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
		return list;

	}
	
	//更新试卷信息，设置试卷题目，根据用户名和用户当前试卷总数
	public void update(ShiJuan sj) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("update shiJuanKu set grade=? where username=? and shiJuanID=?");
		pstmt.setInt(1,sj.getGrade());
		pstmt.setString(2, sj.getUsername());
		pstmt.setInt(3, sj.getShiJuanID());
		pstmt.executeUpdate();
		
		DBUtil.closeDB(conn, pstmt, rs);
		
		
	}
	
	
}
