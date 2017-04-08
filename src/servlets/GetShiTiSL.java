package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ShiJuan;
import beans.ShiTi;
import beans.UserInfo;
import dao.ShiJuanDAO;
import dao.ShiTiDAO;
import func.MyException;
import func.ShiTiFuncForm;

/**
 * Servlet implementation class GetShiTiSL
 */
@WebServlet("/GetShiTiSL")
public class GetShiTiSL extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShiTiSL() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("开始获取试题servlet开始执行");
		request.setCharacterEncoding("UTF-8");
		//获取参数
		int type = Integer.parseInt(request.getParameter("type"));
		int hasChengChu = Integer.parseInt(request.getParameter("hasChengChu"));
		int hasKuoHao = Integer.parseInt(request.getParameter("hasKuoHao"));
		int maxNum = Integer.parseInt(request.getParameter("maxNum"));
		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println("获取的参数值：type=" + type + "    hasChengChu=" + hasChengChu + "    hasKuoHao=" + hasKuoHao + "     maxNum=" + maxNum + "     num=" + num);
		//num = 5;//测试用，删除
		//获取试题
		List<ShiTi> shiTiList = null;
		try {
			shiTiList = ShiTiFuncForm.createYunSuanShi(hasChengChu,hasKuoHao, maxNum, num, type);
//			shiTiList = ShiTiFuncForm.createYunSuanShi(1,1, 10, 5, 0);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		for(int i = 0;i < num;i++)
		{
			System.out.println(shiTiList.get(i).getTiMu());
		}
		
		//获取session对象
		HttpSession session = request.getSession();
		
		//从session获取用户信息
		UserInfo u = (UserInfo)session.getAttribute("userInfo");
		//将试题信息shiTiList构建成一个字符串（每个试题用‘#’号分隔）保存到session属性
		String shiTiResult = "";
		int shiJuanID = u.getShiJuanNum() + 1;
		String username = u.getUsername();
		List<ShiTi> shiTiList2 = new ArrayList<ShiTi>();
		for(int i = 0;i < shiTiList.size();i++)
		{
			ShiTi st = shiTiList.get(i);
			shiTiResult += st.getTiMu() + "#";
			st.setShiJuanID(shiJuanID);
			st.setUsername(username);
			shiTiList2.add(st);
			
		}
		shiTiResult += shiJuanID + "";//用户试卷id
		//获取out对象，将试题信息输出到响应
		PrintWriter out = response.getWriter();
		out.print(shiTiResult);
		out.flush();
		out.close();
		
		//将试题信息shiTiList保存到session中
		session.setAttribute("shiTiList", shiTiList);
		//将试题个数保存到session中
		session.setAttribute("num", num);
		
		
		//构建一个试卷对象
		ShiJuan sj = new ShiJuan();
		sj.setShiTiNum(num);
		sj.setUsername(username);
		sj.setShiJuanID(shiJuanID);
		sj.setDate(new Date());
		//将试卷对象存储到session对象中
		session.setAttribute("shiJuan", sj);
		//构建一个试卷数据库操作对象
		ShiJuanDAO sjdao = new ShiJuanDAO();
		try {
			//将生成的试卷对象插入到试卷数据库
			sjdao.insert(sj);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//构建一个试题数据库操作对象
		ShiTiDAO stdao = new ShiTiDAO();
		
		try {
			//将shiTiList插入试题数据库
			stdao.insert(shiTiList2);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("获取试题servlet执行完成");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
