package func;

import java.util.ArrayList;
import java.util.List;

import beans.ShiTi;

public class ShiTiFuncForm {
	// 生成整数计算式添加限制条件,type为运算式类型 0代表整数式，1代表真分数式
	public static List<ShiTi> createYunSuanShi(int hasChengChu,int hasKuoHao, int maxNum, int n, int type) throws MyException {
		int i = 0;
		if(n <= 0)
		{
			throw new MyException("运算数个数设置错误,应为正数");
		}
		List<ShiTi> list = new ArrayList<ShiTi>();
		ShiTi stb = null;
		// ShiTiDAO std = new ShiTiDAO();
		while (i < n) {
			stb = ShiTiOperator.getExpress(maxNum, hasKuoHao,hasChengChu, type);

			// 检验重复
			boolean chongFu = false;
			for (int j = 0; j < i; j++) {
				ShiTi t = list.get(j);
				if (ShiTiOperator.calculateOrderSame(stb, t)) {
					chongFu = true;
					System.out.println("出现重复：计算式一：" + t.getTiMu() + " = " + t.getRightAnswer() + " 运算顺序："
							+ t.getLogicOrder() + " 运算数个数：" + t.getLength());
					System.out.println("出现重复：计算式二：" + stb.getTiMu() + " = " + stb.getRightAnswer() + " 运算顺序："
							+ stb.getLogicOrder() + " 运算数个数：" + stb.getLength());
					System.out.println("\n\n");
					break;
				}
			}
			if (chongFu == false) {
				list.add(stb);
				i++;
			}
		}
		return list;
	}
}
