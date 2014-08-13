/**
 *
 */
var contentModule = (function(module) {

	Dashboard = Backbone.Router.extend({
		routes : {
			"edit.html?id=:id" : "editContent",
			"delete.html?id=:id" : "deleteContent"
		},

		editContent : function(){
			console.log("edit clicked");
		},

		deleteContent : function(){
			console.log("delete clicked");
		}
	});

	return module
})(contentModule || {});