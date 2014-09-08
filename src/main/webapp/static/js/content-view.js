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

	SearchForm = Backbone.View.extend({
		el : '#searchForm',
		events : {
			'click #validOnly' : 'resetCollection', // Valid Only Checkbox
			'change .tags' : 'resetCollection', // Tag input box
		},

		initialize : function(options) {
			this.tagCloud = options.tagCloud;
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
			});
			this.tagCloud.fetch({
				data : {
					activeOnly : activeOnly,
					tags : tags
				},
				reset : true
			});
		},

	});

	TagCloudView = Backbone.View.extend({
		events : {
			'click a' : 'filterByTag'
		},

		filterByTag : function(e) {
			console.log($(e.currentTarget).data('tag'));
		},

		initialize : function(options) {
			this.listenTo(this.collection, "reset", this.render);
			this.listenTo(this.collection, "change", this.render);
			this.listenTo(this.collection, "add", this.render);
		},

		render : function() {
			console.log('rendering tag cloud');
			var tagList = '<div id="tags"><ul>';
			this.collection.each(function(val) {
				tagList += '<li><a href="#" data-weight="'
						+ val.attributes.weight + '" data-tag="'
						+ val.attributes.name + '">' + val.attributes.name
						+ ' (' + val.attributes.weight + ')</a></li>';
			});
			tagList += '</ul></div>';
			this.$el.html(tagList);
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

		// BackGrid Columns
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

		var contentCollection = new ContentCollection();

		var tagCloudCollection = new TagCloudCollection();

		// Initialize the Content Form
		var contentForm = new SearchForm({
			collection : contentCollection,
			tagCloud : tagCloudCollection
		});

		// Initialize the BackGrid instance
		var grid = new Backgrid.Grid({
			columns : columns,
			collection : contentCollection
		});

		// Initialize the Pager
		var paginator = new Backgrid.Extension.Paginator({
			collection : contentCollection
		});

		var tagCloudView = new TagCloudView({
			collection : tagCloudCollection
		});

		$('#contentGrid').append(grid.render().el);
		$('#contentGrid').append(paginator.render().el);
		$('#tagCloud').append(tagCloudView.render().el);

		contentCollection.fetch({
			reset : true
		});

		tagCloudCollection.fetch({
			reset : true
		});

		return contentForm;
	}

	return module;
})(contentModule || {});

var contentForm = contentModule.initialize();
