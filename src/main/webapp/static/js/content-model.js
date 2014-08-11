var app = app || {};

app.Content = Backbone.Model.extend({});

app.ContentCollection = Backbone.Collection.extend({
	model : app.Content,
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
