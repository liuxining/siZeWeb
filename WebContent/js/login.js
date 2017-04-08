
	$(document).ready(function(){
		//页面加载完成即执行，利用ajax获取用户是否已登录,若返回值为no，则说明没有登录，显示登录div，否则在登录信息div显示已登录，2秒后跳转至主页
		$.get('CheckHasLoginSL',function(data){
			if(data != "no"){

				var userInfo = data.split(" ");
				$("input:text(name='username')").val(userInfo[0]);
				$("input:password(name='password')").val(userInfo[0]);
			
			
			}

			
			
				
			$("#input").show();
			$("input:text[name='username']").focus();
			
			
		});

		//点击登录按钮时间，验证表单不能为空，不空则提交，空则给出警告信息
		$("#bt2").click(function(){
			$("#userInfo").text("");
			$("#pwdInfo").text("");
			
			if($("input:text[name='username']").val() == "")
			{
				$("#userInfo").text("用户名不能为空");
				$("input:text[name='username']").focus();
			}


			else if($("input:password[name='pwd']").val() == "")
			{
				$("#pwdInfo").text("密码不能为空");
				$("input:password[name='pwd']").focus();
			}

			else
			{
				$.ajax({
					url: 'LoginSL',
					type: 'POST',
					//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
					data: {username: $("input:text[name='username']").val(),password:$("input:password[name='pwd']").val()},
				})
				.done(function(data) {
					if(data == "用户名不存在" || data == "密码错误"){
						$("#loginResult").text(data);
					}
					else{
						location.href="index.jsp";
					}

					console.log("success");
				})
				.fail(function() {
					console.log("error");
				})
				.always(function() {
					console.log("complete");
				});
				
				
			}
		});

		$("input").keyup(function(event) {
			/* Act on the event */
			var keynode = event.which;
			if(keynode == 13)
			{
				if($("#input").is(":visible"))
				{
					$("#bt2").click();
				}
			}
		});

	});
	

