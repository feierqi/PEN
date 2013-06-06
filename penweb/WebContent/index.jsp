<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<%
		// Instantiate the webcon
		WebController webcon = new WebController();
		// Check if we are showing all or category
		ICategory cat = null;
		if (request.getParameterMap().containsKey("cat")) {
			cat = webcon.getCategoryById(Long.parseLong(request.getParameter("cat")));
		}
		String loginName = (String) session.getAttribute("name");
		IUser user = null;
		if (loginName != null) {
			user = webcon.getUserByLoginName(loginName);
		}
		List<IExample> ex;
		if (cat == null) {
			if (user == null) {
				ex = webcon.getAllPublicExamples();
			}
			else {
				ex = webcon.getVisibleExamples(user);
			}
		}
		else {
			if (user == null) {
				ex = cat.getPublicExamples();
			}
			else {
				ex = cat.getVisibleExamples(user);
			}
		}
	%>
	<title>PEN &middot; <%if (cat == null) { %>All Examples (<%= ex.size() %>)<%} else { %><%=webcon.escapeHtml(cat.getTitle()) %> (<%=ex.size() %>)<%} %></title>
<%@include file="includes/head/tags" %>
</head>
<body>
<%@include file="includes/popover/createCategory" %>
<%@include file="includes/popover/deleteCategory" %>
<%@include file="includes/popover/searchCategory" %>
<%@include file="includes/header" %>
<div class="bar">
	<div class="left">
		<a href="edit.jsp"><div class="button green">New Example</div></a>
	</div>
	<div class="right">
		<%if (cat == null) {%>
			<h1>All Examples (<%= ex.size() %>)</h1>
		<%} else {%>
			<h1><%=webcon.escapeHtml(cat.getTitle()) %> (<%=ex.size() %>)</h1>
		<%} %>
		
		
			<form class="barForm" method="post" action="deleteCategory" id="deleteCategoryForm">
			<% if (cat!=null && user!=null && cat.getOwner()==user) { %>
			<input type="hidden" name="cid" value="<%=cat.getId()%>"/>
			<a href="javascript:<%if ((cat.getExampleList().size()==0)) {%>deleteCategory();<%} else {%>deleteCategoryCheck();<%}%>"><input type="button" class="button black" id="deleteCategoryButton" value="Delete" /></a>
			<%}%>
			<a href="javascript:searchCategory();"><input type="button" class="button green" value="Search" id="searchCategoryButton" /></a>
			</form>
		</form>
		
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
			<a href="index.jsp"><li <% if (cat == null) {%>class="selected"<%} %>>All Examples (<%= num %>)</li></a>
			<%
				List<ICategory> cats = webcon.getCategories();
				for (ICategory c : cats) {
					if (user == null) {
						num = c.getPublicExamples().size();
					}
					else {
						num = c.getVisibleExamples(user).size();
					}
			%>
			<a href="index.jsp?cat=<%=c.getId() %>"><li <% if ((cat != null) && (c.equals(cat))) { %>class="selected"<%} %>> <%= webcon.escapeHtml(c.getTitle()) %> (<%= num %>)</li></a>
			<% } %> 
		</ul>
		<a href="<% if (user != null) { %>javascript:newCategory();<%} else { %>/penweb/error.jsp?err=5<%}%>"><div class="button black-wide" id="createCategoryButton">New Category</div></a>
	</div>
	<div class="right">
		<ul class="entrylist">
			<%	for (IExample e : ex) { %>
						<a href="edit.jsp?id=<%=e.getId()%>">
							<li>
								<h1><%= webcon.escapeHtml(e.getTitle()) %></h1>
								<div class="fade"></div>
								<div class="code"><%= webcon.escapeHtml(e.getCode()) %></div>
							</li>
						</a>
			<%}%>
		</ul>
	</div>
</div>
<%
	// Close the web controller
	webcon.close();
%>
</body>
</html>
