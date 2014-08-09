var app = app || {};

app.Content = Backbone.Model.extend({});

app.ContentCollection = Backbone.Collection.extend({
	model : app.Content,
	activeOnly : false,
	tags : [],
	url : function() {
		var url = '/targeted-content/api/content';
		var queryString = '?'
		if (this.activeOnly) {
			url += '/active'
		} else {
			url += '/all'
		}
		// Build queryString
		for (tag in this.tags) {
			queryString += 'tags=' + tag + '&'
		}
		fullURL = url + queryString;
		console.log('Fetch URL='+fullURL);
		return fullURL;
	},

	findActive : function() {
		this.activeOnly = true;
		this.fetch({
			reset : true
		});
		return this;
	},

	findByTags : function(tags) {
		this.tags = tags;
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
