<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Registration" />
<%@ include file="/WEB-INF/jspf/head.jspf" %>
	
<body>

	<table id="main-container">


		<%-- HEADER --%>
		<%@ include file="/WEB-INF/jspf/header.jspf"%>
		<%-- HEADER --%>

		<tr >
			<td class="content center">
				<form id="login_form" action="controller" method="post">

					<input type="hidden" name="command" value="registration"/>
					<fieldset >
						<legend>First name</legend>
						<input name="first_name"/><br/>
					</fieldset><br/>
					<fieldset >
						<legend>Last name</legend>
						<input name="last_name"/><br/>
					</fieldset><br/>
					<fieldset >
						<legend>Login</legend>
						<input name="login"/><br/>
					</fieldset><br/>
					<fieldset>
						<legend>Password</legend>
						<input type="password" name="password"/>
					</fieldset><br/>
					
					<input type="submit" value="Registration">
				</form>
				
			</td>
		</tr>

		<%@ include file="/WEB-INF/jspf/footer.jspf"%>
		
	</table>
</body>
</html>