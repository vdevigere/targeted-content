<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<form class="form-horizontal" role="form" action="edit.html" method="GET">
		<div class="form-group">
			<div class="col-sm-10">
				<input type="text" class="form-control" id="content-id" name="_id" placeholder="Enter Content Id" />
			</div>
			<div class="col-sm-2"><button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button></div>
		</div>
</form>