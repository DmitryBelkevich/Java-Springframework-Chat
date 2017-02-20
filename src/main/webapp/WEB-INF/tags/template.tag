<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<spring:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" var="bootstrapCss" />
<spring:url value="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" var="fontAwesomeCss" />
<spring:url value="/resources/css/sticky-footer-navbar.css" var="stickyFooterNavbarCss" />
<spring:url value="/resources/css/styles_scrollup.css" var="scrollUpCss" />
<spring:url value="/resources/css/main.css" var="mainCss" />

<spring:url value="https://code.jquery.com/jquery-3.1.1.min.js" var="jQuery" />
<spring:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" var="bootstrapJs" />
<spring:url value="/resources/js/jquery.scrollUp.min.js" var="scrollUpJs" />
<spring:url value="/resources/js/main.js" var="mainJs" />

<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="Windows-1251">
		<title>Java Chat</title>
		<link href="/resources/images/ico/favicon.ico" rel="shortcut icon">
		
		<link href="${bootstrapCss}" rel="stylesheet" />
		<link href="${fontAwesomeCss}" rel="stylesheet" />
		<link href="${stickyFooterNavbarCss}" rel="stylesheet" />
		<link href="${scrollUpCss}" rel="stylesheet" />
		<link href="${mainCss}" rel="stylesheet" />
		
		<script type="text/javascript" src="${jQuery}"
			integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
  			crossorigin="anonymous"
  		></script>
		<script type="text/javascript" src="${bootstrapJs}"></script>
		<script type="text/javascript" src="${scrollUpJs}"></script>
		<script type="text/javascript" src="${mainJs}"></script>
	</head>
	<body>
		
		<nav>
			<a href='<spring:url value="/main" />'>main</a>
			<a href='<spring:url value="/polling" />'>polling</a>
			<a href='<spring:url value="/long_polling" />'>long_polling</a>
			<a href='<spring:url value="/WebSocket" />'>WebSocket</a>
		</nav>
		
		<div class="container">
			<div class="row">
				<jsp:doBody />
			</div>
		</div>
		
	</body>
</html>