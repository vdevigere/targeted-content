var contentModule = (function(module) {
	Content = Backbone.Model.extend();

	ContentCollection = Backbone.Collection.extend({
		model : Content,
		url : '/targeted-content/api/content',
	});

	return module;
})(contentModule || {});