var app = app || {};

app.ContentView = Backbone.View.extend({
	el : $('#contentList'), // Typically, the tBody element of a table

	template : Handlebars.compile($('#content-row').html()),

	render : function() {
		var html = this.template(this.model.attributes);
		this.$el.append(html);// Add a row to tBody.
		return this;
	}
});

app.ContentListView = Backbone.View.extend({
	el : $("#contentListView"),

	events : {
		'click #validOnly' : 'toggleValidContent', // Valid Only Checkbox
	},

	initialize : function(initialContent) {
		this.collection = new app.ContentCollection(initialContent);

		// Listen to "reset" events on model and render view if model is reset.
		this.listenTo(this.collection, "reset", this.render);

		// Listen to the tag changed event.
		Backbone.on('tag:addTag', this.addTag, this);
		Backbone.on('tag:removeTag', this.removeTag, this);
		// Render on initialize
		this.render();
	},

	render : function() {
		// Clear Table
		this.$el.find('tbody').empty();

		// Render each row of collection.
		this.collection.each(function(content) {
			this.renderContent(content)
		}, this);
	},

	renderContent : function(content) {
		var contentView = new app.ContentView({
			model : content
		});
		contentView.render();
	},

	toggleValidContent : function(e) {
		if (e.currentTarget.checked) {
			console.log('fetching Valid Content...');
			this.collection.findActive();
		} else {
			console.log('fetching All Content');
			this.collection.findAll();
		}
	},

	addTag : function(tag) {
		this.collection.addTag(tag.tagLabel);
	},

	removeTag : function(tag) {
		this.collection.removeTag(tag.tagLabel);
	},

});

new app.ContentCollection().fetch().done(function(data) {
	new app.ContentListView(data);
});
