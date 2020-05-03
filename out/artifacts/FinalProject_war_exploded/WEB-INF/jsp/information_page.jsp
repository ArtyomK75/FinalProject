<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Information" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf" %>

		<tr >
			<td class="content">
				<%-- CONTENT --%>

				<h2 class="information">
					<fmt:message key="info.info_header"/>
				</h2>

				<c:set var="info_header" value="${requestScope.infoHeader}"/>
				<c:set var="message" value="${requestScope.message}"/>

				<c:if test="${not empty info_header}">
					<h3>${info_header}</h3>
				</c:if>

				<c:if test="${not empty message}">
					<h3>${message}</h3>
				</c:if>
				</br>
				<a href="controller?command=mainPage"><fmt:message key="settings_jsp.link.back_to_main_page"></fmt:message></a>
				<%-- CONTENT --%>
			</td>
		</tr>


		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>