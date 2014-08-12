var contentModule = (function(module){
	Content = Backbone.Model.extend({});

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

		findActive : function() {
			this.activeOnly = true;
			this.fetch({
				reset : true
			});
			return this;
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

		findAll : function() {
			this.activeOnly = false;
			this.fetch({
				reset : true
			});
			return this;
		}
	});

	return module;
})(contentModule || {});