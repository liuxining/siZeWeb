package beans;

import java.util.Stack;

import func.FenShu;
import func.MyException;

public class ShiTi {
	private int id,userScore, length,shiJuanID;
	private String tiMu, rightAnswer, userAnswer, username,logicOrder;

	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getShiJuanID() {
		return shiJuanID;
	}

	public void setShiJuanID(int shiJuanID) {
		this.shiJuanID = shiJuanID;
	}

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
	
	

	
	
	public String getTiMu() {
		return tiMu;
	}

	public void setTiMu(String tiMu) {
		this.tiMu = tiMu;
		try {
			expressCalculate();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 计算答案
		this.length = (tiMu.split(" ").length + 1) / 2;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getLogicOrder() {
		return logicOrder;
	}

	public void setLogicOrder(String logicOrder) {
		this.logicOrder = logicOrder;
	}

	public ShiTi() {

	}
	
	
	
	
	
	
	
	
	
	
	
	
	// 表达式计算,参数为字符串类型的运算式
	private void expressCalculate() throws MyException {
		if(this.tiMu == null)
		{
			throw new MyException("试题无效");
		}
		String express = this.tiMu;
		
		
		Stack<String> num = new Stack<String>();
		Stack<String> symbolS = new Stack<String>();
		symbolS.push("#");
		express += "#";
		String order = "";
		char ch;
		int i = 0;
		ch = express.charAt(i);
		while ((!symbolS.peek().equals("#")) || (ch != '#')) {// while循环开始
			if (isNumber(ch)) {// 读到的不是空格，说明开始读运算数
				String readNumStr = "";
				while (true) {
					readNumStr += ch;
					ch = express.charAt(++i);
					if (ch == ' ' || ch == '#' || ch == ')') {// 读到的是空格,或，说明运算数结束
						break;
					}
				}
				num.push(readNumStr);
			} else if (ch == ' ') {
				if ((i + 1) < express.length()) {// 未到字符串末尾
					ch = express.charAt(++i);
				}
			}else {// 读到的是运算符
				char compare = priorityCompare(symbolS.peek(), ch + "");

				if (compare == '=') {// 若优先级相等，则说明ch是右括号，栈顶为左括号，此时将栈顶弹出，读取下一个字符
					symbolS.pop();
					ch = express.charAt(++i);
				} else if (compare == '>') {// ch的优先级小于栈顶的优先级，要说明栈顶的运算符应该先计算，所以应弹栈运算
											// 弹出两个运算数，弹出一个运算符
					String bStr = num.pop();
					String aStr = num.pop();
					String symbolT = symbolS.pop();
					// 计算该字表达式
					String c = yunSuan(aStr, bStr, symbolT);
					if (c.equals("ERROR")) {// 如果计算函数返回error则说明计算过程出现了负数，说明该运算式不符合要求，停止计算，计算结果为error，返回；
						this.rightAnswer = "ERROR";
						return;
					} else {// 计算过程正常，则将计算结果压栈
						order += aStr + "," + symbolT + "," + bStr + ",";// 将运算的子表达式加进运算顺序字符串中，操作数和操作符用逗号隔开
						num.push(c);
					}
				} else if(compare == 'E')
				{
					this.rightAnswer = "ERROR";
					return;
				} else {// 说明ch优先级大于栈顶元素的优先级，则应将ch压栈，读取下一个运算符
					symbolS.push(ch + "");
					if ((i + 1) < express.length()) {
						ch = express.charAt(++i);
					}
				}

			}
		}
		this.rightAnswer = num.pop();
		this.logicOrder = order;
	}

	// 判断ch是否为数字
	private boolean isNumber(char ch) {
		if (ch >= '0' && ch <= '9') {
			return true;
		}
		return false;
	}

	/*
	 * 子表达式计算，参数为两个运算数的字符串形式，和一个运算符，也为字符串类型 返回计算结果的字符串形式
	 * 如果减法运算出现负数，或除数为0，或分数的分母为0则返回ERROR
	 * 
	 */
	private String yunSuan(String aStr, String bStr, String symbol) throws MyException {
		if(aStr == null || bStr == null || symbol == null)
		{
			throw new MyException("子表达式出现错误！");
		}
		int adivIndex = aStr.indexOf("/");
		int bdivIndex = bStr.indexOf("/");
		if ((adivIndex == -1) && (bdivIndex == -1)) {// a.b都是整数
			int a = Integer.parseInt(aStr);
			int b = Integer.parseInt(bStr);
			switch (symbol.charAt(0)) {
			case '+':
				return a + b + "";
			case '-': {
				if (a < b) {
					return "ERROR";
				}
				return a - b + "";
			}
			case '*': {
				return a * b + "";
			}
			case '/': {
				if (b == 0) {
					return "ERROR";
				} else if (a % b == 0) {
					return a / b + "";
				}
				return new FenShu(a, b).toString();
			}
			default:
				return "ERROR";
			}
		} else {// a,b中存在分数，则将a,b都当做分数进行运算
			FenShu a = new FenShu(aStr);
			FenShu b = new FenShu(bStr);
			switch (symbol.charAt(0)) {
			case '+':
				return a.add(b).toString();
			case '-':
			{
				FenShu c = a.subtract(b);
				if(c.getNumerator() < 0)
				{
					return "ERROR";
				}
				return c.toString();
			}
			case '*':
				return a.multiply(b).toString();
			case '/':
				return a.divide(b).toString();
			default:
				return "ERROR";
			}
		}
	}

	// 判断运算符优先级
	private char priorityCompare(String a, String b) {
		char[][] priority = { { '>', '>', '<', '<', '<', '>', '>' }, { '>', '>', '<', '<', '<', '>', '>' },
				{ '>', '>', '>', '>', '<', '>', '>' }, { '>', '>', '>', '>', '<', '>', '>' },
				{ '<', '<', '<', '<', '<', '=', '>' }, { '>', '>', '>', '>', ' ', '>', '>' },
				{ '<', '<', '<', '<', '<', ' ', '=' } };
		int a_index = index_symbol(a);
		int b_index = index_symbol(b);
		if(a_index == -1 || b_index == -1)
		{
			return 'E';
		}
		return priority[a_index][b_index];
	}

	// 获取运算符对应的下标
	private int index_symbol(String a) {
		String p = "+-*/()#";
		// System.out.println("判断运算符对应的下标：" + a);
		return p.indexOf(a);
	}
	
	
	
	
	
	
	
	

}
