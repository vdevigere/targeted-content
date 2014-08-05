<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<form class="form-horizontal" role="form" action="search.html" method="GET">
		<div class="form-group">
			<div class="col-sm-10">
				<ul class="tags">
				</ul>
<!-- 				<input type="text" class="form-control" id="content-tags" name="tags" placeholder="Enter Tags to Filter" /> -->
			</div>
			<div class="col-sm-2"><button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button></div>
		</div>
</form>
<div class="container">
	<div class="row">
	<table class="table table-striped table-bordered col-sm-12">
		<thead>
			<tr>
				<th>#</th>
				<th>Name</th>
				<th>Start Date</th>
				<th>End Date</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="contentRow" items="${validContent }">
			<tr>
				<td><a href="edit.html?id=${contentRow.id }">${contentRow.id }</a></td>
				<td>${contentRow.name }</td>
				<td>${contentRow.startDate }</td>
				<td>${contentRow.endDate }</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</div>