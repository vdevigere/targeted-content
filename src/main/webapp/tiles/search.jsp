<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<script type="text/javascript">
	var initialContent =${initialContent };
</script>
<div class="container-fluid searchForm">
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-8">
			<form class="form-horizontal row" role="form">
				<div class="col-sm-12">
					<input class="tags" name="tags" />
				</div>
				<div class="col-sm-1">
					<h5>Valid Only</h5>
				</div>
				<div class="checkbox col-sm-11">
					<input type="checkbox" id="validOnly">
				</div>
			</form>
		</div>
		<div class="col-sm-2"></div>
	</div>
	<div class="row">
		<div class="col-sm-2" id="tagCloud">
		</div>
		<div class="col-sm-8" id="contentGrid"></div>
		<div class="col-sm-2"></div>
	</div>
</div>