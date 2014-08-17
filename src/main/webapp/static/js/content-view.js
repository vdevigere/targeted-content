var contentModule = (function(module) {
	ContentForm = Backbone.View.extend({
		el : '#searchResultsForm',
		events : {
			'click #validOnly' : 'resetCollection', // Valid Only Checkbox
			'change .tags' : 'resetCollection',
		},

		initialize : function() {
			// Listen to "reset" events on model and render view if model is
			// reset.
			this.listenTo(this.collection, "reset", this.render);
			this.listenTo(this.collection, "change", this.render);

			// Load initial data
			if (typeof initialContent !== 'undefined') {
				this.collection.reset(initialContent);
			}
		},

		resetCollection : function(e) {
			var tags = (this.$el.find('.tags').val()) ? this.$el.find('.tags')
					.val().split(',') : [];
			var activeOnly = this.$el.find('#validOnly').is(':checked');
			this.collection.fetch({
				data : {
					activeOnly : activeOnly,
					tags : tags
				},
				reset : true
			})
		},

		render : function() {
			contentTableView = new ContentTableView({
				collection : this.collection
			});
			this.$el.find('#contentContainer').html(
					contentTableView.render().el);
			return this;
		}
	});

	// View rendering the <tr> tag
	ContentRowView = Backbone.View.extend({
		template : Handlebars.compile($('#content-row-template').html()),
		tagName : "tr",

		render : function() {
			var html = this.template(this.model.attributes);
			this.$el.append(html);
			return this;
		}
	});

	// The Content List Table. Renders the <table> tag and delegates to the view
	// to render each row.
	ContentTableView = Backbone.View.extend({
		className : 'table table-striped table-bordered',
		tagName : 'table',
		headerTemplate : Handlebars.compile($('#content-container-template')
				.html()),

		render : function() {
			// Render the Headers
			var header = this.headerTemplate();
			this.$el.html(header);

			// Render each row of collection and append to the tBody of the
			// table.
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
		var router = new Dashboard();
		Backbone.history.start({
			pushState : true,
			root : "/targeted-content/page/dashboard/"
		})
		var contentCollection = new ContentCollection();
		var contentForm = new ContentForm({
			collection : contentCollection
		});
		return contentForm;
	}

	return module;
})(contentModule || {});

var contentForm = contentModule.initialize();
