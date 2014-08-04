<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="home.html">${project.name}</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<c:forEach var="menuItem" items="${topMenu }">
					<li class="${menuItem.active }"><a href="${menuItem.link}">${menuItem.name }</a></li>
				</c:forEach>
				<li><a href="#contact">Contact</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</div>