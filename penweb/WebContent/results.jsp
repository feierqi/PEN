<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<%
		// Instantiate the webcon
		WebController webcon = new WebController();
		// Check if we are showing all or category
		List<IExample> ex = null;
		String loginName = (String) session.getAttribute("name");
		IUser user = null;
		if (loginName != null) {
			user = webcon.getUserByLoginName(loginName);
		}
		if (request.getParameterMap().containsKey("lang")) {
			ex = webcon.getCodeByLanguageAndUser(user, request.getParameter("lang"));
		}

		List<ICategory> cats = webcon.getCategories();
		
	%>
	<title>PEN &middot; Search Results (<%if (ex!=null) {%><%= ex.size() %><%} else {%>0<%}%>) </title>
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
		<h1>Search Results (<%if (ex!=null) {%><%= ex.size() %><%} else {%>0<%}%>)</h1>		
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
		<ul class="entrylist">
			<% 
				if (ex != null) {
					for (IExample e : ex) { %>
					<a href="edit.jsp?id=<%=e.getId()%>">
						<li>
							<h1><%= webcon.escapeHtml(e.getTitle()) %></h1>
							<div class="fade"></div>
							<div class="code"><%= webcon.escapeHtml(e.getCode()) %></div>
						</li>
					</a>
				<%
					}
				}	
			%>
		</ul>
	</div>
</div>
<%
	// Close the web controller
	webcon.close();
%>
</body>
</html>