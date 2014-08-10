//Initialize Backbone Views
var app = app || {};

$(function() {
	// Activate Tag-it widget
	app.tagIt = $(".tags").tagit({
		fieldName : "tags",
		afterTagAdded : function(event, ui){
			Backbone.trigger('tag:addTag', ui);
		},

		afterTagRemoved : function(event, ui){
			Backbone.trigger('tag:removeTag', ui)
		}
	});

	// On clicking the Add Content Button, add a new form to the dom.
	$("#addContentBtn").click(function(e) {
		var contentHtml = $("#content-template").html();
		$("#content-form-group").append(contentHtml);
		return false;
	});

	// On clicking the trash can icon, delete the content form.
	$("#content-form-group").on("click", ".deleteContent", function(e) {
		$(this).parents("#content-form").remove();
		return false;
	});
});