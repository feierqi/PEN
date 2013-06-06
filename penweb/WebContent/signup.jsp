<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
	<%
		// Instantiate the web controller and grab id paramter
		WebController webcon = new WebController();
		List<ICategory> cats = webcon.getCategories();
		String loginName = (String) session.getAttribute("name");
		IUser user = null;
	
		if (loginName != null) {
			response.sendRedirect("/penweb");
		}
	%>
	<title>PEN &middot; Edit Example</title>
<%@include file="includes/head/tags" %>
</head>
<body>
<div class="modal"></div>
<%@include file="includes/header" %>
<div class="bar">
	<div class="left">
		<a href="edit.jsp"><div class="button green">New Example</div></a>
	</div>
	<div class="right">
		<h1>Sign Up</h1>
	</div>
</div>
<div class="content">
	<div class="left">
		<h1>Public Examples</h1>
		<ul>
			<% int num = webcon.getAllPublicExamples().size(); %>
			<a href="index.jsp"><li>All Examples (<%=num %>)</li></a>
			<% for (ICategory c : cats) {
					num = c.getPublicExamples().size();
				 %>
				<a href="index.jsp?cat=<%=c.getId() %>"><li><%=webcon.escapeHtml(c.getTitle())%> (<%=num %>)</li></a>
			<%} %>
		</ul>
		<a href="/penweb/error.jsp?err=5"><div class="button black-wide">New Category</div></a>
	</div>
	<div class="right">
		<p class="error"></p>
		<form action="addUser" method="post" id="signUp" name="signUpForm" onsubmit="return checkSignUpSubmit();">
			<input type="text" name="loginname" />
			<input type="text" name="displayname" />
			<input type="password" name="password" />
			<input type="password" name="confirm_password" />
			<input type="submit" id="createAccountButton" value="Create Account" />
		</form>

		<form class="barForm">
			
		</form>

	</div>
</div>
<%
	// Close the webcon
	webcon.close();
%>
</body>
</html>