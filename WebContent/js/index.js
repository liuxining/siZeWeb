
    var alltime = 0;
  

	function daoJiShi()
	{
		
		var num = $("input:text[name='num']").val();
		var numInt = parseInt(num);
		
		alltime = numInt * 30;
		jian();
	}

	function jian()
	{

		//alert(alltime);
		if(alltime < 0)
		{

			return;
		}
		else if(alltime == 0)
		{
			window.alert("时间到，已自动交卷，点击确定查看答题结果");
			// document.getElementById('jiaoJuanBT').click();
			$("#jiaoJuanBT").click();
		}
		else
		{
		//	alert("jinlaile");
			// document.getElementById('shengYuTime').innerHTML = "剩余时间：" + Math.floor(alltime / 60) + " : " + (alltime % 60);
			
			alltime--;
			$("#shengYuTime").text("剩余时间：" + Math.floor(alltime / 60) + " : " + (alltime % 60));
			setTimeout("jian()",1000);
		}
	}

	$(document).ready(function(){		
	//页面加载完成即执行，利用ajax获取用户是否已登录,若返回值为no，则说明没有登录，显示登录div，否则在登录信息div显示已登录，2秒后跳转至主页
		$.ajax({
			url: 'CheckHasLoginSL',
			type: 'GET',
			//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
			//data: {param1: 'value1'},
		})
		.done(function(data) {
			if(data != "no"){
				$("#userInfo").show();
				$("#menu").show();
			}
			else
			{
				$("#loginMessage").text("用户未登录，2秒后跳转至登录页面");
				setTimeout("location='login.html'",2000);
			}

			console.log("success");
		})
		.fail(function() {
			console.log("error");
		})
		.always(function() {
			console.log("complete");
		});


		$("#zuoTi").click(function() {
			/* Act on the event */
			$("#selectInput").hide();
			$("#show").hide();
			$("#zuoTiInput").css('text-align', 'center');
			$("#zuoTiInput").show();
		});
		


		$("#zuoTiInputTiJiao").click(function() {
			/* Act on the event */
			
			//alert("做题alert");
			$("#maxNumInfo").text("");
			$("#numInfo").text("");
			var pattern = /^[1-9]\d*$/;
			if(!(pattern.test($("input:text[name='maxNum']").val())))
			{
				$("#maxNumInfo").text("请输入正整数");
				return;
			}
			if(!(pattern.test($("input:text[name='num']").val())))
			{
				$("#numInfo").text("请输入正整数");
				return;
			}
			

			var canshu = "type: " + $("input[name='type']:checked").val() + ",hasChengChu: " + $("input[name='hasChengChu']:checked").val() + ",hasKuoHao: " + $("input[name='hasKuoHao']:checked").val() + ",maxNum: " + $("input:text[name='maxNum']").val() + ",num: " + $("input:text[name='num']").val() + ";";
		//	alert(canshu);
			$.ajax({
				url: 'GetShiTiSL',
				type: 'POST',
				//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
				//data: {param1: 'value1'},
				data: {type: $("input[name='type']:checked").val(),hasChengChu:$("input[name='hasChengChu']:checked").val(),hasKuoHao:$("input[name='hasKuoHao']:checked").val(),maxNum:$("input:text[name='maxNum']").val(),num:$("input:text[name='num']").val()},
			})
			.done(function(data) {
				

				var shiTiShuZu = data.split("#");

				var tableTag = $("#showShiTiTable");
				$("#showShiTiTable").html("");
				$("#rightResult").html("");
				$("#wrongResult").html("");




				var tr1 = document.createElement('tr');
				var th1 = document.createElement('th');
				var textNode1 = document.createTextNode("题号");
				th1.appendChild(textNode1);
				tr1.appendChild(th1);

				var th2 = document.createElement('th');
				var textNode2 = document.createTextNode("题目");
				th2.appendChild(textNode2);
				tr1.appendChild(th2);

				var th3 = document.createElement('th');
				var textNode3 = document.createTextNode("答案");
				th3.appendChild(textNode3);
				tr1.appendChild(th3);

				// var th4 = document.createElement('th');
				// var textNode4 = document.createTextNode("结果");
				// th4.appendChild(textNode4);
				// tr.appendChild(th4);

				tableTag.append(tr1);



				for(var i = 0;i < shiTiShuZu.length - 1;i++)
				{
					var tr2 = document.createElement('tr');
					var td1 = document.createElement('td');
					var textNode1 = document.createTextNode((i + 1) + " : ");
					td1.appendChild(textNode1);
					tr2.appendChild(td1);

					var td2 = document.createElement('td');
					var textNode2 = document.createTextNode(shiTiShuZu[i] + " = ");
					td2.appendChild(textNode2);
					tr2.appendChild(td2);

					var td3 = document.createElement('td');
					var inputTag = document.createElement('input');
					inputTag.type='text';
					inputTag.name=(i + 1) + "";
					inputTag.class="tiMu";
					td3.appendChild(inputTag);
					

					var span = document.createElement('span');
					span.id=(i + 1) + "";
					td3.appendChild(span);

					tr2.appendChild(td3);


					tableTag.append(tr2);

				}

			
				$("#menu").hide();
				$("#selectInput").hide();
				$("#zuoTiInput").hide();
				$("#show").show();
				$("#zuoTiFuZhu").show();
				//开始计时
				daoJiShi();

				console.log("success");
			})
			.fail(function() {
				console.log("error");
			})
			.always(function() {
				console.log("complete");
			});
			
		});





		$("#jiaoJuanBT").click(function() {
			//alert(alltime);
			alltime = -1;
			var userAnswer = "";
			var n = $("input:text[name='num']").val();
			for(var i = 0;i < n;i++)
			{
				userAnswer += (i + 1) + "=" + $("input:text[name='" + (i + 1) + "']").val() + "&";
			}

			$.ajax({
				url: 'JiaoJuanSL',
				type: 'POST',
			//	dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
				data: userAnswer,
			})
			.done(function(data) {
				var result = data.split("#");
				for(var i = 0;i < result.length - 2;i++)
				{
					if(result[i] != "正确")
					{
						$("#"+ (i + 1)).css('color', 'red');
					}
					$("#"+ (i + 1)).text(result[i]);
				}
				
				$("#zuoTiFuZhu").hide();
				$("#menu").show();
				$("#wrongResult").css('color', 'red');
				$("#rightResult").text(result[result.length - 2]);
				$("#wrongResult").text(result[result.length - 1]);

				
				console.log("success");
			})
			.fail(function() {
				console.log("error");
			})
			.always(function() {
				console.log("complete");
			});
			

		});



			//点击查询历史试题按钮，利用ajax获取试卷列表
		$("#selectLiShiShiTi").click(function() {
			
			$.ajax({
				url: 'GetShiJuanListSL',
				type: 'POST',
				//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
				//data: {username: username},
			})
			.done(function(data) {
				
				var shiJuanShuZu = data.split("#");
				var selectTag = $("#shiJuanList");
				$("#shiJuanList").html("");
				var optionTag1 = document.createElement('option');
				var textNode1 = document.createTextNode("全部");
				optionTag1.appendChild(textNode1);
				optionTag1.value="all";
				optionTag1.selected = "selected";
				selectTag.append(optionTag1);
			
				for(var i = 0;i < shiJuanShuZu.length - 1;i++)
				{
				
					var shiJuanVar = shiJuanShuZu[i].split(",");
					var optionTag = document.createElement('option');
					var textNode = document.createTextNode((i + 1) + "、" + shiJuanVar[0]);
					optionTag.appendChild(textNode);
					optionTag.value=shiJuanVar[1];
					selectTag.append(optionTag);
				
				}

				$("#zuoTiInput").hide();
				$("#show").hide();
				$("#selectInput").show();
			
				console.log("success");
			})
			.fail(function() {
				console.log("error");
			})
			.always(function() {
				console.log("complete");
			});
 
		});


		$("#selectShiJuan").click(function() {
			var selectShiJuanID = $("#shiJuanList").val();
			var selectType = $("#typeSelect").val();
			$.ajax({
				url: 'GetShiJuanSL',
				type: 'POST',
				//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
				data: {shiJuanID: selectShiJuanID,shiTiType:selectType},
			})
			.done(function(data) {
				var shiTiShuZu = data.split("#");

				var tableTag = $("#showShiTiTable");
				$("#showShiTiTable").html("");
				$("#result").html("");



				var tr1 = document.createElement('tr');
				var th1 = document.createElement('th');
				var textNode1 = document.createTextNode("题号");
				th1.appendChild(textNode1);
				tr1.appendChild(th1);

				var th2 = document.createElement('th');
				var textNode2 = document.createTextNode("题目");
				th2.appendChild(textNode2);
				tr1.appendChild(th2);

				var th3 = document.createElement('th');
				var textNode3 = document.createTextNode("用户答案");
				th3.appendChild(textNode3);
				tr1.appendChild(th3);

				var th4 = document.createElement('th');
				var textNode4 = document.createTextNode("正确结果");
				th4.appendChild(textNode4);
				tr1.appendChild(th4);

				var th5 = document.createElement('th');
				var textNode5 = document.createTextNode("备注");
				th5.appendChild(textNode5);
				tr1.appendChild(th5);

				tableTag.append(tr1);


				for(var i = 0;i < shiTiShuZu.length - 1;i++)
				{

					var shiTiS = shiTiShuZu[i].split(",");

					var tr2 = document.createElement('tr');
					var td1 = document.createElement('td');
					var textNode1 = document.createTextNode((i + 1) + " ： ");
					td1.appendChild(textNode1);
					tr2.appendChild(td1);

					var td2 = document.createElement('td');
					var textNode2 = document.createTextNode(shiTiS[0]);
					td2.appendChild(textNode2);
					tr2.appendChild(td2);

					var td3 = document.createElement('td');
					var textNode3 = document.createTextNode(shiTiS[1]);
					td3.appendChild(textNode3);
					tr2.appendChild(td3);

					var td4 = document.createElement('td');
					var textNode4 = document.createTextNode(shiTiS[2]);
					td4.appendChild(textNode4);
					tr2.appendChild(td4);

					var td5 = document.createElement('td');
					
					if(shiTiS[3] == "0")
					{
					
						var textNode5 = document.createTextNode("错误");
						td5.appendChild(textNode5);
					}
					else
					{
						var textNode5 = document.createTextNode("正确");
						td5.appendChild(textNode5);
					}
					
					
					tr2.appendChild(td5);





					tableTag.append(tr2);

				}

				
				
			//	$("#selectInput").hide();
				$("#zuoTiInput").hide();
				$("#zuoTiFuZhu").hide();
				$("#show").show();
				console.log("success");
			})
			.fail(function() {
				console.log("error");
			})
			.always(function() {
				console.log("complete");
			});
			
		});



		$("input").keyup(function(event) {
			/* Act on the event */
			var keynode = event.which;
			if(keynode == 13)
			{
				//alert("enter");
				if($("#zuoTiInput").is(":visible"))
				{
            		$("#zuoTiInputTiJiao").click();
				}
				
				
			}
		});


		 
	});


