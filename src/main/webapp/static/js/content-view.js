var contentModule = (function(module) {
	ContentForm = Backbone.View.extend({
		el : '#searchResultsForm',
		events : {
			'click #validOnly' : 'toggleValidContent', // Valid Only Checkbox
			'change .tags' : 'tagChanged',
		},

		initialize : function() {
			// Listen to "reset" events on model and render view if model is
			// reset.
			this.listenTo(this.collection, "reset", this.render);
			this.listenTo(this.collection, "change", this.render);
		},

		toggleValidContent : function(e) {
			this.collection.find(e.currentTarget.checked);
		},

		tagChanged : function(e) {
			this.collection.findByTag(e.currentTarget.value);
		},

		render : function() {
			contentTableView = new ContentTableView({
				collection : this.collection
			});
			this.$el.find('#contentContainer').html(contentTableView.render().el);
			return this;
		}
	});

	//View rendering the <tr> tag 
	ContentRowView = Backbone.View.extend({
		template : Handlebars.compile($('#content-row-template').html()),
		tagName : "tr",

		render : function() {
			var html = this.template(this.model.attributes);
			this.$el.append(html);
			return this;
		}
	});

	// The Content List Table. Renders the <table> tag and delegates to the view to render each row.
	ContentTableView = Backbone.View.extend({
		className : 'table table-striped table-bordered',
		tagName : 'table',
		headerTemplate : Handlebars.compile($('#content-container-template').html()),

		render : function() {
			// Render the Headers
			var header = this.headerTemplate();
			this.$el.html(header);

			// Render each row of collection and append to the tBody of the table.
			this.collection.each(function(contentData) {
				var contentView = new ContentRowView({
					model : contentData
				});
				this.append(contentView.render().el);
			}, this.$el.find('tbody'));

			return this;
		}
	});

	// public
	module.initialize = function() {
		var contentCollection = new ContentCollection();
		var contentForm = new ContentForm({
			collection : contentCollection
		});
		return contentForm;
	}

	return module;
})(contentModule || {});

var contentForm = contentModule.initialize();
