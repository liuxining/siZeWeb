
	$(document).ready(function() {
		$("input:text[name='username']").focus();	

		$("#zhuCe").click(function() {

			if($("#userInfo").text() == "用户名已存在")
			{
				$("input:text[name='username']").focus();	
				return;
			}
			else if($("#userInfo").text() == "用户名可用")
			{
				$("#pwd1Info").text("");
				$("#pwd2Info").text("");
			}
			else
			{
				$("#userInfo").text("");
				$("#pwd1Info").text("");
				$("#pwd2Info").text("");
			}
			var usernamePattern = /^[0-9a-zA-Z]+$/;
			if($("input:text[name='username']").val() == "")
			{
				$("#userInfo").text("请输入用户名");
				$("input:text[name='username']").focus();	
			}
			else if(!(usernamePattern.test($("input:text[name='username']").val())))
			{
				$("#userInfo").text("用户名应为字母或数字");
				$("input:text[name='username']").focus();	
			}
			else if($("input:text[name='username']").val() == "no")
			{
				$("#userInfo").text("用户名不能为no");
				$("input:text[name='username']").focus();
			}
			else if($("input:password[name='pwd1']").val() == "")
			{
				$("#pwd1Info").text("请输入密码");
				$("input:password[name='pwd1']").focus();
			}
			else if($("input:password[name='pwd2']").val() == "")
			{
				$("#pwd2Info").text("请再次输入密码");
				$("input:password[name='pwd2']").focus();
			}
			else if($("input:password[name='pwd1']").val() != $("input:password[name='pwd2']").val())
			{
				$("#pwd2Info").text("两次输入的密码不一致");
				$("input:password[name='pwd2']").focus();
			}
			else
			{
				$.ajax({
					url: 'LogonSL',
					type: 'POST',
					//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
					data: {username: $("input:text[name='username']").val(),password:$("input:password[name='pwd1']").val()},
				})
				.done(function(data) {
					if(data == "success")
					{
						$("#logonResult").text("注册成功,2秒后跳转至登录页面");
						setTimeout("location.href='login.html'",2000);
					}
					else
					{
						$("#logonResult").text("注册失败！");
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

		$("input:text[name='username']").change(function() {
			if($("input:text[name='username']").val() != "")
			{
				$.ajax({
					url: 'CheckUsernameHasSL',
					type: 'POST',
					//dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
					data: {username: $("input:text[name='username']").val()},
				})
				.done(function(data) {
					if(data == "用户名已存在")
					{
						$("#userInfo").text("用户名已存在");
						$("input:text[name='username']").focus();
					}
					else
					{
						$("#userInfo").text("用户名可用");
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
				$("#zhuCe").click();
			}
		});

	});