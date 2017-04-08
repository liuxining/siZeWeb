package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UserInfo;
import dao.UserInfoDAO;

/**
 * Servlet implementation class LoginSL
 */
@WebServlet("/LoginSL")
public class LoginSL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginSL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("登录servlet开始执行");
		response.setContentType("text/html;charSet=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		UserInfo u = new UserInfo();
		u.setUsername(username);
		
		UserInfo n = null;
		
		UserInfoDAO uidao = new UserInfoDAO();
		try {
			n = uidao.selectByUsername(u);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		
		
		if(n == null)
		{
			//没有此用户
			out.print("用户名不存在");
			System.out.println("用户名不存在");
		}
		else if(n.getPassword().equals(password))
		{
			//登录成功
			//将用户信息存入session
			//获取session对象
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", n);
			out.print(username);
			System.out.println("用户： " + username + " 登录成功");
		}
		else
		{
			//密码错误
			out.print("密码错误");
			System.out.println("密码错误");
		}
		out.flush();
		out.close();
		System.out.println("登录servlet执行结束");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
