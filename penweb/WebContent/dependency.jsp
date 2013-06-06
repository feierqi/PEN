<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
	<%
		// Instantiate the webcon
		WebController webcon = new WebController();
		// Check if we are showing all or category
		ICategory cat = null;
		Long eid = null;
		IExample tex = null;
		boolean isSet = false;
		ArrayList<IExample> dependencies = null;
		if (request.getParameterMap().containsKey("cat")) {
			cat = webcon.getCategoryById(Long.parseLong(request.getParameter("cat")));
		}
		if (request.getParameterMap().containsKey("eid")) {
			eid = Long.parseLong(request.getParameter("eid"));
			tex = webcon.getExampleById(eid);
			if (tex != null) {
				dependencies = tex.getDependency();
			}
			else { response.sendRedirect("/penweb"); isSet = true; }
		} else {
			if (!isSet) {response.sendRedirect("/penweb"); isSet = true; }
		}
		
		String loginName = (String) session.getAttribute("name");
		IUser user = null;
		if (loginName != null) {
			user = webcon.getUserByLoginName(loginName);
		}
		else { 
			if (!isSet) { response.sendRedirect("/penweb"); isSet = true; }
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
	<title>PEN &middot; <%if (cat == null) { %>All Examples (<%= webcon.getNumEntries() %>)<%} else { %><%=webcon.escapeHtml(cat.getTitle()) %> (<%=cat.getExampleList().size() %>)<%} %></title>
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
			<h1>Select new dependency</h1>
			<a href="edit.jsp?id=<%= eid %>"><div class="button black barForm">Cancel</div></a>
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
			<a href="dependency.jsp?eid=<%= eid %>"><li <% if (cat == null) {%>class="selected"<%} %>>All Examples (<%= num - 1 %>)</li></a>
			<%
				List<ICategory> cats = webcon.getCategories();
				for (ICategory c : cats) {
					if (user == null) {
						num = c.getPublicExamples().size();
					}
					else {
						num = c.getVisibleExamples(user).size();
						if (c.getExampleIds().contains(eid)) {
							num--;
						}
					}
			%>
			<a href="dependency.jsp?cat=<%=c.getId() %>&eid=<%= eid %>"><li <% if ((cat != null) && (c.equals(cat))) { %>class="selected"<%} %>> <%= webcon.escapeHtml(c.getTitle()) %> (<%= num %>)</li></a>
			<% } %> 
		</ul>
		<a href="<% if (user != null) { %>javascript:newCategory();<%} else { %>/penweb/error.jsp?err=5<%}%>"><div class="button black-wide" id="createCategoryButton">New Category</div></a>
	</div>
	<div class="right">
		<ul class="entrylist">
			<% 
				for (IExample e : ex) { 
					boolean depender = false;
					if (dependencies != null && !(dependencies.isEmpty())) {
						depender =dependencies.contains(e);
					}
					if (!e.getId().equals(eid)) {%>
					<a href="addDependency?eid=<%= eid %>&did=<%=e.getId()%>">
					<li
						<%
								if (depender) { %>
									class="selected"
								<%}
						%>
					>
						<h1><%= webcon.escapeHtml(e.getTitle()) %></h1>
						<div class="fade"></div>
						<div class="code"><%= webcon.escapeHtml(e.getCode()) %></div>
					</li>
				</a>
				<%	}
				} %>
		</ul>
	</div>
</div>
<%
	// Close the web controller
	webcon.close();
%>
</body>
</html>