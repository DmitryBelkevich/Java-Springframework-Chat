<%@ page
	language="java"
	contentType="text/html; charset=Windows-1251"
	pageEncoding="Windows-1251"
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>

<spring:url value="/resources/js/long_polling.js" var="longPollingJs" />

<t:template>

<p>online:</p>
<div id="onlineClients"></div>

<p>messages:</p>
<div id="content"></div>
<form class="form" role="form">
	<div id="">
		<input id="inputField" type="text" class="form-control" placeholder="type message" />
		<button id="sendButton" type="button" class="btn btn-default">send</button>
	</div>
</form>

<p>typing:</p>
<div id="typingClients"></div>

<script type="text/javascript" src="${longPollingJs}"></script>

</t:template>