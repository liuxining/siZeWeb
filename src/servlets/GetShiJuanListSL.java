package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ShiJuan;
import beans.UserInfo;
import dao.ShiJuanDAO;

/**
 * Servlet implementation class GetShiJuanList
 */
@WebServlet("/GetShiJuanListSL")
public class GetShiJuanListSL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShiJuanListSL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html;charSet=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("获取试卷列表servlet开始执行");
		
		//获取session对象
		HttpSession session = request.getSession();
		//从session对象获取用户信息
		UserInfo u = (UserInfo) session.getAttribute("userInfo");
		//System.out.println("参数：username=" + username);
		List<ShiJuan> shiJuanList = null;
		
		//构建试卷数据库操作对象
		ShiJuanDAO sjdao = new ShiJuanDAO();
		try {
			shiJuanList = sjdao.selectByUsername(u.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String result = "";
		
		if(shiJuanList != null)
		{
			for(int i = 0;i < shiJuanList.size();i++)
			{
				ShiJuan sj = shiJuanList.get(i);
				result += "日期：" + sj.getDate() + "  试题数：" + sj.getShiTiNum() + "  正确个数：" + sj.getGrade() + "," + sj.getShiJuanID() + "#";
			}
		}
		
		//获取out输出对象
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		
		System.out.println("获取试卷信息servlet执行结束，返回的信息:" + result);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
