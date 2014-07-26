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
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/flick/jquery-ui.css">
<link href="/static/css/simple.css" rel="stylesheet">
<link href="/static/tag-it/css/jquery.tagit.css" rel="stylesheet">
</head>
<body>
	<div class="container">
		<tiles:insertAttribute name="header" />
	</div>
	<div class="container">
		<tiles:insertAttribute name="body" />
	</div>
	<div class="container">
		<tiles:insertAttribute name="footer" />
	</div>
	<!-- /.container -->

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
	<script
		src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/static/tag-it/js/tag-it.min.js"></script>
	<script type="text/javascript" src="/static/js/targeted-content.js"></script>
</body>
</html>
