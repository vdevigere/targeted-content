var contentModule = (function(module) {
	ContentDetailView = Backbone.View.extend({
		template : Handlebars.compile($('#content-row-template').html()),

		render : function() {
			var html = this.template(this.model.attributes);
			this.$el.append(html);
			return this;
		}
	});

	// The Content List Table
	ContentListView = Backbone.View.extend({
		el : $("#searchResultsForm"),

		template : Handlebars.compile($('#content-container-template').html()),

		events : {
			'click #validOnly' : 'toggleValidContent', // Valid Only Checkbox
			'change .tags' : 'tagChanged',
		},

		initialize : function() {
			// Listen to "reset" events on model and render view if model is
			// reset.
			this.listenTo(this.collection, "reset", this.render);

			// Render on initialize
			this.render();
		},

		render : function() {
			// Render the Headers
			var header = this.template();
			var contentContainer = this.$el.find('.contentContainer');
			contentContainer.html(header);

			// Render each row of collection.
			this.collection.each(function(contentData) {
				var contentView = new ContentDetailView({
					el : this.$el.find('tbody'),
					model : contentData
				});
				contentView.render();
			}, this);
		},

		toggleValidContent : function(e) {
			if (e.currentTarget.checked) {
				this.collection.findActive();
			} else {
				this.collection.findAll();
			}
		},

		tagChanged : function(e) {
			this.collection.findByTag(e.currentTarget.value);
		},
	});

	// public
	module.initialize = function() {
		var contentListView = new ContentListView({collection: new ContentCollection()});
	}

	return module;
})(contentModule || {});

contentModule.initialize();
