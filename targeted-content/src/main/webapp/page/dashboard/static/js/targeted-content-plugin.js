var TargetedContent = (function(module) {

	// View rendering the <tr> tag
	ContentRowView = Backbone.View
			.extend({
				template : Handlebars
						.compile("        <td>{{id}}</td>\n"
								+ "        <td>{{name}}</td > \n"
								+ "        <td>{{formatDate startDate \"short\"}}</td>\n"
								+ "        <td>{{formatDate endDate \"short\"}}</td >\n"
								+ "        <td>\n"
								+ "            <a href=\"edit.html?id={{id }}\"><span class=\"glyphicon glyphicon-edit\"></span></a>\n"
								+ "        </td>\n"
								+ "        <td>\n"
								+ "            <a href=\"delete.html?id={{id }}\"><span class=\"glyphicon glyphicon-trash\"></span></a>\n"
								+ "        </td>"),
				tagName : "tr",

				render : function() {
					var html = this.template(this.model.attributes);
					this.$el.append(html);
					return this;
				}
			});

	// The Content List Table. Renders the <table> tag and delegates to the view
	// to render each row.
	module.Grid = Backbone.View.extend({
		className : 'table table-striped table-bordered',
		tagName : 'table',
		headerTemplate : Handlebars.compile("               <thead>\n"
				+ "                    <tr>\n"
				+ "                        <th>#</th>\n"
				+ "                        <th>Name</th>\n"
				+ "                        <th>Start Date</th>\n"
				+ "                        <th>End Date</th>\n"
				+ "                        <th colspan=\"2\"></th>\n"
				+ "                    </tr>\n" + "                </thead>\n"
				+ "                <tbody>\n" + "                </tbody>"),

		initialize : function() {
			this.listenTo(this.collection, "reset", this.render);
			this.listenTo(this.collection, "change", this.render);
		},

		render : function() {
			console.log('rendering..');
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
			console.log(this.$el);
			return this;
		}
	});

	return module;
})(TargetedContent || {});
;