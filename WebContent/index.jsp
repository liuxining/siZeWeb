<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>主页</title>

<script src="js/jquery-3.2.0.min.js"></script>


<script type="text/javascript" src="js/index.js"></script>

<style type="text/css">
 	#wrap {position:absolute;left:0px;top:0px;width:100%;height:100%}
	#menu {display: none;text-align: center;}
	#userInfo {display: none;text-align: center;}
	#zuoTiInput {display: none;}
	#selectInput {display: none;text-align: center;}
	#tiMu {height: 400px;overflow: auto;}
	#show {display: none;}
	#right {position:absolute;left:50%;top:40px;height:80%;width:40%}
	#left {position:absolute;width: 50%;top:20%;}
	#zuoTiFuZhu {text-align: center;}
	#result {text-align: center;}
</style>


</head>
<body>
	<img src="photo/index_bg.png" width="100%" height="100%">
	<div id="wrap">
	
	<div id="left">
	
	
		<div id="loginMessage"></div>

		<div id="userInfo">
			用户名：${userInfo.username}
			<a href="LogoutSL"><button id="logout">注销</button></a>
		</div>

		<div id="menu">
			<button id="zuoTi">做题</button>
			<button id="selectLiShiShiTi">查询历史试题</button>
		</div>
		
		<div id="zuoTiInput" >

			<table align="center">
				<tr><td>试题类型：</td><td><label>整数式<input type="radio" name="type" value="0" checked="checked"></label></td><td><label>真分数式<input type="radio" name="type" value="1"></label></td></tr>
				<tr><td>有无乘除：</td><td><label>无<input type="radio" name="hasChengChu" value="0" checked="checked"></label></td><td><label>有<input type="radio" name="hasChengChu" value="1"></label></td></tr>
				<tr><td>有无括号：</td><td><label>无<input type="radio" name="hasKuoHao" value="0" checked="checked"></label></td><td><label>有<input type="radio" name="hasKuoHao" value="1"></label></td></tr>
				<tr><td>最大值：</td><td colspan="2"><input type="text" name="maxNum" value="10"><span id="maxNumInfo"></span></td></tr>
				<tr><td>试题个数：</td><td colspan="2"><input type="text" name="num" value="10"><span id="numInfo"></span></td></tr>
				<tr><td colspan="3"><input type="button" id="zuoTiInputTiJiao" value="提交"></td></tr>
			</table>

		</div>

		<div id="selectInput">
			试卷：
			<select id="shiJuanList">
				
			</select>
			类型：
			<select id="typeSelect">
				<option value="all" selected="selected">
					全部
				</option>
				<option value="right">
					正确
				</option>
				
				<option value="wrong">
					错误
				</option>
				
			</select>

			<button id="selectShiJuan">
				查询
			</button>
		</div>
	
	
		<div id="show">
			<div id="tiMu">
			<table id="showShiTiTable" align="center">
	
			</table>
			</div>
			<div id="zuoTiFuZhu"><button id="jiaoJuanBT">交卷</button><span id="shengYuTime"></span></div>
						
		
			<div id="result">
					<div id="rightResult"></div>
					<div id="wrongResult"></div>
			</div>
			
		</div>
				
	</div>
	
	
	<div id="right">
		<img src="photo/index_right.jpg"  width="100%" height="100%">
	</div>
 </div>
</body>
</html>