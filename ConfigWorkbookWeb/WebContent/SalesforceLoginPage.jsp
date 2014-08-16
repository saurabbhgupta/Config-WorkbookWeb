<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<script>
	function validateForm() {
		var var_username = document.forms["frm"]["username"].value;
		if (var_username == null || var_username == "") {
			alert("Username name must be filled out");
			return false;
		}
		var var_password = document.forms["frm"]["password"].value;
		if (var_password == null || var_password == "") {
			alert("Password name must be filled out");
			return false;
		}
		var var_endpoint = document.forms["frm"]["endpoint"].value;
		if (var_endpoint == null || var_endpoint == "") {
			alert("Endpoint name must be filled out");
			return false;
		}
	}
</script>
<body background="14.jpg" background-size="100%" >
	
	<!-- 
	<img src="loader_circle.gif" alt="logo" align="middle" />
	  -->
	<form name="frm" method="post" action="ConfigServer" onsubmit="return validateForm()">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="22%">&nbsp;</td>
				<td width="78%">&nbsp;</td>
			</tr>
			<tr>
				<td>Username</td>
				<td><input type="text" name="username"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td>EndPoint</td>
				<td><input type="text" name="endpoint"></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<table>
			<tr>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
				<td><input type="submit" value="SUBMIT"></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><button type="button">CANCEL</button></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
		</table>
	</form>
	
</body>
</html>