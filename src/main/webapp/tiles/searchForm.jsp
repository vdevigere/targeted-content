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
		<ul class="nav nav-tabs col-sm-6" role="tablist">
		  <li class="active"><a href="#active_now" role="tab" data-toggle="tab">Active Now</a></li>
		  <li><a href="#all" role="tab" data-toggle="tab">All</a></li>
		</ul>
	</div>
	<div class="row tab-content">
		<div class=" col-sm-12 tab-pane active" id="active_now">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<th>#</th>
						<th>Name</th>
						<th>Start Date</th>
						<th>End Date</th>
						<th colspan="2"></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="validContentItem" items="${validContent }">
					<tr>
						<td>${validContentItem.id }</td>
						<td>${validContentItem.name }</td>
						<td>${validContentItem.startDate }</td>
						<td>${validContentItem.endDate }</td>
						<td>
							<a href="edit.html?id=${validContentItem.id }"><span class="glyphicon glyphicon-edit"></span></a>
						</td>
						<td>
							<a href="delete.html?id=${validContentItem.id }"><span class="glyphicon glyphicon-trash"></span></a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="tab-pane" id="all">
			<table class="table table-striped table-bordered">
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
				<c:forEach var="allContentItem" items="${allContent }">
					<tr>
						<td>${allContentItem.id }</td>
						<td>${allContentItem.name }</td>
						<td>${allContentItem.startDate }</td>
						<td>${allContentItem.endDate }</td>
						<td>
							<a href="edit.html?id=${allContentItem.id }"><span class="glyphicon glyphicon-edit"></span></a>
						</td>
						<td>
							<a href="delete.html?id=${allContentItem.id }"><span class="glyphicon glyphicon-trash"></span></a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>		
		</div>
	</div>
</div>