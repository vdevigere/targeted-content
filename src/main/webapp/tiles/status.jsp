<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<c:if test="${status != null && status.message !=null && !status.message.isEmpty()}">
<div class="row">
	<div class="alert ${status.type.style } col-sm-12" role="alert">${status.message }</div>
</div>
</c:if>