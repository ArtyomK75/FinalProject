<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>


<c:set var="title" value="Admin" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<fmt:setLocale value="${user.locale}" scope="session"/>
<fmt:setBundle basename="resources"/>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <td class="content">
        <%-- CONTENT --%>

        <c:choose>

            <c:when test="${fn:length(blockedUsers) == 0}"><fmt:message key="main_admin_page_jsp.link.no_users"/></c:when>

            <c:otherwise>

                <form id="change_status" action="controller">
                    <input type="hidden" name="command" value="changeStatus"/>
                    <input value=<fmt:message key="button.change_status"/> type="submit"/>


                    <table id="list_users_table">
                        <thead>
                        <tr>
                            <td>№</td>
                            <td><fmt:message key="field.client"/></td>
                            <td><fmt:message key="field.login"/></td>
                            <td><fmt:message key="field.status"/></td>
                            <td><fmt:message key="field.new_status"/></td>
                        </tr>
                        </thead>


                        <c:forEach var="bean" items="${blockedUsers}">

                            <tr>
                                <td>${bean.userId}</td>
                                <td>${bean.userFirstName} ${bean.userLastName}</td>
                                <td>${bean.userLogin}</td>
                                <td>${bean.statusName}</td>
                                <td>
                                    <select name=${bean.userId}>
                                        <c:forEach var="status" items="${statusList}">
                                            <option>${status}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>

                        </c:forEach>
                    </table>

                </form>
            </c:otherwise>
        </c:choose>

        <%-- CONTENT --%>
    </td>
    </tr>


    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
</html>