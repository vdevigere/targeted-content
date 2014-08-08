var Content = Backbone.Model.extend({
	urlRoot : '/targeted-content/api/content'
});

var ContentTableView = Backbone.View.extend({
	el : $("#contentList"),

	initialize : function() {
		this.render();
	},

	render : function() {
		console.log('rendering...');
		var dataURL = "http://localhost:8080/targeted-content/api/content/all";
		$.getJSON(dataURL, function(json) {
			console.log('Got JSON' + JSON.stringify(json));
			var wrap = {
				objects : json
			};
			var template = Handlebars.compile($('#content-row').html());
			var html = template(wrap);
			console.log(html);
		});
	}
});