<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="ConfigWoorkbookWeb.ConfigWorkbookProgress"%>
<%@page import="ConfigWoorkbookWeb.sampletry"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="ConfigWoorkbookWeb.ConfigBookMain"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
<script>
	/*alert("SOS");
	
	var s =  getAttribute("status");
	alert("after getAttribute");
	var statusText = document.getElementById('status');
	alert("after getElementById");
	statusText.innerHTML = s;
	alert("EOS");*/
	function updateValue(value) {
		var spn = document.getElementById("sp");
		spn.innerHTML = value;
	}
</script>
</head>
<body>


	<div align="center">
		<img src="loader_circle.gif" alt="logo" align="middle" />
	</div>

	<div align="center">
		<span id="sp"> 0 </span>%
		<!-- ${status}  -->
		<p>
			Logging In..<br />

			<%
				String s, str_username, str_password, str_endpoint;
				str_username = request.getParameter("username");
				str_password = request.getParameter("password");
				str_endpoint = request.getParameter("endpoint");

				//ConfigWorkbookProgress.step1(str_username, str_password, str_endpoint);
				ConfigWorkbookProgress obj = new ConfigWorkbookProgress();
				obj.step1(str_username, str_password, str_endpoint);
			%>
			<script>
				updateValue(20)
			</script>

			Logged In Successfully..<br /> Retrieving Metadata Components..<br />

			<%
				obj.step2();
			%>

			<script>
				updateValue(70)
			</script>

			Metadata Retrieved..<br />Writing to Zip files...<br/>
			
			<%
				obj.step3();
			%>
			
			<script>
				updateValue(100)
			</script>
			Config Workbook Created...
		
			
			
			<p>
				value of u =
				<%=str_username%>
				value of p =
				<%=str_password%>
				value of e =
				<%=str_endpoint%>
			</p>




		</p>
	</div>





</body>
</html>