<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<script type="text/x-handlebars-template" id="content-template">
	<div id="content-form">
		<div class="form-group">
			<label for="content-data" class="col-sm-2">Content Data</label>
			<div class="col-sm-10">
				<textarea rows="4" class="form-control" id="content-data" name="content-data" placeholder="Enter Data"></textarea>
			</div>
		</div>
		<div class="form-group">
			<label for="content-weight" class="col-sm-2">Content Weight</label>
			<div class="col-sm-10">
				<div class="input-group input-group-sm">
					<input type="number" class="form-control" id="content-weight" name="content-weight" placeholder="Enter Weight (0-100)" />
					<span class="input-group-addon">%</span>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-2"></div>
			<div class="col-sm-10">
				<a href="#" class="deleteContent"><span class="glyphicon glyphicon-trash"></span></a>
			</div>
		</div>
	</div>
</script>
<form class="form-horizontal" role="form" action="../../api/content/save" method="post">
	<div class="container">
		<!-- START: CONTENT -->
		<div class="form-group">
			<label for="content-name" class="col-sm-2">Content Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="content-name" name="content-name" placeholder="Enter Name" value="${content.name }" />
			</div>
		</div>
		<!-- END: CONTENT -->
		
		
		<div class="form-group">
			<button type="button" class="btn btn-success col-sm-2" id="addContentBtn">Add Content</button>
			<div class="col-sm-10"></div> 
		</div>
		<!-- START: The Content Data Section -->
		<div class="panel panel-info">
			<div class="panel-heading">
				<h5 class="panel-title">Content</h5> 				
			</div>
			<div class="panel-body" id="content-form-group">
			<c:forEach var="contentItem" items="${content.contentDataSet }">
				<div id="content-form">
					<div class="form-group">
						<label for="content-data" class="col-sm-2">Content Data</label>
						<div class="col-sm-10">
							<textarea rows="4" class="form-control" id="content-data" name="content-data" placeholder="Enter Data">${contentItem.data }</textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="content-weight" class="col-sm-2">Content Weight</label>
						<div class="col-sm-10">
							<div class="input-group input-group-sm">
								<input type="number" class="form-control" id="content-weight" name="content-weight" placeholder="Enter Weight (0-100)" value="${contentItem.weight }"/>
								<span class="input-group-addon">%</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-2"></div>
						<div class="col-sm-10">
							<a href="#" class="deleteContent"><span class="glyphicon glyphicon-trash"></span></a>
						</div>
					</div>
				</div>
			</c:forEach>
			</div>
		</div>
		<!-- END: Content Data Section -->
		<div class="form-group">
			<button type="button" class="btn btn-success col-sm-2" id="addTargetBtn">Add Target</button>
			<div class="col-sm-10"></div> 
		</div>
		
		
		<!-- START: TARGET SECTION -->
		<div class="panel panel-info">
			<div class="panel-heading">
				<h5 class="panel-title">Targets</h5>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label for="start-date" class="col-sm-3">Start Date</label>
					<fmt:formatDate value="${content.target['startDate']}" pattern="yyyy-MM-dd" var="startDate"/>
					<fmt:formatDate value="${content.target['endDate'] }" pattern="yyyy-MM-dd" var="endDate"/>
					<div class="col-sm-3"><input type="date" class="form-control" id="start-date" name="start-date" placeholder="Start Date" value="${startDate }" /></div>
					<label for="end-date" class="col-sm-3">End Date</label>
					<div class="col-sm-3"><input type="date" class="form-control" id="end-date" name="end-date" placeholder="End Date" value="${endDate}"/></div>
 				</div>
 				<div class="form-group">
 					<label for="tags" class="col-sm-3">Tags</label>
					<div class="col-sm-9">
						<ul class="tags">
							<c:forEach var="tag" items="${content.tags}">
								<li>${tag }</li>
							</c:forEach>
						</ul>
					</div>
 				</div>
			</div>
		</div>
		<!-- END: TARGET SECTION -->
		<button type="submit" class="btn btn-default">Submit</button>
	</div>
</form>
