<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<%
		// Instantiate the webcon
		WebController webcon = new WebController();
	
		// Check if we are showing all or category
		int error = 0;
		if (request.getParameterMap().containsKey("err")) {
			error = Integer.parseInt(request.getParameter("err"));
		}
		
		String loginName = (String) session.getAttribute("name");
		IUser user = null;
		if (loginName != null) {
			user = webcon.getUserByLoginName(loginName);
		}
	%>
	<title>PEN &middot; Error</title>
<%@include file="includes/head/tags" %>
</head>
<body>
<%@include file="includes/popover/createCategory" %>
<%@include file="includes/header" %>
<div class="bar">
	<div class="left">
		<a href="edit.jsp"><div class="button green">New Example</div></a>
	</div>
	<div class="right">
		<h1>Error</h1>
	</div>
</div>
<div class="content">
	<div class="left">
		<h1><%if (user != null) { %>Examples<%} else { %>Public Examples<%} %></h1>
		<ul>
			<% 
				int num;
				if (user == null) {
					num = webcon.getAllPublicExamples().size();
				}
				else {
					num = webcon.getVisibleExamples(user).size();
				}
			%>
			<%List<ICategory> cats = webcon.getCategories(); %>
			<a href="index.jsp"><li>All Examples (<%=num %>)</li></a>
			<% for (ICategory c : cats) {
				if (user == null) {
					num = c.getPublicExamples().size();
				}
				else {
					num = c.getVisibleExamples(user).size();
				} %>
				<a href="index.jsp?cat=<%=c.getId() %>"><li><%=webcon.escapeHtml(c.getTitle())%> (<%=num %>)</li></a>
			<%} %>
		</ul>
		<a href="<% if (user != null) { %>javascript:newCategory();<%} else { %>/penweb/error.jsp?err=5<%}%>"><div class="button black-wide" id="createCategoryButton">New Category</div></a>
	</div>
	<div class="right">
		<%
			switch (error) {
			case 1:%>
				<p>The user name you chose is unavailable.</p>
			<%
				break;
			case 2:%>
				<p>Invalid username and password.</p>
			<% 
				break;
			case 3:	%>
				<p>You must be logged in to create a new example.</p>
			<%
				break;
			case 4: %>
				<p>The category name you chose is unavailable.</p>
			<%
				break;
			case 5: %>
				<p>You must be logged in to create a new category.</p>
			<%
				break;
			case 6: %>
				<p>Incorrect example id</p>
			<%
				break;
			case 7: %>
				<p>Example name already taken</p>
			<%
				break;
			default: %>
				<p>Invalid error message.</p>
			<%
				break;
			}
		%>
	</div>
</div>
<%
	// Close the web controller
	webcon.close();
%>
</body>
</html>