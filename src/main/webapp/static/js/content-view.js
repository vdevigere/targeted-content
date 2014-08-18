var contentModule = (function(module) {

	GlyphIconLink = Backgrid.UriCell.extend({
		className : "glyphicon-cell",

		uri : null,

		glyph : null,

		render : function() {
			this.$el.empty();
			var field = this.column.get("name");
			var value = this.model.get(field);
			this.$el.append($("<a>", {
				tabIndex : -1,
				href : this.uri + "?" + field + "=" + value,
				title : this.title,
				target : this.target
			}).html($("<span>", {
				"class" : "glyphicon " + this.glyph
			})));
			this.delegateEvents();
			return this;
		}
	});

	// BackGrid View
	var columns = [ {
		name : "id", // The key of the model attribute
		label : "ID", // The name to display in the header
		editable : false,
		cell : "string"
	}, {
		name : "name",
		label : "Name",
		cell : "string"
	}, {
		name : "startDate",
		label : "Start Date",
		cell : "date"
	}, {
		name : "endDate",
		label : "End Date",
		cell : "date"
	}, {
		name : 'id',
		label : "",
		editable : false,
		sortable : false,
		cell : GlyphIconLink.extend({
			uri : 'edit.html',
			glyph : 'glyphicon-edit'
		})
	}, {
		name : 'id',
		label : "",
		editable : false,
		sortable : false,
		cell : GlyphIconLink.extend({
			uri : 'delete.html',
			glyph : 'glyphicon-trash'
		})
	} ];

	ContentForm = Backbone.View.extend({
		el : '#searchResultsForm',
		events : {
			'click #validOnly' : 'resetCollection', // Valid Only Checkbox
			'change .tags' : 'resetCollection', // Tag input box
		},

		initialize : function() {
			// Initialize the BackGrid instance
			var grid = new Backgrid.Grid({
				columns : columns,
				collection : this.collection
			});

			var contentContainer = $('<div>', {
				'class' : 'row'
			}).append($('<div>', {
				'class' : 'col-sm-12 contentContainer'
			}));

			contentContainer.html(grid.render().el);
			this.$el.append(contentContainer);

			var paginator = new Backgrid.Extension.Paginator({
				collection : this.collection
			});

			this.$el.append(paginator.render().el);

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
