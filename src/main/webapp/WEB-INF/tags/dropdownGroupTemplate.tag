<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="groupName" required="true"%>
<%@ attribute name="options" required="true" type="java.util.List"%>

<select name="${groupName}" style="display:none">
	<c:forEach var="item" items="${options}">
		<option value="${item.id}">${item.name}</option>
	</c:forEach>
</select>