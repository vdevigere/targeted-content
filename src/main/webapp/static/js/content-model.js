var app = app || {};

app.Content = Backbone.Model.extend({});

app.ContentCollection = Backbone.Collection.extend({
	model : app.Content,
	activeOnly : false,
	tags : [],
	url : function() {
		var url = '/targeted-content/api/content';
		if (this.activeOnly) {
			url += '/active'
		}
		// Build queryString
		var queryString='';
		if (this.tags.length > 0) {
			queryString = '?'
			for (tag in this.tags) {
				queryString += 'tags=' + this.tags[tag] + '&'
			}
		}
		fullURL = url + queryString;
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

	addTag : function(tag) {
		console.log('adding tag...');
		console.log(tag);
		this.tags.push(tag);
		this.fetch({
			reset : true
		});
		return this;
	},

	removeTag : function(tag) {
		console.log('removing tag...');
		console.log(tag);
		var index = this.tags.indexOf(tag);
		this.tags.splice(index, 1);
		console.log(this.tags);
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
