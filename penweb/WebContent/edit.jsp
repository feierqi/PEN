<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List,java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
	function checkSelected() {
		var selected = document.getElementById("language");
		if (selected.value == "other") {
			document.getElementById("otherlang").style.display = "block";
			document.getElementById("other").name = "language";
			selected.name = "";
		}
		else {
			document.getElementById("otherlang").style.display = "none";
			document.getElementById("other").name = "";
			selected.name = "language";
		}
	}
</script>
	<%
		// Instantiate the web controller and grab id paramter
		WebController webcon = new WebController();
		boolean isNewExample;
		boolean isOwner = false;
		Long id;
		IExample ex;
		if (!request.getParameterMap().containsKey("id")) {
			isNewExample = true;
			isOwner = true;
			ex = null;
			id = null;
		}
		else {
			id = Long.parseLong(request.getParameter("id"));
			ex = webcon.getExampleById(id);
			if (ex == null) {
				response.sendRedirect("/penweb/error.jsp?err=6");
				webcon.close();
				return;
			}
			isNewExample = false;
		}
		String loginName = (String) session.getAttribute("name");
		IUser user = null;
		if (loginName != null) {
			user = webcon.getUserByLoginName(loginName);
		}
		if (isNewExample) {
			if (user == null) {
				response.sendRedirect("/penweb/error.jsp?err=3");
				webcon.close();
				return;
			}
		}
		else {
			if (user != null) {
				if (ex.getOwner() == user) {
					isOwner = true;
				}
			}
			if (!isOwner && !ex.isPublic()) {
				response.sendRedirect("/penweb/");
				webcon.close();
				return;
			}
		}

		List<ICategory> cats = webcon.getCategories();
		
		%>

	<title>PEN &middot; Edit Example</title>
<%@include file="includes/head/tags" %>
</head>
<body>
<%if (!isNewExample) {%>
<%@include file="includes/popover/duplicateIntoCategories" %>
<%@include file="includes/popover/entryOptions" %>
<% } %>
<%@include file="includes/popover/createCategory" %>
<%@include file="includes/header" %>
<div class="bar">
	<div class="left">
		<a href="edit.jsp"><div class="button green">New Example</div></a>
	</div>
	<div class="right">
		<h1>
		<%	if (isNewExample) {%>
				New Example
			<%}	else {%>
				<%=webcon.escapeHtml(ex.getTitle())%>
			<%}%></h1>
			
			<%	if (!isNewExample) {%>
				<form class="barForm">
				<%if(user!=null){%>
				<a href="javascript:entryOptions();"><input type="button" class="button black" id="entryOptionsButton" value="Options" /></a>
				<%}%>
				<%if(isOwner){%>
				<input type="button" class="button green" id="saveButton" onClick="saveExample();" value="Save Example" <%if (!isNewExample) {%>onmouseover="showCommentBlock();"<%}%> />
				<%}%>
				</form>
			<%} else {%>
				<form class="barForm" <%if(user==null){%>style="display:none"<%}%>>
				<input type="button" class="button green" id="saveButton" onClick="saveExample();" value="Save Example" <%if (!isNewExample) {%>onmouseover="showCommentBlock();"<%}%> />
				</form>
			<%} %>
	</div>
</div>
	<div id="commentBlock">
		<textarea id="comment" onmouseout="hideCommentBlock();"></textarea>
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
		<form action="<%if (isNewExample) { %>addCode<%} else { %>modifyCode<%} %>" method="post" name="editForm" onsubmit="return checkAddCodeSubmit();">
			<input type="hidden" name="comment" />
			<p class="error"></p>
			<% if (!isNewExample) { %>
			<input type="hidden" name="eid" value="<%=id%>"/>
			<%} %>
			Author:
			<%if (!isNewExample) {%>
				<p><%=webcon.escapeHtml(ex.getAuthorsNames()) %></p>
			<%} else {%>
				<p><%=webcon.escapeHtml(user.getDisplayName()) %></p>
			<%} %>
			Title: *
			<input type="text" name="title" <%if(user==null || !isOwner){%>disabled="disabled"<%}%> <%if(!isNewExample) {%>value="<%=webcon.escapeHtml(ex.getTitle())%>"<%}%> />
			
			<input type="hidden" name="loginname" <%if(!isNewExample) {%>value="<%=ex.getAuthors().get(0).getLoginName()%>"<%} else {%>value="<%=user.getLoginName()%>"<%}%>/>
			Language: * <br>
			<%
			List<String> langs = webcon.readLangListFromFile();
			boolean isOther = false;
			if (!isNewExample) {
				if (!langs.contains(ex.getLanguage())) {
					isOther = true;
				}
			}
			%>
			<select <%if(!isOther) {%>name="language"<%}%> id="language" onchange="checkSelected()" <%if (user==null || !isOwner) { %>disabled=disabled<%}%>>
				<% 
				for (String s : langs) { %>
					<option <%if (!isNewExample) { if(ex.getLanguage().equalsIgnoreCase(s)) {%>selected="selected" <%}}%>value="<%=s%>"><%=s%></option>
				<%}%>
				<option <%if (isOther) {%>selected="selected"<%}%> value="other">Other...</option>
			</select> <br><br>
			<div id="otherlang" <%if(langs.size()!=0 && !isOther) {%>style="display:none;"<%}%>>
				Other Language:
				<input type="text" <% if (isOther) {%>name="language"<%}%> id="other" <%if (isOther) {%>value="<%=webcon.escapeHtml(ex.getLanguage())%>"<%}%> <%if (user==null || !isOwner) {%>disabled="disabled"<%}%>>
			</div>
			Code: *
			<textarea <%if(user==null || !isOwner){%>disabled="disabled"<%}%> name="content"><%if(!isNewExample) {%><%=ex.getCode()%><%}%></textarea>
			<font size="1"><p>* Required Fields</p></font>
			
				<p>Share with public? <input <%if(user==null || !isOwner){%>disabled="disabled"<%}%> <%if (!isNewExample) { if (ex.isPublic()) {%>checked<%}}%> type="checkbox" name="public"/></p>
			<%if (!cats.isEmpty()) {%>
			<p>Categories: <%if (user!=null || isOwner) {%>(Hold Ctrl to select multiple categories)<%}%></p>
			<select name="cids" multiple="multiple" <%if(!isOwner) {%>disabled="disabled"<%}%>>
				<% for (ICategory c : cats) { %>
					<option value="<%=c.getId() %>" <%if(c.getExampleIds().contains(id)) {%>selected<%}%>><%=webcon.escapeHtml(c.getTitle()) %></option>
				<%}%>
			</select>
			<%} %>
			<%
			if (!isNewExample) {
				ArrayList<IExample> dependencies = ex.getDependency();
				if (dependencies.size() != 0) {
			%>
			<p>Dependencies</p>
			<ul class="dependencyList">
				<%
					for (IExample e : dependencies) {
				%>
				<li><a href="edit.jsp?id=<%= e.getId() %>"><%= webcon.escapeHtml(e.getTitle()) %></a></li>
				<%} %>
			</ul>
			<%} }%>
			<% if (!isNewExample && (ex.getComment() != null && ex.getComment() != "")) { %><p>Last change: <%= webcon.escapeHtml(ex.getComment()) %></p> <%} %>
		</form>
	</div>
</div>
<%
	// Close the webcon
	webcon.close();
%>
</body>
</html>
