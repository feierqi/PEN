<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="database.*,penweb.*,dataStructure.*,java.util.List"%>
<!DOCTYPE html>
<html>
<head>
	<%
		// Instantiate the web controller and grab id paramter
		WebController webcon = new WebController();
	%>
	<meta charset="UTF-8">
	<title>PEN &middot; Create Entry</title>
	<link rel="stylesheet" type="text/css" href="css/reset.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>
	<script src="js/jquery.watermark.min.js"></script>
	<script>
		$(document).ready(function(){
			// Watermarks for login
			$("input[name=username]").watermark("Username");
			$("input[name=password]").watermark("Password");

			// Watermarks for new categories
			$("#createCategory input[name=name]").watermark("Category Name");
			$("#createCategory textarea[name=desc]").watermark("Enter description...");			

			// Watermarks for new entry
			$("input[name=title]").watermark("Title");
			$("input[name=author]").watermark("Author");
			$("input[name=language]").watermark("Language");
			$("textarea[name=content]").watermark("Enter code here...");
		});

		// Checks addCode submission to make sure title and content aren't blank
		function checkAddCodeSubmit() {
			if ($("input[name=title]").val() == "") {
				$(".error").html("Error: Please provide a title.");
				$(".error").show();
				return false;
			}
			if ($("textarea[name=content]").val() == "") {
				$(".error").html("Error: Please enter code.");
				$(".error").show();
				return false;
			}					
			return true;
		}

		// Checks addCategory submission to make sure name isn't blank
		function checkAddCategorySubmit() {
			if ($("#createCategory input[name=name]").val() == "") {
				$(".modal .error").html("Error: Please provide a category name.");
				$(".modal .error").show();
				return false;
			}
			return true;
		}


		function newCategory() {
			$("#createCategory").show();
			$(".modal").show();		
		}
 
		function closeModal() {
			$(".modal").hide();
			$(".modal .sheet").hide();
		}
	</script>
</head>
<body>
<div class="modal">
	<div class="sheet" id="createCategory">
		<a href="javascript: closeModal();"><div class="close"></div></a>
		<h1>New Category</h1>
		<div class="modalContent">
			<form action="addCategory" method="post" onsubmit="return checkAddCategorySubmit();">
				<p class="error"></p>
				<input type="text" name="name" />
				<textarea name="desc"></textarea>
				<input type="submit" value="Create" />
			</form>
		</div>
	</div>
</div>
<div class="header">
	<h1>PEN</h1>
	<h2>The Programmer's<br>Examples Notebook</h2>
	<form name="login">
		<div class="input"><input type="text" name="username" /></div>
		<div class="input"><input type="password" name="password" /></div>
		<input type="submit" class="button blue" value="Log In" />
		<input type="button" class="button black" value="Sign Up" />
	</form>
</div>
<div class="bar">
	<div class="left">
		<a href="create.jsp"><div class="button green">New Entry</div></a>
	</div>
	<div class="right">
		<h1>New Entry</h1>
	</div>
</div>
<div class="content">
	<div class="left">
		<h1>My Examples</h1>
		<ul>
			<%List<ICategory> cats = webcon.getCategories(); %>
			<a href="index.jsp"><li>All Entries (<%=webcon.getNumEntries() %>)</li></a>
			<% for (ICategory c : cats) { %>
				<a href="index.jsp?cat=<%=c.getId() %>"><li><%=webcon.escapeHtml(c.getTitle())%> (<%=c.getExampleList().size() %>)</li></a>
			<%} %>
		</ul>
		<a href="javascript:newCategory();"><div class="button black-wide">New Category</div></a>
	</div>
	<div class="right">
		<form action="addCode" method="post" onsubmit="return checkAddCodeSubmit();">
			<p class="error"></p>
			<input type="text" name="title" />
			<input type="text" name="author" />
			<input type="text" name="language" />
			<textarea name="content"></textarea>
			<p>Categories</p>
			<% for (ICategory c : cats) { %>
				<p><input type="checkbox" name="cids" value="<%=c.getId() %>" /> <%=webcon.escapeHtml(c.getTitle()) %></p>
			<%} %>
			<input type="submit" class="button black" value="Save Entry" />
		</form>
	</div>
</div>
<%
	// Close the webcon
	webcon.close();
%>
</body>
</html>