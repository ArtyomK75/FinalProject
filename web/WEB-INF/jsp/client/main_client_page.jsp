<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jspf/directive/page.jspf" %>
<%@ include file="/WEB-INF/jspf/directive/taglib.jspf" %>

<html>

<c:set var="title" value="Client-bank" scope="page"/>
<%@ include file="/WEB-INF/jspf/head.jspf" %>

<body>
<table id="main-container">

    <%@ include file="/WEB-INF/jspf/header.jspf" %>

    <tr>
        <td class="content">
            <%-- CONTENT --%>

            <form autocomplete="off" id="main_form" action="controller" method="post">
                <input type="hidden" name="command" value="editPayments"/>
                <input type="hidden" name="countPaymentBeanList" value="${countPaymentBeanList}"/>

                <table id="list_users_table">
                    <thead>
                    <tr>
                        <td><fmt:message key="field.number"/></td>
                        <td><fmt:message key="field.date"/></td>
                        <td><fmt:message key="field.count"/></td>
                        <td><fmt:message key="field.receiver"/></td>
                        <td><fmt:message key="field.sum"/></td>
                        <td><fmt:message key="field.status"/></td>
                        <td><fmt:message key="field.edit"/></td>

                    </tr>
                    </thead>

                    <c:forEach var="bean" items="${countPaymentBeanList}">

                        <tr>
                            <td>${bean.id}</td>
                            <td>${bean.date}</td>
                            <td>${bean.count}</td>
                            <td>${bean.countReceiver}</td>
                            <td>${bean.sum}</td>

                            <td>

                                <c:forEach var="status" items="${statusList}">
                                    <c:if test="${status.key == bean.statusId}">
                                        ${status.value}
                                    </c:if>
                                </c:forEach>

                            </td>

                            <td>
                                <c:if test="${bean.statusId == 0}">
                                    <button type="submit" name="editPayment" value= ${bean.id}><fmt:message key="button.edit"/></button>
                                </c:if>
                            </td>

                        </tr>

                    </c:forEach>
                </table>

                <button type="submit" name="sortBy" value="byNumber"><fmt:message key="button.sort_by_number"/></button>
                <button type="submit" name="sortBy" value="byDateAsc"><fmt:message key="button.sort_by_ascending_date"/></button>
                <button type="submit" name="sortBy" value="byDateDesc"><fmt:message key="button.sort_by_descending_date"/></button>
                </br>
                <button type="submit" name="editPayment" value=addPayment><fmt:message key="button.add_payment"/></button>
            </form>

            <%-- CONTENT --%>
        </td>
    </tr>

    <%@ include file="/WEB-INF/jspf/footer.jspf" %>

</table>
</body>
