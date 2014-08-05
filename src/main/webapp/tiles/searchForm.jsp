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
				<th></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="contentRow" items="${validContent }">
			<tr>
				<td>${contentRow.id }</td>
				<td>${contentRow.name }</td>
				<td>${contentRow.startDate }</td>
				<td>${contentRow.endDate }</td>
				<td>
					<a href="edit.html?id=${contentRow.id }"><span class="glyphicon glyphicon-edit"></span></a>
					<a href="delete.html?id=${contentRow.id }"><span class="glyphicon glyphicon-trash"></span></a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>
</div>