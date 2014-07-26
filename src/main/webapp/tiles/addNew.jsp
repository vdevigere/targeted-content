<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<div class="row">
	<div class="col-md-4"></div>
	<form action="../content/post" method="POST">
		<div class="col-md-4">
			<div class="row">
				<div class="col-md-6">Name:</div>
				<div class="col-md-6">
					<input type="text" name="name" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">URL:</div>
				<div class="col-md-6">
					<input type="text" name="url" />
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">Type:</div>
				<div class="col-md-6">
					<select name="type">
						<c:forEach var="contentType" items="${contentTypeValues}">
							<option id="${contentType}">${contentType}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">Tags:</div>
				<div class="col-md-6">
					<ul id="tags">
					</ul>
				</div>
			</div>
			<div class=row">
				<div class="col-md-12">
					<input type="submit" value="Submit" />
				</div>
			</div>
		</div>
	</form>
	<div class="col-md-4"></div>
</div>