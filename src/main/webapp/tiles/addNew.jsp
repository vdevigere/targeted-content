<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="row">
	<div class="col-md-4"></div>
	<form action="../content/post" method="post">
		<div class="col-md-4">
			<div class="row">
				<div class="col-md-6">Name:</div>
				<div class="col-md-6"><input type="text" name="name" /></div>
			</div>
			<div class="row">
				<div class="col-md-6">URL:</div>
				<div class="col-md-6"><input type="text" name="url"/></div>
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
				<div class="col-md-6"><input type="text" name="tags" /></div>
			</div>
			<div class=row">
				<div class="col-md-12"><input type="submit" value="Submit" /></div>
			</div>
		</div>
	</form>
	<div class="col-md-4"></div>
</div>