$(document).ready(function(){
	// Watermarks for login
	$("input[name=loginname]").watermark("Username");
	$("input[name=password]").watermark("Password");

	// Watermarks for sign up
	$("#signUp input[name=loginname]").watermark("Username");
	$("#signUp input[name=displayname]").watermark("Display Name");
	$("#signUp input[name=password]").watermark("Password");
	$("#signUp input[name=confirm_password]").watermark("Confirm Password");
	
	// Watermarks for new categories
	$("#createCategory input[name=name]").watermark("Category Name");
	$("#createCategory textarea[name=desc]").watermark("Enter description...");			

	// Watermarks for new entry
	$("input[name=title]").watermark("Title");
	$("input[name=author]").watermark("Author");
	$("input[name=language]").watermark("Language");
	$("textarea[name=content]").watermark("Enter code here...");
	$("textarea#comment").watermark("Change comment?");

	// Click anywhere to hide a popover or comment block
	$(document).on("click", function() {
		$(".popover").fadeOut(250);
		$("#commentBlock").fadeOut(250);
	});

	// Prevent clicks on popovers or comment blocks from hiding popovers or comment blocks
	$("#commentBlock").click(function(event){ event.stopPropagation(); });
	$(".popover").click(function(event){ event.stopPropagation(); });
	
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
	if ($("input[name=language]").val() == "") {
		$(".error").html("Error: Please enter language.");
		$(".error").show();
		return false;
	}
	return true;
}

// Checks addCategory submission to make sure name isn't blank
function checkAddCategorySubmit() {
	if ($("#createCategory input[name=name]").val() == "") {
		$(".popover .error").html("Error: Please provide a category name.");
		$(".popover .error").show();
		return false;
	}
	return true;
}

// Check signUp submission to make sure fields aren't blank and passwords math
function checkSignUpSubmit() {
	if ($("#signUp input[name=loginname]").val() == "") {
		$(".error").html("Error: Please provide a username.");
		$(".error").show();
		return false;
	} else if ($("#signUp input[name=displayname]").val() == "") {
		$(".error").html("Error: Please provide a display name.");
		$(".error").show();
		return false;
	} else if ($("#signUp input[name=password]").val() == "") {
		$(".error").html("Error: Please provide a password.");
		$(".error").show();
		return false;
	} else if ($("#signUp input[name=password]").val() != $("#signUp input[name=confirm_password]").val()) {
		$(".error").html("Error: The passwords do not match.");
		$(".error").show();
		return false;
	}
	return true;
}

// Category popover
function newCategory() {
	var buttonPosition = $("#createCategoryButton").offset();
	var popoverPositionTop = buttonPosition.top - 10;
	var popoverPositionLeft = buttonPosition.left + $("#createCategoryButton").width() + 25;
	$("#createCategory").css({"top": popoverPositionTop + "px", "left": popoverPositionLeft + "px"});
	$("#createCategory").fadeIn(200);
}

// Entry options popover
function entryOptions() {
	var buttonPosition = $("#entryOptionsButton").offset();
	var popoverPositionTop = buttonPosition.top + 55;
	var popoverPositionLeft = buttonPosition.left - 150;
	$("#entryOptions").css({"top": popoverPositionTop + "px", "left": popoverPositionLeft + "px"});
	$("#entryOptions").fadeIn(200);
}

// Duplicate popover
function duplicateIntoCategories() {
	var buttonPosition = $("#entryOptionsButton").offset();
	var popoverPositionTop = buttonPosition.top + 55;
	var popoverPositionLeft = buttonPosition.left - 235;
	$("#duplicateIntoCategories").css({"top": popoverPositionTop + "px", "left": popoverPositionLeft + "px"});
	$(".popover").fadeOut(125);
	$("#duplicateIntoCategories").fadeIn(125);
}

// Delete category
function deleteCategory () {
	$("#deleteCategoryForm").submit();
}

// Delete category check popover
function deleteCategoryCheck() {
	var buttonPosition = $("#deleteCategoryButton").offset();
	var popoverPositionTop = buttonPosition.top + 55;
	var popoverPositionLeft = buttonPosition.left - 235;
	$("#deleteCategory").css({"top": popoverPositionTop + "px", "left": popoverPositionLeft + "px"});
	$("#deleteCategory").fadeIn(250);
}

function searchCategory() {
	var buttonPosition = $("#searchCategoryButton").offset();
	var popoverPositionTop = buttonPosition.top + 55;
	var popoverPositionLeft = buttonPosition.left - 200;
	$("#searchCategory").css({"top": popoverPositionTop + "px", "left": popoverPositionLeft + "px"});
	$("#searchCategory").fadeIn(250);
}

// Save example
function saveExample() {
	$("form[name=editForm] input[name=comment]").val($("textarea#comment").val());
	if(document.editForm.onsubmit()) {
		document.editForm.submit();
	}
}

// Comment block on hover over Save Example
function showCommentBlock() {
	$("#commentBlock").fadeIn(250);
}

// Hide comment block
function hideCommentBlock() {
	$("#commentBlock").fadeOut(250);
}