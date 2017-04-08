package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.ShiTi;

public class ShiTiDAO {
	
	//将list中的试题插入到数据库,
	public void insert(List<ShiTi> list) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("insert into shiTiKu(tiMu,rightAnswer,username,logicOrder,length,shiJuanID) values(?,?,?,?,?,?)");
		
		for(ShiTi st : list)
		{
			pstmt.setString(1, st.getTiMu());
			pstmt.setString(2, st.getRightAnswer());
			pstmt.setString(3, st.getUsername());
			pstmt.setString(4, st.getLogicOrder());
			pstmt.setInt(5, st.getLength());
			pstmt.setInt(6, st.getShiJuanID());
			pstmt.executeUpdate();
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
	}
	
	//根据用户名和用户的试题ID查询试题，返回结果为list
	public List<ShiTi> selectByUserNameANDShiJuanID(String username,int shiJuanID) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ShiTi> list = new ArrayList<ShiTi>();
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("select * from shiTiKu where username=? and shiJuanID=?");
		pstmt.setString(1, username);
		pstmt.setInt(2, shiJuanID);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			ShiTi st = new ShiTi();
			st.setId(rs.getInt("id"));
			st.setTiMu(rs.getString("tiMu"));
			st.setRightAnswer(rs.getString("rightAnswer"));
			st.setUserAnswer(rs.getString("userAnswer"));
			st.setUserScore(rs.getInt("userScore"));
			list.add(st);
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
		return list;


	}
	
	
	//根据用户名和试题对错查询试题，返回结果为list
		public List<ShiTi> selectByUserNameANDScore(String username,int score) throws SQLException
		{
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			List<ShiTi> list = new ArrayList<ShiTi>();
			
			conn = DBUtil.getDBConnection();
			pstmt = conn.prepareStatement("select * from shiTiKu where username=? and userScore=?");
			pstmt.setString(1, username);
			pstmt.setInt(2, score);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				ShiTi st = new ShiTi();
				st.setId(rs.getInt("id"));
				st.setTiMu(rs.getString("tiMu"));
				st.setRightAnswer(rs.getString("rightAnswer"));
				st.setUserAnswer(rs.getString("userAnswer"));
				st.setUserScore(rs.getInt("userScore"));
				list.add(st);
			}
			
			DBUtil.closeDB(conn, pstmt, rs);
			return list;


		}

	
	//根据用户名查询试题
	public List<ShiTi> selectByUsername(String username) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ShiTi> list = new ArrayList<ShiTi>();
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("select * from shiTiKu where username=?");
		pstmt.setString(1, username);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			ShiTi st = new ShiTi();
			st.setId(rs.getInt("id"));
			st.setTiMu(rs.getString("tiMu"));
			st.setRightAnswer(rs.getString("rightAnswer"));
			st.setUserAnswer(rs.getString("userAnswer"));
			st.setUserScore(rs.getInt("userScore"));
			list.add(st);
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
		return list;


	}
	
	//根据用户名/试卷id、试题得分查询试题
	public List<ShiTi> selectByUsernameAndShiJuanIDAndScore(String username,int shiJuanID,int score) throws SQLException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<ShiTi> list = new ArrayList<ShiTi>();
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("select * from shiTiKu where username=? and shiJuanID=? and userScore=?");
		pstmt.setString(1, username);
		pstmt.setInt(2, shiJuanID);
		pstmt.setInt(3, score);
		rs = pstmt.executeQuery();
		
		while(rs.next())
		{
			ShiTi st = new ShiTi();
			st.setId(rs.getInt("id"));
			st.setTiMu(rs.getString("tiMu"));
			st.setRightAnswer(rs.getString("rightAnswer"));
			st.setUserAnswer(rs.getString("userAnswer"));
			st.setUserScore(rs.getInt("userScore"));
			list.add(st);
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
		return list;


	}

	
	
	//更新试题，根据题目和用户名和试卷ID 
	public void update(List<ShiTi> list) throws SQLException
	{

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		conn = DBUtil.getDBConnection();
		pstmt = conn.prepareStatement("update shiTiKu set userAnswer=?,userScore=? where tiMu=? and username=? and shiJuanID=?");
		
		
		
		for(ShiTi st : list)
		{
			pstmt.setString(1, st.getUserAnswer());
			pstmt.setInt(2, st.getUserScore());
			pstmt.setString(3, st.getTiMu());
			pstmt.setString(4, st.getUsername());
			pstmt.setInt(5, st.getShiJuanID());
			pstmt.executeUpdate();
		}
		
		DBUtil.closeDB(conn, pstmt, rs);
		
	}
	
	
	
}
