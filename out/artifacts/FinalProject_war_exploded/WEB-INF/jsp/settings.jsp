<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Settings" scope="page" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
	<table id="main-container">

		<%@ include file="/WEB-INF/jspf/header.jspf" %>

		<tr>
			<td class="content">
				<%-- CONTENT --%>

				<form id="settings_form" action="controller" method="post">
					<input type="hidden" name="command" value="updateSettings" />

					<div>
							<fmt:message key="settings_jsp.label.set_locale"/>:
							<select name="locale">
								<c:forEach items="${applicationScope.locales}" var="locale">
									<c:set var="selected" value="${locale.key == user.locale ? 'selected' : '' }"/>
									<option value="${locale.key}" ${selected}>${locale.value}</option>
								</c:forEach>
							</select>
							<input type="submit" value="<fmt:message key='settings_jsp.form.submit_save_locale'/>">
						<a href="controller?command=mainPage"><fmt:message key="settings_jsp.link.back_to_main_page"></fmt:message></a>
					</div>
					
				</form>
				
				<%-- CONTENT --%>
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf" %>
		
	</table>
</body>
</html>