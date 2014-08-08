$(document).ready(function(){
	// BACKBONE CODE


	// Helper function to load external HBRs
	getTemplate = function(path, callback) {
		var source;
		var template;

		$.ajax({
			url : path,
			success : function(data) {
				source = data;
				template = Handlebars.compile(source);
				if (callback)
					callback(template);
			}
		});

	}

	var ContentTableView = Backbone.View
			.extend({
				el : $("#contentList"),

				initialize : function() {
					this.render();
				},

				render : function() {
					console.log('rendering...');
					var dataURL = "http://localhost:8080/targeted-content/api/content/all";
					$.getJSON(dataURL, function(json) {
						console.log('Got JSON'+JSON.stringify(json));
						var wrap = {
							objects : json
						};
						var template = Handlebars.compile($('#content-row').html());
						var html = template(wrap);
						console.log(html);
					});
				}
			});

	var contentList = new ContentTableView();
});