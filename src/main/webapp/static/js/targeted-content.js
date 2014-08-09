//Initialize Backbone Views
var app = app || {};

$(function() {
	// Activate Tag-it widget
	$(".tags").tagit({
		fieldName : "tags"
	});

	// On clicking the Add Content Button, add a new form to the dom.
	$("#addContentBtn").click(function(e) {
		var contentHtml = $("#content-template").html();
		$("#content-form-group").append(contentHtml);
		return false;
	});

	// On clicking the trash can icon, delete the content form.
	$("#content-form-group").on("click", ".deleteContent", function(e) {
		console.log("button clicked");
		$(this).parents("#content-form").remove();
		return false;
	});

	// Activate tabs
	$('#myTab a').click(function(e) {
		e.preventDefault()
		$(this).tab('show')
	});

	var contentCollection = new app.ContentCollection();
	var results = contentCollection.fetch();
	results.done(function(data){
		console.log(data);
		app.ResultsView = new app.ContentListView(data);
	});
});