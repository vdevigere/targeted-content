<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<form class="form-horizontal" role="form" action="../../api/content/save" method="post">
	<div class="container">
		<!-- START: CONTENT -->
		<div class="form-group">
			<label for="content-name" class="col-sm-2">Content Name</label>
			<div class="col-sm-10">
				<input type="text" class="form-control" id="content-name" name="content-name" placeholder="Enter Name" />
			</div>
		</div>
		<!-- END: CONTENT -->
		
		
		
		<!-- START: The Content Data Section -->
		<div class="panel panel-info">
			<div class="panel-heading">
				<h5 class="panel-title">Content</h5> 				
			</div>
			<div class="panel-body">
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
					<ul class="nav nav-pills col-sm-10">
						<li><a href="#"><span class="glyphicon glyphicon-plus"></span></a></li>
						<li><a href="#"><span class="glyphicon glyphicon-trash"></span></a></li>
					</ul>
				</div>
			</div>
		</div>
		<!-- END: Content Data Section -->
		
		
		<!-- START: TARGET SECTION -->
		<div class="panel panel-info">
			<div class="panel-heading">
				<h5 class="panel-title">Targets</h5>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label for="start-date" class="col-sm-3">Start Date</label>
					<div class="col-sm-3"><input type="date" class="form-control" id="start-date" name="start-date" placeholder="Start Date" /></div>
					<label for="end-date" class="col-sm-3">End Date</label>
					<div class="col-sm-3"><input type="date" class="form-control" id="end-date" name="end-date" placeholder="End Date" /></div>
				</div>
			</div>
		</div>
		<!-- END: TARGET SECTION -->
		<button type="submit" class="btn btn-default">Submit</button>
	</div>
</form>
