var app = app || {};

app.ContentView = Backbone.View.extend({
	el : $('#contentList'),

	template : Handlebars.compile($('#content-row').html()),

	render : function() {
		var html = this.template(this.model.attributes);
		this.$el.append(html);
		return this;
	}
});

app.ContentListView = Backbone.View.extend({
	el : $("#contentListView"),

	events : {
		'click #validOnly' : 'toggleValidContent' // Valid Only Checkbox
	},

	initialize : function(initialContent) {
		this.collection = new app.ContentCollection(initialContent);
		//Listen to "reset" events on model and render view if model is reset.
		this.listenTo(this.collection, "reset", this.render);
		this.render();
	},

	render : function() {
		//Clear Table
		this.$el.find('tbody').empty();
		//Render each row of collection.
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

});