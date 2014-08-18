var contentModule = (function(module) {
	Content = Backbone.Model.extend();

	ContentCollection = Backbone.PageableCollection.extend({
		model : Content,
		url : '/targeted-content/api/content',
		state : {
			pageSize : 5
		},
		mode : "client" // page entirely on the client side
	});

	return module;
})(contentModule || {});