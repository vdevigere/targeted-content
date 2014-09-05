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

	TagCloudItem = Backbone.Model.extend();

	TagCloudCollection = Backbone.PageableCollection.extend({
		model : TagCloudItem,
		url : '/targeted-content/api/tagCloud'
	});
	return module;
})(contentModule || {});