<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<select>
	<c:forEach var="exerciseRoutine" items="${routines }">
		<option id="${exerciseRoutine.id }">${exerciseRoutine.name }</option>
	</c:forEach>
</select>