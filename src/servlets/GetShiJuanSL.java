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

import beans.ShiTi;
import beans.UserInfo;
import dao.ShiTiDAO;

/**
 * Servlet implementation class GetShiJuanSL
 */
@WebServlet("/GetShiJuanSL")
public class GetShiJuanSL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShiJuanSL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("获取试卷中的试题servlet开始执行");
		response.setContentType("text/html;charSet=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		//获取参数值
		String shiJuanID = request.getParameter("shiJuanID");
		String shiTiType = request.getParameter("shiTiType");
		System.out.println("获取到的参数：试题信息" + shiJuanID + "   试题类型： " + shiTiType);
		
		
		//获取用户名
		HttpSession session = request.getSession();
		UserInfo u = (UserInfo)session.getAttribute("userInfo");
		String username = u.getUsername();
		
		System.out.println("用户名：" + username);
		
		List<ShiTi> shiTiList = null;
		ShiTiDAO stdao = new ShiTiDAO();
		try {
			if(shiJuanID.equals("all"))
			{
				if(shiTiType.equals("all"))
				{
					//查询用户所有试卷的所有试题
					shiTiList = stdao.selectByUsername(username); 
				}
				else
				{
					int type = 0;
					if(shiTiType.equals("right"))
					{
						type = 1;
					}
					shiTiList = stdao.selectByUserNameANDScore(username, type);
				}
			}
			else
			{
				int shijuanid = Integer.parseInt(shiJuanID);
				if(shiTiType.equals("all"))
				{
					//查询用户所有试卷的所有试题
					shiTiList = stdao.selectByUserNameANDShiJuanID(username, shijuanid); 
				}
				else
				{
					int type = 0;
					if(shiTiType.equals("right"))
					{
						type = 1;
					}
					shiTiList = stdao.selectByUsernameAndShiJuanIDAndScore(username, shijuanid, type);
				}

			}
		}catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(shiTiList == null)
		{
			System.out.println("试题list为空");
		}
		else
		{
			System.out.println("试题list不为空，长度为：" + shiTiList.size());
			for(int i = 0;i < shiTiList.size();i++)
			{
				System.out.println(shiTiList.get(i).getTiMu());
			}
		}
		
			
		String result = "";
		for(int i = 0;i < shiTiList.size();i++)
		{
			ShiTi st = shiTiList.get(i);
			result += st.getTiMu() + "," + st.getUserAnswer() + "," + st.getRightAnswer() + "," + st.getUserScore() + "#";
		}
		
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
		System.out.println("获取试卷中的试题servlet执行结束，返回的信息为： " + result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
