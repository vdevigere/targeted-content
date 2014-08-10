<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="Viddu Devigere">
<link rel="icon" href="../../favicon.ico">
<title><tiles:getAsString name="title" /></title>
<!-- Bootstrap core CSS -->
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://code.jquery.com/ui/1.11.0/themes/flick/jquery-ui.css">
<link href="/static/targeted-content/css/simple.css" rel="stylesheet">
<link href="/static/tag-it/css/jquery.tagit.css" rel="stylesheet">
<!--############## TEMPLATES ##############-->
<script id="content-row" type="text/x-handlebars-template">
    <tr>
        <td>{{id}}</td>
        <td>{{name}}</td > 
        <td>{{formatDate startDate "short"}}</td>
        <td>{{formatDate endDate "short"}}</td >
		<td>
			<a href="edit.html?id={{id }}"><span class="glyphicon glyphicon-edit"></span></a>
		</td>
		<td>
			<a href="delete.html?id={{id }}"><span class="glyphicon glyphicon-trash"></span></a>
		</td>
    </tr>
</script>
<!-- ############## -->
</head>
<body>
	<div class="container">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="container">
		<tiles:insertAttribute name="status" ignore="true"/>
		<tiles:insertAttribute name="body" />
	</div>
	<div class="container">
		<tiles:insertAttribute name="footer" />
	</div>
	<!-- /.container -->

	<!-- ===================================Bootstrap core JavaScript ========================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/static/tag-it/js/tag-it.min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.1.2/backbone-min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/handlebars.js/2.0.0-alpha.4/handlebars.min.js"></script>
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.7.0/moment.min.js"></script>
	<script type="text/javascript" src="/static/targeted-content/js/handlebars-helpers.js"></script>	
	<script type="text/javascript" src="/static/targeted-content/js/content-model.js"></script>
	<script type="text/javascript" src="/static/targeted-content/js/content-view.js"></script>
	<script type="text/javascript" src="/static/targeted-content/js/targeted-content.js"></script>
</body>
</html>
