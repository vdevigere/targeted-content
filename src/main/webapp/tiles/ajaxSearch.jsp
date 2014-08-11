<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<div class="container" id="contentListView">
	<div class="row">
		<div class="col-sm-12">
			<ul class="tags">
			</ul>
		</div>
	</div>
	<form class="form-horizontal row" role="form">
	  <div class="col-sm-1">
	      <h5>Valid Only</h5>
	  </div>
      <div class="checkbox col-sm-11"> 
          <input type="checkbox" id="validOnly">
      </div>
	</form>
	<div class="row">
		<div class="col-sm-12">
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
				<tbody id="contentList">
				</tbody>
			</table>
		</div>
	</div>
</div>
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