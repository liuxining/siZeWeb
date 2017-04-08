package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.UserInfo;
import dao.UserInfoDAO;

/**
 * Servlet implementation class CheckUsernameHasSL
 */
@WebServlet("/CheckUsernameHasSL")
public class CheckUsernameHasSL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUsernameHasSL() {
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
		String username = request.getParameter("username");
		String result = "用户名已存在";
		if(username != null)
		{
			UserInfo u = new UserInfo();
			u.setUsername(username);
			UserInfoDAO uidao = new UserInfoDAO();
			UserInfo n = null;
			try {
				n = uidao.selectByUsername(u);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(n == null)
			{
				result = "用户名可用";
			}
		}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
