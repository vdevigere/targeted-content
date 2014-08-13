var contentModule = (function(module) {
	Content = Backbone.Model.extend({
		urlRoot : '/targeted-content/api/content'
	});

	ContentCollection = Backbone.Collection.extend({
		model : Content,
		activeOnly : false,
		tags : [],

		getQueryString : function() {
			var qs = "";
			if (this.activeOnly) {
				qs += 'activeOnly=' + this.activeOnly + '&';
			}
			for (tag in this.tags) {
				qs += 'tags=' + this.tags[tag] + '&'
			}
			if (qs) {
				return '?' + qs;
			} else {
				return qs;
			}
		},

		url : function() {
			var url = '/targeted-content/api/content';
			fullURL = url + this.getQueryString();
			console.log('Fetch URL=' + fullURL);
			return fullURL;
		},

		findByTag : function(csvTagList) {
			if (csvTagList) {
				this.tags = csvTagList.split(',');
			} else {
				this.tags = [];
			}
			this.fetch({
				reset : true
			});
			return this;
		},

		find : function(activeOnly) {
			this.activeOnly = activeOnly;
			this.fetch({
				reset : true
			});
			return this;
		}
	});

	return module;
})(contentModule || {});