var contentModule = (function(module) {
	Content = Backbone.Model.extend();

	ContentCollection = Backbone.PageableCollection.extend({
		model : Content,
		url : 'http://localhost:9000/api/content',
		state : {
			pageSize : 5
		},
		mode : "client" // page entirely on the client side
	});

	TagCloudItem = Backbone.Model.extend();

	TagCloudCollection = Backbone.PageableCollection.extend({
		model : TagCloudItem,
		url : 'http://localhost:9000/api/tagCloud'
	});
	return module;
})(contentModule || {});