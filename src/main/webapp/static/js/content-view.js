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
		},

		toggleValidContent : function(e) {
			console.log('Checkbox : ' + e.currentTarget.checked);
			if (e.currentTarget.checked) {
				this.collection.findActive();
			} else {
				this.collection.findAll();
			}
		},

		tagChanged : function(e) {
			this.collection.findByTag(e.currentTarget.value);
		},

		render : function() {
			console.log('rendering table..');
			contentListView = new ContentListView({
				collection : this.collection
			});
			this.$el.find('#contentContainer').html(contentListView.render().el);
			return this;
		}
	});

	ContentDetailView = Backbone.View.extend({
		template : Handlebars.compile($('#content-row-template').html()),
		tagName : "tr",

		render : function() {
			console.log('rendering row');
			var html = this.template(this.model.attributes);
			this.$el.append(html);
			return this;
		}
	});

	// The Content List Table
	ContentListView = Backbone.View.extend({
		className : 'col-sm-12',

		template : Handlebars.compile($('#content-container-template').html()),

		render : function() {
			console.log('rendering list view..');
			// Render the Headers
			var header = this.template();
			this.$el.html(header);

			// Render each row of collection.
			this.collection.each(function(contentData) {
				var contentView = new ContentDetailView({
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
